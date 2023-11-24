package com.eztornado.tornadocorebase.repositories;


import com.eztornado.tornadocorebase.models.TCException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TCExceptionRepository extends JpaRepository<TCException, Long> {

}