package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.application.dto.LoginUserDto;
import at.fhv.teamb.symphoniacus.rest.models.Credentials;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponse;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponseBuilder;
import at.fhv.teamb.symphoniacus.rest.models.JwToken;
import at.fhv.teamb.symphoniacus.rest.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Singleton
@Path("/login")
public class LoginApi {
    private static final Logger LOG = LogManager.getLogger(LoginApi.class);
    private AuthenticationService authenticationService = new AuthenticationService();

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
            LOG.error(e);
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
     * Authenticate the user against the Database.
     *
     * @return a LoginUserDto of the user if the given Parameters are valid.
     */
    private LoginUserDto authenticate(String username, String password) throws Exception {
        Optional<LoginUserDto> loginUser = this.authenticationService.login(username, password);

        if (loginUser.isEmpty()) {
            LOG.warn("Cant authenticate User {}", username);
            throw new Exception();
        } else {
            LOG.debug("Authenticate User {}", username);
            return loginUser.get();
        }
    }

    /**
     * Gets the Token from the login Service.
     *
     * @param user User for which the Token is issued.
     * @return String representation of a JWT
     */
    private String issueToken(LoginUserDto user) throws JOSEException {
        LOG.debug("Issue Token for User {}", user.getUserShortcut());
        return this.authenticationService.issueToken(user);
    }
}