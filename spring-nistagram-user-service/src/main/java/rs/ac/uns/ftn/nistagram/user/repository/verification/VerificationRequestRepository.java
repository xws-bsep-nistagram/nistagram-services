package rs.ac.uns.ftn.nistagram.user.repository.verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.nistagram.user.domain.verification.VerificationRequest;
import rs.ac.uns.ftn.nistagram.user.domain.verification.VerificationStatus;

import java.util.List;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {

    boolean existsByUserUsername(@Param("username") String username);

    List<VerificationRequest> findByStatus(VerificationStatus status);

}
