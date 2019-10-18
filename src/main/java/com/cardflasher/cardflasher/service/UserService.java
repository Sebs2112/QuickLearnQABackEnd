package com.cardflasher.cardflasher.service;

import com.cardflasher.cardflasher.model.User;
import com.cardflasher.cardflasher.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserService {


    @Autowired
    private UserRepo uRepo;




    public Optional getUser(String id){
        return uRepo.findById(id);

    }
}
