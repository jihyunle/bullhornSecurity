package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/") //main screen
    public String listMessages(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        if (userService.getCurrentUser() != null) {
            model.addAttribute("user_id", userService.getCurrentUser().getId());
        }
        return "list";
    }

    //============== Security ===========
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "/registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("user",user);
        if(result.hasErrors()) {
            return "registration";
        }
        else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {

//        try {
//
//        } catch ()
        return "login";
    }

//    @GetMapping("/login")
//    public String login2(Model model) {
//        model.addAttribute("user", new User());
//        return "login";
//    }

    @RequestMapping("/secure")
    public String secure(Model model) {
        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", userService.getCurrentUser());
        return "secure";
    }



    //========== message ==================
    @GetMapping("/add")
    public String messageForm(Model model) {
        model.addAttribute("message", new Message());
        return "messageForm";
    }

    @PostMapping("/process")
    public String processForm(@Valid Message message, BindingResult result, Model model) {
        model.addAttribute("message", message);
        if (result.hasErrors()) {
            return "messageForm";
        }
        message.setUser(userService.getCurrentUser());
        messageRepository.save(message);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showMessages(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateMessages(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "messageForm";
    }

    @RequestMapping("/delete/{id}")
    public String delMessages(@PathVariable("id") long id) {
        messageRepository.deleteById(id);
        return "redirect:/";
    }

    //================== User =================
//    @GetMapping("/addUser")
//    public String categoryForm(Model model){
//        model.addAttribute("category", new User());
//        return "categoryForm";
//    }
//    @PostMapping("/processCategory")
//    public String processCategory(@Valid Category category, BindingResult result){
//        if(result.hasErrors()){
//            return "categoryForm";
//        }
//        categoryRepository.save(category);
//        return "redirect:/";
//    }
    @RequestMapping("/deleteUser/{id}")
    public String delUser(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return "redirect:/";
    }


}

