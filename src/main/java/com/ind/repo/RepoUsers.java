package com.ind.repo;

import com.ind.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoUsers extends JpaRepository<Users, Long> {
    List<Users> findAllByOrderByRole();

    Users findByUsername(String username);

    Users findByUsernameAndLastname(String username, String lastname);

    Users findByEmail(String email);
}
