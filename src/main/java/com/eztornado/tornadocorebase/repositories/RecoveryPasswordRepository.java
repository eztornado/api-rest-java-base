package com.eztornado.tornadocorebase.repositories;

import com.eztornado.tornadocorebase.models.File;
import com.eztornado.tornadocorebase.models.RecoveryPassword;
import com.eztornado.tornadocorebase.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryPasswordRepository extends JpaRepository<RecoveryPassword, Long> {
    <Optional> RecoveryPassword findByToken(String token);
}