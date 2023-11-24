package com.eztornado.tornadocorebase.repositories;

import com.eztornado.tornadocorebase.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    // Query methods if needed
}
