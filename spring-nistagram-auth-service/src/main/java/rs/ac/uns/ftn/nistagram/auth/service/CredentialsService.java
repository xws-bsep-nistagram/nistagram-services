package rs.ac.uns.ftn.nistagram.auth.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public Credentials loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));
    }

    @Transactional
    public Credentials registerUser(RegistrationRequest request) {
        return repository.save(generateCredentials(request));
    }

    @Transactional
    public Credentials delete(String username) {
        Credentials found = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));

        repository.delete(found);

        return found;
    }

    private Credentials generateCredentials(RegistrationRequest request) {
        Credentials credentials = new Credentials(request.getUsername(), request.getPassword(), request.getEmail());
        validateRegistration(credentials);
        return credentials;
    }

    private void validateRegistration(Credentials credentials) {
        if (repository.existsByUsername(credentials.getUsername())) {
            throw new BadCredentialsException("Username already taken!");
        } else if (repository.existsByEmail(credentials.getEmail())) {
            throw new BadCredentialsException("E-mail already taken!");
        }
    }

    @Transactional
    public void activate(String uuid) {
        Credentials found = repository.findByUuid(uuid)
                .orElseThrow(() -> new AuthException("User with given UUID not found!"));
        if (found.getActivated()) throw new RuntimeException("Account already activated!");
        found.activate();
        repository.save(found);
    }

    @Transactional
    public void addUserRole(String username, String role) {
        Credentials found = loadUserByUsername(username);
        if (found.hasAuthority(role)) {
            throw new AuthException(String.format("User '%s' already has role %s!", username, role));
        }
        found.addAuthority(role);
        repository.save(found);
    }

}
