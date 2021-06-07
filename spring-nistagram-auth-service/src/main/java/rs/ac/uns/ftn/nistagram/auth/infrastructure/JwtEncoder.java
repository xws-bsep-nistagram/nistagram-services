package rs.ac.uns.ftn.nistagram.auth.infrastructure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.auth.config.PropertyConfiguration;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthToken;

import java.io.IOException;
import java.util.Map;

@Component
public class JwtEncoder {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtEncoder(PropertyConfiguration properties) {
        this.algorithm = Algorithm.HMAC256(properties.getJwtSecret());
        this.verifier = JWT.require(algorithm).build();
    }

    public String encode(AuthToken authToken) throws IOException {
        return JWT.create()
                .withClaim("username", authToken.getUsername())
                .withClaim("expires", "")
                .sign(algorithm);
    }

    public Map<String, Claim> decode(String jwt) {
        verifier.verify(jwt);
        DecodedJWT decoded = JWT.decode(jwt);
        return decoded.getClaims();
    }

}
