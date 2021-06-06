package rs.ac.uns.ftn.nistagram.feed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
