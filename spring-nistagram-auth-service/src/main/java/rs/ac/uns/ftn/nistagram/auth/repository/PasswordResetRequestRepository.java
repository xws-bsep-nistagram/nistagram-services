package rs.ac.uns.ftn.nistagram.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetRequest;

import java.util.Optional;

public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, String> {

    @Query(value = "select R from PasswordResetRequest R where R.uuid = :uuid")
    Optional<PasswordResetRequest> findByUUID(@Param("uuid") String uuid);
}
