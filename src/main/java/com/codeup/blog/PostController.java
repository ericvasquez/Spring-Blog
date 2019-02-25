package com.codeup.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {


    @GetMapping("/posts")
    @ResponseBody
public String index(){
        return "posts index page";
    }

//view an individual post
    @GetMapping("/posts/{id}")
            @ResponseBody
    public String show(@PathVariable long id){
        return "posts index page" + id;
    }

    //view the form for creating a post
    @GetMapping("/posts/create")
   @ResponseBody
        public String showForm(){
        return "view the form for creating a post";

    }
//create a new post
    //same URL that shows form is the same as creates
    @PostMapping("/posts/create")
    @ResponseBody
    public String create(@RequestParam(name = "title") String title){
        return "create post with title" + title;
    }

}
