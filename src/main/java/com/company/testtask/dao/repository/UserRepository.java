package com.company.testtask.dao.repository;

import com.company.testtask.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);
}
