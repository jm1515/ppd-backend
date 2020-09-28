package com.ppd.mubler.model.repository;

import com.ppd.mubler.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByEmail(String email);
    User findById(long id);
}
