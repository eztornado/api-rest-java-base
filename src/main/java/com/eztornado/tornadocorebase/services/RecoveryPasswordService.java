package com.eztornado.tornadocorebase.services;

import com.eztornado.tornadocorebase.dto.RecoveryPasswordDto;
import com.eztornado.tornadocorebase.models.RecoveryPassword;

public interface RecoveryPasswordService {
    RecoveryPassword create(RecoveryPasswordDto dto);

    RecoveryPassword findByToken(String token);
}
