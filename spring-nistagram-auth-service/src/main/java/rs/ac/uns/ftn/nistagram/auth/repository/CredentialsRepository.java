package rs.ac.uns.ftn.nistagram.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.auth.domain.Credentials;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, String> {

    Optional<Credentials> findByUsername(String username);

    Optional<Credentials> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUuid(String uuid);

    Optional<Credentials> findByUuid(String uuid);

}
