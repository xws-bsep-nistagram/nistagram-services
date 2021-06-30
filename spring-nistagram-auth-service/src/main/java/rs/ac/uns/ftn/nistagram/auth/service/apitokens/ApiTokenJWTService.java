package rs.ac.uns.ftn.nistagram.auth.service.apitokens;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.auth.config.PropertyConfiguration;
import rs.ac.uns.ftn.nistagram.auth.domain.ApiToken;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.apitokens.InvalidApiTokenJWT;

@Component
public class ApiTokenJWTService {
    private final Algorithm _algorithm;

    public ApiTokenJWTService(PropertyConfiguration config) {
        _algorithm = Algorithm.HMAC256(config.getJwtSecret());
    }

    private static final String PACKAGE_NAME_CLAIM = "packageName";
    private static final String AGENT_CLAIM = "agent";
    private static final String ID_CLAIM = "id";

    public ApiToken decode(String jwt) throws InvalidApiTokenJWT {
        JWTVerifier verifier = JWT.require(_algorithm).build();
        try {
            verifier.verify(jwt);
        }
        catch (JWTVerificationException e) {
            throw new InvalidApiTokenJWT();
        }
        DecodedJWT decodedJWT = JWT.decode(jwt);
        return new ApiToken(
                decodedJWT.getClaim(ID_CLAIM).asLong(),
                decodedJWT.getClaim(PACKAGE_NAME_CLAIM).asString(),
                decodedJWT.getClaim(AGENT_CLAIM).asString()
        );
    }

    public String encode(ApiToken apiToken) {
        return JWT.create()
                .withClaim(PACKAGE_NAME_CLAIM, apiToken.getPackageName())
                .withClaim(AGENT_CLAIM, apiToken.getAgent())
                .withClaim(ID_CLAIM, apiToken.getId())
                .sign(_algorithm);
    }
}
