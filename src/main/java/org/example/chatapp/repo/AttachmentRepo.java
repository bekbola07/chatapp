package org.example.chatapp.repo;

import org.example.chatapp.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepo extends JpaRepository<Attachment, UUID> {
}
