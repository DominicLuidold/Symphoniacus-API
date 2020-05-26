package at.fhv.teamb.symphoniacus.rest.models;

public class JwToken {
    private final String token;

    public JwToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
