package rs.ac.uns.ftn.nistagram.auth.service;

import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.auth.domain.ApiToken;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.apitokens.ApplicationAlreadyRegisteredException;
import rs.ac.uns.ftn.nistagram.auth.repository.ApiTokenRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ApiTokenService {

    private final ApiTokenRepository apiTokenRepository;

    @Transactional
    public ApiToken create(String packageName, String agent) {
        assertApplicationNotRegistered(packageName);
        ApiToken apiToken = new ApiToken();
        apiToken.setAgent(agent);
        apiToken.setPackageName(packageName);
        return apiTokenRepository.save(apiToken);
    }

    private void assertApplicationNotRegistered(String packageName) {
        Optional<ApiToken> existingApiToken = apiTokenRepository.findByPackageName(packageName);
        if (existingApiToken.isPresent())
            throw new ApplicationAlreadyRegisteredException(packageName);
    }

    @Transactional
    public void revokeApiToken(String packageName) {
        apiTokenRepository.deleteByPackageName(packageName);
    }
}
