package com.codeup.blog.models;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="posts")
public class Post {
    @Id @GeneratedValue
    private long id;

    @Column(nullable = false, length = 100)
    private String title;


    @Column(nullable = false, length = 250)
    private String body;

    @OneToOne
    private User user;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "posts_categories", joinColumns = {@JoinColumn(name = "post_id")},
  inverseJoinColumns = {@JoinColumn(name = "category_id")})
  private List<Category> categories;



    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Post(){
    }



    public Post(long id, String title, String body, User user, List<Category> categories) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.user = user;
        this.categories = categories;
    }

    public Post(String title, String body, User user, List<Category> categories) {
        this.title = title;
        this.body = body;
        this.user = user;
        this.categories = categories;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}