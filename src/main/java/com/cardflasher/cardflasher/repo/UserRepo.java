package com.cardflasher.cardflasher.repo;


import com.cardflasher.cardflasher.model.Card;
import com.cardflasher.cardflasher.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//Beans are always created by spring
//Spring boot will complete the interface and then instantiate it when injected into another class, in this example in the cotroller
public interface UserRepo extends CrudRepository<User, String> {






}
