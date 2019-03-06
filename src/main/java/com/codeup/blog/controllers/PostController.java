package com.codeup.blog.controllers;

import com.codeup.blog.Services.EmailService;
import com.codeup.blog.models.Category;
import com.codeup.blog.models.Post;
import com.codeup.blog.models.User;
import com.codeup.blog.repositories.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.blog.repositories.PostRepository;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
//    @Autowired
private EmailService emailService;

    private final PostRepository postDao;
    public PostController(PostRepository postDao, EmailService emailService){
        this.postDao=postDao;
        this.emailService = emailService;
    }

    @Autowired
    private Users userDao;



    @GetMapping("/posts")
    public String all(Model model){
        Iterable<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model model){
        Post post = postDao.findOne(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String showForm(){
        return "posts/create";
    }

    @PostMapping("/posts/create")
       public String create( @RequestParam(name = "title") String title,
                @RequestParam(name = "body") String body){
            List<Category>categories = new ArrayList<>();
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userDB = userDao.findOne(sessionUser.getId());
            Post post = new Post(title, body, userDB, categories);
            Post savedPost = postDao.save(post);
            emailService.prepareAndSend(savedPost, "Done", "Its saved");
            return "redirect:/posts";
        }

//    @PostMapping("/posts/create")
//    public String create(@ModelAttribute Post post){
//
//
//  postDao.save(post);
//        emailService.prepareAndSend("email", "Done", "Its saved");
//        return "redirect:/posts";
//    }


    @GetMapping("/posts/{id}/edit")
    public String edit(@PathVariable long id, Model model){
        Post post = postDao.findOne(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String update(@PathVariable long id, @RequestParam(name = "title") String title, @RequestParam(name = "body") String body, Model model){
        Post post = postDao.findOne(id);
        post.setTitle(title);
        post.setBody(body);
        postDao.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/delete")
    public  String delete(@RequestParam(name = "id") long id){
        Post post = postDao.findOne(id);
        postDao.delete(post);
        return "redirect:/posts";
    }

}
