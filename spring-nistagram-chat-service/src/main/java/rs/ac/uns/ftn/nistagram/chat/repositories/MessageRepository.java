package rs.ac.uns.ftn.nistagram.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.chat.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
