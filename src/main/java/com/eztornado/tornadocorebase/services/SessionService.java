package com.eztornado.tornadocorebase.services;


import com.eztornado.tornadocorebase.dto.SessionDto;
import com.eztornado.tornadocorebase.models.Session;

import java.util.Date;
import java.util.List;

public interface SessionService {
    Session create(SessionDto sessionDto);
    List<Session> getSessionsByExpiresAtBefore(Date date);
    Session save(Long id, SessionDto userDto);

    void delete(Long id);
}