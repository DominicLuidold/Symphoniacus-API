package at.fhv.teamb.symphoniacus.rest.models;

import java.io.Serializable;

/**
 * Used for login to map to a Json Object.
 *
 * @author Tobias Moser
 **/
public class Credentials implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
