package rs.ac.uns.ftn.nistagram.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.auth.domain.Credentials;
import rs.ac.uns.ftn.nistagram.auth.domain.RegistrationRequest;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.AuthException;
import rs.ac.uns.ftn.nistagram.auth.repository.CredentialsRepository;

@Service
public class CredentialsService implements UserDetailsService {

    private final CredentialsRepository repository;

    public CredentialsService(CredentialsRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));
    }

    @Transactional
    public UserDetails registerUser(RegistrationRequest request) {
        return repository.save(generateCredentials(request));
    }

    private Credentials generateCredentials(RegistrationRequest request) {
        Credentials credentials = new Credentials(request.getUsername(), request.getPassword(), request.getEmail());
        validateRegistration(credentials);
        return credentials;
    }

    private void validateRegistration(Credentials credentials) {
        if (repository.existsByUsername(credentials.getUsername())) {
            throw new AuthException("Username already taken!");
        } else if (repository.existsByEmail(credentials.getEmail())) {
            throw new AuthException("E-mail already taken!");
        }
    }

}
