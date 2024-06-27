package org.example.chatapp.repo;

import org.example.chatapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepo extends JpaRepository<Message, UUID> {
    @Query(value = "SELECT * FROM message m WHERE " +
            "(m.from_id = :user1 AND m.to_id = :user2) OR " +
            "(m.from_id = :user2 AND m.to_id = :user1)", nativeQuery = true)
    List<Message> findMessagesBetweenUsers(@Param("user1") Integer user1, @Param("user2") Integer user2);

}
