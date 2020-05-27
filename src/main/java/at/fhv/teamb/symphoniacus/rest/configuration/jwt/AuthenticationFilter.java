package at.fhv.teamb.symphoniacus.rest.configuration.jwt;

import at.fhv.teamb.symphoniacus.rest.models.CustomResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                .substring(AUTHENTICATION_SCHEME.length()).trim();

        try {

            // Validate the token
            validateToken(token);

        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        CustomResponse errorResponse = new CustomResponse
                .CustomResponseBuilder("Not authorized for this action.", 401)
                .build();

        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .type("text/json")
                        .entity(errorResponse)
                        .build());
    }

    /**
     * Check if the token was issued by the server. Throw an Exception if the token is invalid.
     * @param token given from the User.
     */
    private void validateToken(String token) throws Exception {
        boolean verifiedSignature = false;

        //TODO save key in config file

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

        JWSVerifier verifier = new MACVerifier(key);
        JWSObject jwsObject = JWSObject.parse(token);
        verifiedSignature = jwsObject.verify(verifier);

        if (!verifiedSignature) {
            throw new Exception();
        }
    }
}