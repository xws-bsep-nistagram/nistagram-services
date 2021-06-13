package rs.ac.uns.ftn.nistagram.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.nistagram.auth.domain.Credentials;

import java.util.Optional;

public interface CredentialsRepository extends JpaRepository<Credentials, String> {

    Optional<Credentials> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Credentials> findByUuid(String uuid);

}
