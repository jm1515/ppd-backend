package com.ppd.mubler.service;

import com.ppd.mubler.model.entity.User;
import com.ppd.mubler.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements Service<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User entity) {
        this.userRepository.save(entity);
    }

    public List<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        for (User user : this.findByEmail(email)) {
            if (user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public User getUserById(long id){
        return this.userRepository.findById(id);
    }

    public Iterable<User> getAllUsers(){
        return this.userRepository.findAll();
    }
}
