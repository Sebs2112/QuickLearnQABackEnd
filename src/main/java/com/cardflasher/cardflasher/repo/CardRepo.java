package com.cardflasher.cardflasher.repo;


import com.cardflasher.cardflasher.model.Card;

import com.cardflasher.cardflasher.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



//Could also extend JpaRepo
@Repository
public interface CardRepo extends CrudRepository<Card, Long> {

    List<Card> findAllByUserId(String id);
    Card findByTitleAndUserId(String name, String u);
}
