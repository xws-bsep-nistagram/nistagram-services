package rs.ac.uns.ftn.nistagram.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.auth.repository.CredentialsRepository;

@Service
public class CredentialsService implements UserDetailsService {

    private final CredentialsRepository repository;

    public CredentialsService(CredentialsRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));
    }

}
