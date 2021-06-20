package rs.ac.uns.ftn.nistagram.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.nistagram.user.domain.user.request.VerificationRequest;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {

    boolean existsByUserUsername(@Param("username") String username);

}
