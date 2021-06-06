package rs.ac.uns.ftn.nistagram.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.user.repository.UserRepository;

@Service
public class ProfileService {

    private final UserRepository repository;

    public ProfileService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public User get(String username) {
        return repository.findById(username).orElseThrow(() ->
            new EntityNotFoundException(
                    String.format("Profile for username '%s' doesn't exist!", username)
            )
        );
    }

}
