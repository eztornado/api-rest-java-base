package com.eztornado.tornadocorebase.services;

import com.eztornado.tornadocorebase.dto.RecoveryPasswordDto;
import com.eztornado.tornadocorebase.models.RecoveryPassword;
import com.eztornado.tornadocorebase.repositories.RecoveryPasswordRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecoveryPasswordServiceImpl implements RecoveryPasswordService {

    private final RecoveryPasswordRepository repository;

    public RecoveryPasswordServiceImpl(RecoveryPasswordRepository repository) {
        this.repository = repository;
    }

    @Override
    public RecoveryPassword create(RecoveryPasswordDto dto) {
        RecoveryPassword recovery = new RecoveryPassword();
        recovery.setUser(dto.getUser());
        Date fecha_actual = new Date();
        recovery.setToken(new BCryptPasswordEncoder().encode("" + fecha_actual.toString()+"-"+dto.getUser().getEmail()));
        recovery = repository.save(recovery);
        return recovery;
    }

    @Override
    public RecoveryPassword findByToken(String token) {
        return repository.findByToken(token);
    }
}
