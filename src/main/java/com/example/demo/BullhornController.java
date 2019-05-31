package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class BullhornController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;


    @RequestMapping("/")
    public String listMessages(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
//        if (userService.getUser() != null) {
//            model.addAttribute("user_id", userService.getUser().getId());
//        }
        return "list";
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

