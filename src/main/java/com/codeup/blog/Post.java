package com.codeup.blog;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 250)
    private String title;

    @Column(nullable = false)
    private String body;


    public Post(String title, String body) {
        
    }

    public void setTitle(String title) {
    }

    public void setBody(String body) {
    }
}
