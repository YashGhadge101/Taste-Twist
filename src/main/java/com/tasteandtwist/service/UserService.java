package com.tasteandtwist.service;

import com.tasteandtwist.model.User;
import com.tasteandtwist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired private UserRepository repo;
    public User findByEmail(String email) { return repo.findByEmail(email); }
    public User save(User u) { return repo.save(u); }
}
