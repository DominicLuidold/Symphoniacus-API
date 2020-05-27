package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.application.dto.LoginUserDto;
import at.fhv.teamb.symphoniacus.rest.models.Credentials;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponse;
import at.fhv.teamb.symphoniacus.rest.models.JsonHelper;
import at.fhv.teamb.symphoniacus.rest.models.JwToken;
import at.fhv.teamb.symphoniacus.rest.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import java.nio.file.Paths;
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
     * asdf.
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
                    new CustomResponse.CustomResponseBuilder("success", 200)
                    .withPayload(new JwToken(token))
                    .build();


            return Response.ok(tokeResponse.toJson()).build();

        } catch (Exception e) {
            CustomResponse errorResponse = new CustomResponse
                    .CustomResponseBuilder("The provided login credentials are invalid", 401)
                    .build();

            return Response
                    .status(Response.Status.FORBIDDEN)
                    .type("text/json")
                    .entity(errorResponse.toJson())
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
        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                new Payload(data));

        /* // For generating random Keys.
        byte[] sharedKey = new byte[32];
        new SecureRandom().nextBytes(sharedKey);
         */
        String key = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<?, ?> map = mapper.readValue(
                    Paths.get(
                            System.getProperty("user.dir"),
                            "/src/main/resources/Api-Key.json"
                    ).toFile(), Map.class);

            key = (String) map.get("key");

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        jwsObject.sign(new MACSigner(key));

        return jwsObject.serialize();
    }
}
