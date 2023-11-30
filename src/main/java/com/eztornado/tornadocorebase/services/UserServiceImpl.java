package com.eztornado.tornadocorebase.services;

import com.eztornado.tornadocorebase.dto.UserDto;
import com.eztornado.tornadocorebase.filters.UserFilter;
import com.eztornado.tornadocorebase.models.User;
import com.eztornado.tornadocorebase.repositories.UserRepository;
import com.eztornado.tornadocorebase.repositories.specifications.UserSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.eztornado.tornadocorebase.exceptions.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Value("${tornadocore.softDelete}")
    private boolean softDelete;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User create(UserDto userDto) {
        User user = new User();
        // Convertir UserDto a la entidad User y establecer las propiedades necesarias
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setActive(userDto.getActive());
        user.setPassword( new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setRoles(userDto.getRoles());

        // Guardar el usuario en la base de datos
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        if(this.softDelete == true) {
            return userRepository.findAllSoftDeleted();
        } else {
            return  userRepository.findAll();
        }
    }

    @Override
    public Page<User> findAll(UserFilter T, Pageable P) {
        Specification<User> spec = new UserSpecification(T);
        return userRepository.findAll( spec, P);
    }

    @Override
    public User findByEmail(String email) {
        if(this.softDelete == true) {
            return userRepository.findByEmailSoftDeleted(email);
        } else {
            return userRepository.findByEmail(email);
        }
    }

    @Override
    public User findById(Long id) {
        if(this.softDelete == true) {
            return userRepository.findByIdSoftDeleted(id);
        } else {
            return userRepository.findById(id).orElse(null);
        }
    }

    @Override
    public void delete(Long id) {
        // Puedes verificar primero si el usuario existe
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public void softDelete(Long id) {
        // Puedes verificar primero si el usuario existe
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        User user = this.findById(id);
        user.setDeleted_at(new Date());
        userRepository.save(user);
    }

    @Override
    public User save(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Actualizar las propiedades del usuario con los valores de UserDto
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        if(userDto.getActive() == null) {
            user.setActive(userDto.getActive());
        }
        user.setActive(userDto.getActive());
        if(userDto.getPassword() != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        }
        user.setRoles(userDto.getRoles());
        return userRepository.save(user);
    }
}