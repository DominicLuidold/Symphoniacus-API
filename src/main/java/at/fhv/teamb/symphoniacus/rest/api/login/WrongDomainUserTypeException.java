package at.fhv.teamb.symphoniacus.rest.api.login;

public class WrongDomainUserTypeException extends Exception {

    WrongDomainUserTypeException() {
        super("Domain user type is not a Musician.");
    }
}
