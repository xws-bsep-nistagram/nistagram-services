package rs.ac.uns.ftn.nistagram.notification.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.notification.domain.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "select n from Notification n where n.target = ?1 and n.seen = 0")
    List<Notification> findByUsername(String username);

    @Query(value = "select n from Notification n where n.target = ?1 or n.subject = ?1")
    List<Notification> findAllContaining(String username);

}
