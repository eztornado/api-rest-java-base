package com.eztornado.tornadocorebase.repositories;

import com.eztornado.tornadocorebase.models.FileToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileTokenRepository extends JpaRepository<FileToken, Long> {


    Optional<FileToken> findByTokenAndFileIdAndUserId(String token, Long fileId, Long userId);
}