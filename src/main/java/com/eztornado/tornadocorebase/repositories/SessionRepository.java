package com.eztornado.tornadocorebase.repositories;

import com.eztornado.tornadocorebase.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    // Método para buscar sesiones cuya fecha de expiración sea anterior a la fecha proporcionada y no estén marcadas como eliminadas
    List<Session> findByExpiresAtBeforeAndDeletedAtIsNull(Date date);

    // Método para buscar sesiones cuya fecha de expiración sea anterior a la fecha proporcionada
    List<Session> findByExpiresAtBefore(Date date);
}