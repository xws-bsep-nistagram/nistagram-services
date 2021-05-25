package rs.ac.uns.ftn.nistagram.auth.infrastructure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.auth.config.PropertyConfiguration;

import java.io.IOException;

@Component
public class JwtEncoder {

    private static final String JWT_CLAIM = "user";
    private final Algorithm algorithm;
    private final ObjectMapper mapper;
    private final JWTVerifier verifier;

    public JwtEncoder(PropertyConfiguration properties, ObjectMapper mapper) {
        this.algorithm = Algorithm.HMAC256(properties.getJwtSecret());
        this.verifier = JWT.require(algorithm).build();
        this.mapper = mapper;
    }

    public String encrypt(Object object) throws IOException {
        String serialized = mapper.writeValueAsString(object);
        return JWT.create()
                .withClaim(JWT_CLAIM, serialized)
                .sign(algorithm);
    }

    public <T> T decrypt(String encrypted, Class<T> targetClass) throws IOException {
        verifier.verify(encrypted);
        DecodedJWT decoded = JWT.decode(encrypted);
        String claim = decoded.getClaim(JWT_CLAIM).asString();
        return mapper.readValue(claim, targetClass);
    }

}
