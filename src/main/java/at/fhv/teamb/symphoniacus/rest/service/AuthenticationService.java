package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.application.LoginManager;
import at.fhv.teamb.symphoniacus.application.dto.LoginUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AuthenticationService {
    private String key;

    /**
     * Service to process a Login of a User.
     *
     * @return a Optional of a LoginUserDto if the login is valid the Optional is Present.
     */
    public Optional<LoginUserDto> login(String username, String password) throws Exception {
        LoginManager loginManager = new LoginManager();
        LoginUserDto dto = new LoginUserDto.UserDtoBuilder(-1)
                .withUserShortcut(username)
                .withPassword(password)
                .build();


        return loginManager.login(dto);
    }

    /**
     * Generate  a User JsonWebToken and returns it in String representation.
     *
     * @param user User for which the Token is issued.
     * @return String representation of a JWT
     */
    public String issueToken(LoginUserDto user) throws JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("Symphoniacus")
                .expirationTime(new Date(new Date().getTime() + 3600 * 4 * 1000))
                .claim("username", user.getUserShortcut())
                .claim("fullName", user.getFullName())
                .claim("userType", user.getType())
                .claim("userId", user.getUserId())
                .build();

        SignedJWT signedJwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        String key = getKey();
        JWSSigner signer = new MACSigner(key);
        signedJwt.sign(signer);

        return signedJwt.serialize();
    }

    /**
     * Verifies a JWT token given as String.
     *
     * @param token JWT in compact string form
     * @return SignedJWT token if valid, else empty Optioal
     */
    public Optional<SignedJWT> verifyToken(String token) {
        boolean isValid;
        String key = getKey();

        try {
            SignedJWT signedJwt = SignedJWT.parse(token);

            JWSVerifier verifier = new MACVerifier(key);

            isValid = signedJwt.verify(verifier)
                    && (new Date().before(signedJwt.getJWTClaimsSet().getExpirationTime()));


            if (isValid) {
                return Optional.of(signedJwt);
            }
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Reads the Keyfile from the resource folder once so it can be served.
     *
     * @return the Api key of Symphoniacus
     */
    public String getKey() {
        if (this.key == null) {
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                File keyFile = new File(
                        Objects.requireNonNull(
                                classLoader.getResource("Api-Key.json")
                        ).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                Map<?, ?> map = mapper.readValue(keyFile, Map.class);

                this.key = (String) map.get("key");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this.key;
    }
}
