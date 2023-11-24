package com.eztornado.tornadocorebase.services;

import com.eztornado.tornadocorebase.dto.SessionDto;
import com.eztornado.tornadocorebase.exceptions.SessionNotFoundException;
import com.eztornado.tornadocorebase.models.Session;
import com.eztornado.tornadocorebase.models.User;
import com.eztornado.tornadocorebase.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    @Value("${tornadocore.softDelete}")
    private boolean softDelete;

    private final SessionRepository repository;

    @Autowired
    public SessionServiceImpl(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Session create(SessionDto sessionDto) {
        Session sesion = new Session();
        User user = new User(sessionDto.getUser());
        sesion.setIp(sessionDto.getIp());
        sesion.setClient(sessionDto.getClient());
        sesion.setUser(user);
        sesion.setToken(new BCryptPasswordEncoder().encode(sessionDto.getToken()));
        sesion.setExpiresAt(sessionDto.getExpiresAt());
        sesion = repository.save(sesion);
        return sesion;
    }

    @Override
    public List<Session> getSessionsByExpiresAtBefore(Date date) {
        if (softDelete) {
            // Si tienes habilitado el borrado blando, puedes agregar una verificación para ignorar registros marcados como eliminados
            return repository.findByExpiresAtBeforeAndDeletedAtIsNull(date);
        } else {
            return repository.findByExpiresAtBefore(date);
        }
    }

    @Override
    public Session save(Long id, SessionDto dto) {
        Session session = repository.findById(id)
                .orElseThrow(() -> new SessionNotFoundException(id));
        User user = new User(dto.getUser());
        session.setIp(dto.getIp());
        session.setClient(dto.getClient());
        if(dto.getToken() != session.getToken()) {
            //Tenemos un nuevo token sin encriptar
            session.setToken(new BCryptPasswordEncoder().encode(dto.getToken()));
        }
        session.setExpiresAt(dto.getExpiresAt());
        if(softDelete) {
            session.setDeletedAt(dto.getDeleted_at());
        }
        session.setUser(user);
        return repository.save(session);
    }

    @Override
    public void delete(Long id) {
        // Puedes verificar primero si el usuario existe
        if (!repository.existsById(id)) {
            throw new SessionNotFoundException(id);
        }
        repository.deleteById(id);
    }
}