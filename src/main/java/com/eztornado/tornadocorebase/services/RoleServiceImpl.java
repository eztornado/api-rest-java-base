package com.eztornado.tornadocorebase.services;

import com.eztornado.tornadocorebase.exceptions.RoleNotFoundException;
import com.eztornado.tornadocorebase.exceptions.UserNotFoundException;
import com.eztornado.tornadocorebase.models.ERole;
import com.eztornado.tornadocorebase.models.Role;
import com.eztornado.tornadocorebase.models.User;
import com.eztornado.tornadocorebase.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role roleDto) {
        Role role =  new Role();
        role.setName(roleDto.getName());
        role = this.roleRepository.save(role);
        return role;
    }

    @Override
    public Optional<Role> findByName(ERole name) {
        return this.roleRepository.findByName(name);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return this.roleRepository.findById(id);
    }

    @Override
    public Role save(Long id, Role roleDto) {
        Optional<Role> role = this.roleRepository.findById(roleDto.getId());
        if(role == null) {
            throw new RoleNotFoundException(roleDto.getId());
        }else {
            Role roleObj = role.get();
            roleObj.setName(roleDto.getName());
            roleObj = this.roleRepository.save(roleObj);
            return roleObj;
        }
    }

    @Override
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        roleRepository.deleteById(id);
    }
}
