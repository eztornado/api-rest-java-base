package com.eztornado.tornadocorebase.repositories;

import com.eztornado.tornadocorebase.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    <Optional> User findByEmail(String email);
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.deleted_at IS NULL")
    List<User> findAllSoftDeleted();

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.deleted_at IS NULL")
    User findByEmailSoftDeleted(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.deleted_at IS NULL")
    User findByIdSoftDeleted(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.deleted_at IS NULL")
    User findByUsernameSoftDeleted(@Param("username") String username);

    Page<User> findAll(Specification<User> spec, Pageable p);
}