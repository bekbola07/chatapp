package org.example.chatapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatapp.entity.Attachment;
import org.example.chatapp.entity.Message;
import org.example.chatapp.entity.User;
import org.example.chatapp.repo.AttachmentRepo;
import org.example.chatapp.repo.MessageRepo;
import org.example.chatapp.repo.UserRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final UserRepo userRepo;
    private final AttachmentRepo attachmentRepo;
    private final MessageRepo messageRepo;
    @PostMapping("/send")
    public String sendMessage(@AuthenticationPrincipal UserDetails currentUser,
                              @RequestParam(value = "to", required = false) Integer toUserId,
                              @RequestParam("body") String body,
                              @RequestParam("attachment") MultipartFile attachment,
                              Model model) {
        User fromUser = userRepo.findByUsername(currentUser.getUsername()).orElseThrow();
        User toUser = userRepo.findById(toUserId).orElseThrow(() -> new IllegalArgumentException("no user"));

        Message message = new Message();
        message.setFrom(fromUser);
        message.setTo(toUser);
        message.setBody(body);
        message.setSend_at(LocalDateTime.now());

        if (!attachment.isEmpty()) {
            Attachment fileAttachment = new Attachment();
            fileAttachment.setFileName(attachment.getOriginalFilename());
            fileAttachment.setFileType(attachment.getContentType());
            try {
                fileAttachment.setContent(attachment.getBytes());
            } catch (IOException e) {
                model.addAttribute("errorMessage", "Error uploading file");
            }
            fileAttachment = attachmentRepo.save(fileAttachment);
            message.setAttachment(fileAttachment);
        }

        messageRepo.save(message);
        return "redirect:/";  // Redirect to the appropriate page
    }
}
