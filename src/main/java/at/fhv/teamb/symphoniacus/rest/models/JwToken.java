package at.fhv.teamb.symphoniacus.rest.models;

/**
 * Model for Json Web Token to add to a {@CustomResponse}.
 *
 * @author Tobias Moser
 **/

public class JwToken {
    private final String token;

    public JwToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
