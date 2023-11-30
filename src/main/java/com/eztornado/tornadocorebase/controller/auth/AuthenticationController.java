package com.eztornado.tornadocorebase.controller.auth;

import com.eztornado.tornadocorebase.controller.BaseController;
import com.eztornado.tornadocorebase.dto.RecoveryPasswordDto;
import com.eztornado.tornadocorebase.dto.SessionDto;
import com.eztornado.tornadocorebase.dto.UserDto;
import com.eztornado.tornadocorebase.exceptions.RoleNotFoundException;
import com.eztornado.tornadocorebase.models.*;
import com.eztornado.tornadocorebase.payload.request.LoginRequest;
import com.eztornado.tornadocorebase.payload.request.RecoveryPasswordAskRequest;
import com.eztornado.tornadocorebase.payload.request.RecoveryPasswordUpdateRequest;
import com.eztornado.tornadocorebase.payload.response.ApiResponse;
import com.eztornado.tornadocorebase.security.jwt.JwtUtils;
import com.eztornado.tornadocorebase.security.services.UserDetailsImpl;
import com.eztornado.tornadocorebase.services.RecoveryPasswordService;
import com.eztornado.tornadocorebase.services.RoleService;
import com.eztornado.tornadocorebase.services.SessionService;
import com.eztornado.tornadocorebase.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController extends BaseController {

    @Autowired
    AuthenticationManager authenticationManager;

    private UserService userService;

    private SessionService sessionService;

    private RecoveryPasswordService recoveryPasswordService;


    private RoleService roleService;

    @Value("${tornadocore.registerEnabled}")
    private boolean registerEnabled;

    @Autowired
    JwtUtils jwtUtils;

    public AuthenticationController(UserService userService, SessionService sessionService, RecoveryPasswordService recoveryPasswordService, RoleService roleService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.recoveryPasswordService = recoveryPasswordService;
        this.roleService = roleService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {


        if(registerEnabled == false) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Register is disabled");
            return ResponseEntity.badRequest().body(new ApiResponse<>("error",errors,"Validation errors"));
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error",bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toArray(String[]::new),"Validation errors"));
        }

        // Verificar si el usuario ya existe
        if (userService.findByEmail(userDto.getEmail()) != null) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("User exists");
            return ResponseEntity.badRequest().body(new ApiResponse<>("error",errors,"Validation errors"));
        }
        //Establecer el usuario activo
        userDto.setActive(true);

        // Establecer el rol del usuario
        Optional<Role> role = roleService.findByName(ERole.ROLE_USER);
        if(role == null) {
            throw new RoleNotFoundException(ERole.ROLE_USER.name());
        }
        HashSet<Role> userRoles = new HashSet<Role>();
        userRoles.add(role.get());
        userDto.setRoles(userRoles);


        return  ResponseEntity.ok(new ApiResponse<>("success",userService.create(userDto),"User created successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> authenticateUser(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Suponiendo que tienes un método que puede obtener la fecha de expiración del token
        Date expirationDate = jwtUtils.getExpirationDateFromJwt(jwt);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        HashMap<String, Object> resultado = new HashMap<>();
        resultado.put("token", jwt);
        resultado.put("user", userDetails);
        resultado.put("expires_at", expirationDate);

        //Registrar inicio de sesión
        String clientIp = this.getClientIp(httpRequest);
        String clientAgent = this.getClientAgent(httpRequest);

        SessionDto sesionDto = new SessionDto();
        sesionDto.setUser(userDetails);
        sesionDto.setIp(clientIp);
        sesionDto.setClient(clientAgent);
        sesionDto.setToken(jwt);
        sesionDto.setExpiresAt(expirationDate);
        Session session = sessionService.create(sesionDto);
        resultado.put("session_id", session.getId());

        return ResponseEntity.ok(new ApiResponse<>("success",resultado,""));
    }

    @PostMapping("/recovery-password/ask")
    public ResponseEntity<ApiResponse<Object>> recoveryPasswordAsk(@Valid RecoveryPasswordAskRequest request, HttpServletRequest httpServletRequest) {

        User user = userService.findByEmail(request.getEmail());
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("error", "{}","User not found"));
        }
        RecoveryPasswordDto dto = new RecoveryPasswordDto();
        dto.setUser(user);
        RecoveryPassword result = recoveryPasswordService.create(dto);
        HashMap<String, Object> resultado = new HashMap<>();
        resultado.put("token",result.getToken());
        return ResponseEntity.ok(new ApiResponse<>("success",resultado,"Reset password petition was successfully processed"));
    }

    @PostMapping("/recovery-password/update")
    public ResponseEntity<ApiResponse<Object>> recoveryPasswordUpdate(@Valid RecoveryPasswordUpdateRequest request, HttpServletRequest httpServletRequest) {

        RecoveryPassword recoveryPassword = recoveryPasswordService.findByToken(request.getToken());
        if(recoveryPassword == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("error", "{}","RecoveryPassword petition not found"));
        }

        User user = userService.findById(recoveryPassword.getUser().getId());
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("error", "{}","User not found"));
        }

        UserDto userDto = new UserDto(user);
        userDto.setPassword(request.getNewPassword());
        userService.save(user.getId(), userDto);

        return ResponseEntity.ok(new ApiResponse<>("success","{}","Password was changed successfully"));
    }
}