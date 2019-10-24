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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Card getByNameAndUser(String name, OAuth2User principal){

        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();
        return cRepo.findByTitleAndUserId(name,userId);
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

    public void addMultipleFromCSV(MultipartFile file, OAuth2User principal){

        //Error check to see if the csv has the correct titles.
        try{
            InputStreamReader inputS = new InputStreamReader(file.getInputStream(),
                    StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(inputS);
            br.readLine();

            Pattern hash = Pattern.compile("(#)\\w*\\b");
            Matcher m1;
            Matcher m2;
            String line;
            String[] columns;


            String category = "";
            String ftext = "";
            String btext = "";

            while((line =br.readLine())!= null){
                System.out.println(line);


                if(line.substring(0,1).equals("#")){
                    columns = line.split(",");
                    category = columns[1].replaceAll("\"","");

                    m1 = hash.matcher(columns[0]);


                    if(m1.find()) {
                        ftext = m1.group();
                        btext = columns[0].substring(ftext.length()+1, columns[0].length());

                     }


                }else if(line.substring(0,1).equals("\"")) {
                    columns = line.split("\",");

                    if (columns.length > 1) {
                        category = columns[1].split(",")[0].replaceAll("\"","");

                    }

                    m1 = hash.matcher(columns[0]);


                    if(m1.find()) {
                        ftext = m1.group();
                        btext = columns[0].substring(ftext.length()+1, columns[0].length());


                    }
                 }
                ftext = ftext.replaceAll("#","");
                System.out.println("cat " + category + "title " + ftext + "back text " + btext);
                if(btext.length()<=255 && ftext.length()!= 0 && btext.length()!=0) {
                    add(new Card(ftext, ftext, btext, category, null), principal);
                }
            }


        }catch(Exception e){
            System.out.println("Error");
        }
    }



}
