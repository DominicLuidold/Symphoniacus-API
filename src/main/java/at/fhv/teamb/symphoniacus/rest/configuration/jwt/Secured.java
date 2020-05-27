package at.fhv.teamb.symphoniacus.rest.configuration.jwt;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;

/**
 * Annotation Interface to secure a API Endpoint.
 *
 * @author Tobias Moser
 **/

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured {
}