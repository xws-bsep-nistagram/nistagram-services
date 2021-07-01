package rs.ac.uns.ftn.nistagram.auth.service.apitokens;

import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.auth.domain.ApiToken;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.apitokens.ApiTokenNotFoundException;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.apitokens.ApplicationAlreadyRegisteredException;
import rs.ac.uns.ftn.nistagram.auth.repository.ApiTokenRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ApiTokenService {

    private final ApiTokenRepository apiTokenRepository;
    private final ApiTokenJWTService jwtService;

    @Transactional
    public String createAndEncode(String packageName, String agent) {
        assertApplicationNotRegistered(packageName);
        ApiToken apiToken = new ApiToken();
        apiToken.setAgent(agent);
        apiToken.setPackageName(packageName);
        ApiToken savedApiToken = apiTokenRepository.save(apiToken);
        return jwtService.encode(savedApiToken);
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

    public ApiToken decode(String apiTokenJWT) {
        return jwtService.decode(apiTokenJWT);
    }

    public String get(String agent) {
        ApiToken foundApiToken = apiTokenRepository.getByAgent(agent).orElseThrow(ApiTokenNotFoundException::new);
        return jwtService.encode(foundApiToken);
    }
}
