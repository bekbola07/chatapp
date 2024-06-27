package org.example.chatapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String body;
    @CreationTimestamp
    private LocalDateTime send_at;
    private boolean isViewed;
    @OneToOne
    private Attachment attachment;
    @ManyToOne
    private User from;
    @ManyToOne
    private User to;
}
