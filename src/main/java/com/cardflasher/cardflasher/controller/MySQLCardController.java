package com.cardflasher.cardflasher.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.cardflasher.cardflasher.model.Card;
import com.cardflasher.cardflasher.model.User;
import com.cardflasher.cardflasher.repo.CardRepo;
import com.cardflasher.cardflasher.repo.UserRepo;
import com.cardflasher.cardflasher.service.CardService;
import com.cardflasher.cardflasher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping("/api/")
public class MySQLCardController {


    @Autowired
    private CardService cardServ;
    @Autowired
    private UserService userServ;



    @RequestMapping(value = "cards", method = RequestMethod.GET)
    public Iterable<Card> list(Principal principal){


        return cardServ.list(principal);
    }




    @RequestMapping(value = "cards", method = RequestMethod.POST)
    public Card add(@RequestBody Card card, @AuthenticationPrincipal OAuth2User principal) {
        
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();


        Optional<User> user = userServ.getUser(userId);
        card.setUser(user.orElse(new User(userId,
                details.get("name").toString(), details.get("email").toString())));

        Card result = cardServ.add(card);
        return result;

    }
    //Get mapping would also work here and post put etc
    @RequestMapping(value = "cards/{id}", method = RequestMethod.GET)
    public Card get(@PathVariable Long id) throws Exception {

        return cardServ.get(id);
    }



    @RequestMapping(value = "cards/{id}", method = RequestMethod.PUT)
    public Card update(@PathVariable Long id, @RequestBody Card newCard) throws Exception {

        return cardServ.update(id,newCard);
    }


    @RequestMapping(value = "cards/{id}", method = RequestMethod.DELETE)

    public void delete(@PathVariable Long id ) throws Exception {


        cardServ.delete(id);
    }




}
