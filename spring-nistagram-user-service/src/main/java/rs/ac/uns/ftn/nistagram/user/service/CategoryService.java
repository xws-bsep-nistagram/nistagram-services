package rs.ac.uns.ftn.nistagram.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.verification.Category;
import rs.ac.uns.ftn.nistagram.user.repository.verification.CategoryRepository;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<Category> getAll() {
        log.info("Fetching all categories for user verification");
        return repository.findAll();
    }

}
