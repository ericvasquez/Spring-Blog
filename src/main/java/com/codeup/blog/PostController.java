package com.codeup.blog;

import models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repositories.PostRepository;

@Controller
public class PostController {
   // private final PostRepository postDao;

// public PostController(PostRepository postDao){
//     this.postDao = postDao;
// }

 //shows all posts
    @GetMapping("/posts")
public String all(Model model){
    Iterable<Post> posts = postDao.findAll();
     model.addAttribute("posts", posts);
        return "posts/index";
    }

//view an individual post
    //gets number out of url
    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model model){
     Post post = postDao.findOne(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    //view the form for creating a post
    //there's no data, it only shows the form
    @GetMapping("/posts/create")
        public String showForm(){
        return "posts/create";

    }
//save the new post to the database
 //   same URL that shows form is the same as creates
//    @PostMapping("/posts/create")
//    @ResponseBody
//    public String create(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body){
//     Post post = new Post(title, body);
//     postDao.save(post);
//        return "redirect:/posts";
//    }
@PostMapping("/posts/create")
public String create(
        @RequestParam(name = "title") String title,
        @RequestParam(name = "body") String body
){

    Post post = new Post(title, body);
    postDao.save(post);
    return "redirect:/posts";

}
    //make get request to bring up post object in a form to edit it/ return edit view
    @GetMapping("/posts/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        Post post = postDao.findOne(id);
        model.addAttribute("post", post);
        return "posts/edit";

    }
    //saves the edited post
    @PostMapping("/posts/{id}/edit")
    public String update(
            @PathVariable long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body,
            Model model) {

        Post post = postDao.findOne(id);
        post.setTitle(title);
        post.setBody(body);
        postDao.save(post);

        return "redirect:/posts/" + id;
    }

    //delete a post
    @PostMapping("/posts/delete")
    public String delete(@RequestParam(name = "id") long id) {
        Post post = postDao.findOne(id);
        postDao.delete(post);
        return "redirect:/posts";
    }

    // show a random post
    @GetMapping("/posts/random")
    public String random(Model model) {
         Post post = postDao.getRandom();
         model.addAttribute("post", post);
        return "posts/show";
    }
}
