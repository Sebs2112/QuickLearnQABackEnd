package com.cardflasher.cardflasher.service;


import com.cardflasher.cardflasher.model.Card;
import com.cardflasher.cardflasher.model.User;
import com.cardflasher.cardflasher.repo.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Component
public class CardService {

    @Autowired
    private CardRepo cRepo;
    @Autowired
    private UserService userServ;




    public Iterable<Card> list(Principal principal){


        return cRepo.findAllByUserId(principal.getName());
    }





    public Card add(Card card, OAuth2User principal)

    {

        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();


        Optional<User> user = userServ.getUser(userId);
        card.setUser(user.orElse(new User(userId,
                details.get("name").toString(), details.get("email").toString())));
        Card result = cRepo.save(card);
        return result;

    }


    public Card get(Long id) throws Exception {

        return cRepo.findById(id)
                .orElseThrow(() -> new Exception());
    }



    public Card update(Long id,Card newCard) throws Exception {
        System.out.println("Id" + id);
        Card card = cRepo.findById(id)
                .orElseThrow(() -> new Exception());
        card.setBackText(newCard.getBackText());
        card.setCategory(newCard.getCategory());
        card.setFrontText(newCard.getFrontText());
        card.setTitle(newCard.getTitle());
        return cRepo.save(card);
    }




    public void delete(Long id ) throws Exception {

        Card card = cRepo.findById(id)
                .orElseThrow(() -> new Exception());
        cRepo.delete(card);
    }



}
