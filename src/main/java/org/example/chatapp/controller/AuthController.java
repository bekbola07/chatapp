package org.example.chatapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatapp.entity.Attachment;
import org.example.chatapp.entity.User;
import org.example.chatapp.repo.AttachmentRepo;
import org.example.chatapp.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentRepo attachmentRepo;
    @GetMapping("/login")
    public String loginPage(){
        return "/auth/login-page";
    }

    @GetMapping("/logout")
    public String logoutPage(){
        return "/auth/logout";
    }
    @PostMapping("/logout")
    public String logout(){
        return "/auth/logout";
    }
    @GetMapping("/register")
    public String registerPage(){
        return "/auth/register";
    }
    @PostMapping("/register")

    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam MultipartFile userImage) throws IOException {

        Attachment attachment = Attachment.builder()
                .content(userImage.getBytes())
                .build();

        attachmentRepo.save(attachment);


        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .attachment(attachment)
                .build();

        userRepo.save(user);

        return "redirect:/auth/login";
    }
}
