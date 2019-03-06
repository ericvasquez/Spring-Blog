package com.codeup.blog.controllers;

import com.codeup.blog.Services.EmailService;
import com.codeup.blog.models.Category;
import com.codeup.blog.models.Post;
import com.codeup.blog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.codeup.blog.repositories.PostRepository;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
//    @Autowired
    private final PostRepository postDao;
    public PostController(PostRepository postDao){
        this.postDao=postDao;
    }



    @Autowired
    private EmailService emailService;

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
            User user = new User();
            Post post = new Post(title, body, new User(), categories);
            Post savedPost = postDao.save(post);
            emailService.prepareAndSend(savedPost, "Done", "Its saved");
            return "redirect:/posts";
        }


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
