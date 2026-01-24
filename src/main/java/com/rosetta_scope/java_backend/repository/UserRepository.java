package com.rosetta_scope.java_backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rosetta_scope.java_backend.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
