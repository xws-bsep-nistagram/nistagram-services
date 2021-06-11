package rs.ac.uns.ftn.nistagram.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetRequest;

public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, String> {

}
