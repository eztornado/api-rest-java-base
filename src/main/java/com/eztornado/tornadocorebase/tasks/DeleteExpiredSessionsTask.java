package com.eztornado.tornadocorebase.tasks;
import com.eztornado.tornadocorebase.annotation.EveryMinute;
import com.eztornado.tornadocorebase.dto.SessionDto;
import com.eztornado.tornadocorebase.models.Session;
import com.eztornado.tornadocorebase.models.User;
import com.eztornado.tornadocorebase.services.SessionService;
import com.eztornado.tornadocorebase.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DeleteExpiredSessionsTask implements Task {

    private final SessionService service;
    private final UserService userService;

    @Autowired
    public DeleteExpiredSessionsTask(SessionService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @Value("${tornadocore.softDelete}")
    private boolean softDelete;

    @Override
    @EveryMinute(1)
    public void execute() {

        List<Session> sesiones = service.getSessionsByExpiresAtBefore(new Date());

        for(Session session : sesiones) {
            if(softDelete == true) {
                User user = userService.findById(session.getUser().getId());
                com.eztornado.tornadocorebase.security.services.UserDetailsImpl userDetails = new com.eztornado.tornadocorebase.security.services.UserDetailsImpl(user);
                SessionDto dto = new SessionDto();
                dto.setIp(session.getIp());
                dto.setId(session.getId());
                dto.setClient(session.getClient());
                dto.setExpiresAt(session.getExpiresAt());
                dto.setToken(session.getToken());
                dto.setDeleted_at(new Date());
                dto.setUser(userDetails);
                service.save(session.getId(), dto);
                System.out.println("DeleteExpiredSessionsTask -  Session ID: " + session.getId() + "has marked as finished");
            } else {
                service.delete(session.getId());
                System.out.println("DeleteExpiredSessionsTask -  Session ID: " + session.getId() + "has marked as finished");
            }
        }

    }
}
