package org.example.chatapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatapp.entity.Attachment;
import org.example.chatapp.repo.AttachmentRepo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final AttachmentRepo attachmentRepo;
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getAttachmentContent(@PathVariable UUID id) {
        Attachment attachment = attachmentRepo.findById(id).orElseThrow(()->new RuntimeException("file no"));
        if (attachment != null && attachment.getContent() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Change content type as per your attachment type
                    .body(attachment.getContent());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
