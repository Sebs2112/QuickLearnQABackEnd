package com.cardflasher.cardflasher.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;


//If you wanted this class to be stored in a table of a different name then you could oput the following line
//Card=School or something similar
@Entity
public class Card {

    @Id //At ID means that this is the primary key.
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String frontText;
    private String backText;

    private String category;
    private String title;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private User user;

    public Card(){}


    public Card(String title, String frontText, String backText,  String category, User user) {

        this.frontText = frontText;
        this.backText = backText;
        this.category = category;
        this.title = title;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrontText() {
        return frontText;
    }

    public String getBackText() {
        return backText;
    }

    public Long getId(){
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
