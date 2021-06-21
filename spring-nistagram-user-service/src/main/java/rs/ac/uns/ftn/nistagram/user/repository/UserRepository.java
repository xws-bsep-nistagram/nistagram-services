package rs.ac.uns.ftn.nistagram.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByUsernameContains(String usernameQuery);

}
