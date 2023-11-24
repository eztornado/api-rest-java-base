package com.eztornado.tornadocorebase.security.services;

import com.eztornado.tornadocorebase.models.User;
import com.eztornado.tornadocorebase.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    if(user == null) {
      throw new UsernameNotFoundException("User Not Found with username: " + username);
    }
    return com.eztornado.tornadocorebase.security.services.UserDetailsImpl.build(user);
  }
}
