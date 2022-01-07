package site.alex.konon.sol.telegramBot.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import site.alex.konon.sol.telegramBot.services.TokenService;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Override
    public boolean checkToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            if (!decodedJWT.getIssuer().equals("auth-telegram_bot")) {
                logger.error("Issuer is incorrect");
                return false;
            }

            if (!decodedJWT.getAudience().contains("android")) {
                logger.error("Audience is incorrect");
                return false;
            }
        } catch (JWTVerificationException e) {
            logger.error("Token is invalid: " + e.getMessage());
            return false;
        }

        return true;
    }
    @Override
    public String generateAuthToken(String clientId) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        Instant now = Instant.now();
        Instant exp = now.plus(6, ChronoUnit.MONTHS);

        return JWT.create()
                .withIssuer("auth-telegram_bot")
                .withAudience("android")
                .withSubject(clientId)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm);
    }

    @Override
    public  String getRandom(){
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        String secretKey = new BigInteger(1, bytes).toString(16);
        return secretKey;
    }


}
