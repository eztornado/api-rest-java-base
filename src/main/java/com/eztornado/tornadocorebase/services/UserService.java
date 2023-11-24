package com.eztornado.tornadocorebase.services;

import com.eztornado.tornadocorebase.dto.UserDto;
import com.eztornado.tornadocorebase.filters.UserFilter;
import com.eztornado.tornadocorebase.models.User;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User create(UserDto userDto);
    List<User> findAll();

    Page<User> findAll(UserFilter T, Pageable P);

    User findByEmail(String email);

    User save(Long id, UserDto userDto);

    User findById(Long id);

    void delete(Long id);
    void softDelete(Long id);
}