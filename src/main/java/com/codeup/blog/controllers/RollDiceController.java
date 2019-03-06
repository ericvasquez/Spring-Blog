package com.codeup.blog.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
       public String roll(){
        return "roll-dice";
    }

    @GetMapping("roll-dice/{n}")
    public String guessCheck(@PathVariable int n, Model model){
        int random = (int) Math.ceil(Math.random () *6 );
        model.addAttribute("random", random);
        model.addAttribute("n", n);
      boolean guess = false;
        if(random == n){
        guess=true;
        }
        model.addAttribute("guess", guess);
        return "results";
    }
}
