package rs.ac.uns.ftn.nistagram.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.chat.domain.ChatSession;

import java.util.List;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    @Query(value = "select distinct cs from ChatSession cs where cs.initiatorUsername=:sender " +
            "and cs.recipientUsername=:receiver or cs.initiatorUsername=:receiver " +
            "and cs.recipientUsername=:sender")
    ChatSession findByParticipants(String sender, String receiver);

    @Query(value = "select cs from ChatSession cs where cs.initiatorUsername=:username " +
            "or cs.recipientUsername=:username")
    List<ChatSession> findByUsername(String username);
}
