package com.company.testtask.dao.repository;

import com.company.testtask.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);

    @Query(nativeQuery = true, value = "SELECT * FROM users u WHERE u.id BETWEEN ?1 AND ?2")
    List<User> findByIdRange(Long startId, Long finishId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM users u WHERE u.id BETWEEN ?1 AND ?2 ")
    void deleteByIdRange(Long startId, Long finishId);
}
