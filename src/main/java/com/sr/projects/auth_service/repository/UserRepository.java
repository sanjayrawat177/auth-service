package com.sr.projects.auth_service.repository;

import org.springframework.stereotype.Repository;
import com.sr.projects.auth_service.entity.Users;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByUserNameAndPassword(String userName, String password);
}
