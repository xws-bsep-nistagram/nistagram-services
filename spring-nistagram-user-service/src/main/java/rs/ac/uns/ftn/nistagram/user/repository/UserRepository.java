package rs.ac.uns.ftn.nistagram.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {
}
