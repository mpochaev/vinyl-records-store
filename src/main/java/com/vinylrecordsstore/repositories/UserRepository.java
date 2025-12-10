package com.vinylrecordsstore.repositories;

import com.vinylrecordsstore.entities.User;
import com.vinylrecordsstore.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIgnoreCaseOrEmailIgnoreCase(String login, String email);

    boolean existsByLoginIgnoreCase(String login);

    boolean existsByEmailIgnoreCase(String email);

    List<User> findAllByRole(Role role);
}