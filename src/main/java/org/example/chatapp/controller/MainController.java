package org.example.chatapp.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.chatapp.entity.Message;
import org.example.chatapp.entity.User;
import org.example.chatapp.repo.MessageRepo;
import org.example.chatapp.repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserRepo userRepo;
    private final MessageRepo messageRepo;

    @GetMapping("/")
    public String mainPage(Model model,
                           @RequestParam(required = false) Integer id,
                           Authentication authentication,
                           HttpSession session){
        model.addAttribute("users", userRepo.findAll());
        if (id != null){
            User chosenUser = userRepo.findById(id).orElseThrow(() -> new RuntimeException("user no"));
            model.addAttribute("chosenUser", chosenUser);
            session.setAttribute("chosenUser",chosenUser);
            List<Message> messages = messageRepo.findMessagesBetweenUsers(chosenUser.getId(), ((User) authentication.getPrincipal()).getId());

            model.addAttribute("messages",messages);
        }else if (session.getAttribute("chosenUser")!=null){
            User chosenUser = (User) session.getAttribute("chosenUser");
            model.addAttribute("chosenUser", chosenUser);

            List<Message> messages = messageRepo.findMessagesBetweenUsers(chosenUser.getId(), ((User) authentication.getPrincipal()).getId());

            model.addAttribute("messages",messages);
        }
        return "/index";
    }
}
