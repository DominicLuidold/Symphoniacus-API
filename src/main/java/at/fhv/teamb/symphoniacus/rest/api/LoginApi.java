package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.application.dto.LoginUserDto;
import at.fhv.teamb.symphoniacus.rest.models.Credentials;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponse;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponseBuilder;
import at.fhv.teamb.symphoniacus.rest.models.JsonHelper;
import at.fhv.teamb.symphoniacus.rest.models.JwToken;
import at.fhv.teamb.symphoniacus.rest.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/login")
public class LoginApi {
    /**
     * Login endpoint to get a valid JWT to given valid Username and Password in the Post Request.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        try {

            // Authenticate the user using the credentials provided
            LoginUserDto user = authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(user);

            // Return the token on the response
            CustomResponse<JwToken> tokeResponse =
                    new CustomResponseBuilder<JwToken>("success", 200)
                    .withPayload(new JwToken(token))
                    .build();


            return Response.ok(tokeResponse).build();

        } catch (Exception e) {
            CustomResponse<Void> errorResponse =
                    new CustomResponseBuilder<Void>(
                            "Client Failure", 401
                    ).withMessage("The provided login credentials are invalid")
                    .build();

            return Response
                    .status(Response.Status.FORBIDDEN)
                    .type("text/json")
                    .entity(errorResponse)
                    .build();
        }
    }


    /**
     *  Authenticate the user against the Database.
     *
     * @return a LoginUserDto of the user if the given Parameters are valid.
     */
    private LoginUserDto authenticate(String username, String password) throws Exception {
        LoginService loginService = new LoginService();
        Optional<LoginUserDto> loginUser = loginService.login(username, password);

        if (loginUser.isEmpty()) {
            throw new Exception();
        } else {
            return loginUser.get();
        }
    }

    /**
     * Generate  a User JsonWebToken and returns it in String representation.
     *
     * @param user User for which the Token is issued.
     * @return String representation of a JWT
     */
    private String issueToken(LoginUserDto user) throws JOSEException {
        String data = JsonHelper.toJson(user);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("Symphoiacus")
                .expirationTime(new Date(new Date().getTime() + 3600 * 4 * 1000))
                .claim("username", user.getUserShortcut())
                .claim("fullName", user.getFullName())
                .claim("userType", user.getType())
                .build();

        SignedJWT signedJwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        /* // For generating random Keys.
        byte[] sharedKey = new byte[32];
        new SecureRandom().nextBytes(sharedKey);
         */
        String key = null;

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File keyFile = new File(classLoader.getResource("Api-Key.json").getFile());

            ObjectMapper mapper = new ObjectMapper();
            Map<?, ?> map = mapper.readValue(
                    keyFile, Map.class);

            key = (String) map.get("key");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JWSSigner signer = new MACSigner(key);
        signedJwt.sign(signer);

        return signedJwt.serialize();
    }
}