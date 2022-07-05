package com.ind.repo;

import com.ind.models.Actions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoActions extends JpaRepository<Actions, Long> {
    List<Actions> findByIdUser(Long idUser);

    List<Actions> findByIdUserAndDateStartingWith(Long idUser, String date);
}
