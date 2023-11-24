package com.eztornado.tornadocorebase.controller.admin;

import com.eztornado.tornadocorebase.dto.UserDto;
import com.eztornado.tornadocorebase.filters.UserFilter;
import com.eztornado.tornadocorebase.models.User;
import com.eztornado.tornadocorebase.payload.response.ApiResponse;
import com.eztornado.tornadocorebase.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/api/admin")
    public class UsersController {

    @Value("${tornadocore.softDelete}")
    private boolean softDelete;

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RolesAllowed({"ROLE_ADMIN"})
        @GetMapping("/users")
        public ResponseEntity<ApiResponse<Object>> getUsers(UserFilter userFilterDto, Pageable pageable) {
            return ResponseEntity.ok(new ApiResponse<>("success",userService.findAll(userFilterDto, pageable),""));
        }

    @RolesAllowed({"ROLE_ADMIN"})
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<Object>> createUser(@Valid  @RequestBody UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error",bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toArray(String[]::new),"Validation errors"));
        }
        userDto.setActive(true);
        User newUser = userService.create(userDto);
        return ResponseEntity.ok(new ApiResponse<>("success",newUser,""));
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Object>> getUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
            if(user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("error", "{}","User not found"));
            }
            return ResponseEntity.ok(new ApiResponse<>("success", user,""));
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Object>> updateUser(@PathVariable Long id,@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error",bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toArray(String[]::new),"Validation errors"));
        }

        User updatedUser = userService.save(id, userDto);
        if (updatedUser != null) {
            return ResponseEntity.ok(new ApiResponse<>("success", updatedUser, ""));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("error", "{}","User not found"));
        }
    }

    @DeleteMapping("/users/{id}")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable Long id) {
        if(this.softDelete == true) {
            userService.softDelete(id);
        } else {
            userService.delete(id);
        }
        return ResponseEntity.ok(new ApiResponse<>("success", "{}", "User deleted successfully"));
    }


}
