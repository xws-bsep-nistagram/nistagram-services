package rs.ac.uns.ftn.nistagram.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.nistagram.user.domain.verification.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
