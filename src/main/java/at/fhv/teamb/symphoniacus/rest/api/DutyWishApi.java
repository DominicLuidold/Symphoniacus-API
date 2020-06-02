package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DateWishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DutyWishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishDto;
import at.fhv.teamb.symphoniacus.rest.configuration.jwt.Secured;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponseBuilder;
import at.fhv.teamb.symphoniacus.rest.service.DutyWishService;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Set;

/**
 * API for {@link DateWishDto}.
 *
 * @author Valentin Goronjic
 */
@Path("/dutywishes")
@Singleton
public class DutyWishApi {

    private DutyWishService dutyWishService = new DutyWishService();

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFutureDutyWishesOfUser(@Context SecurityContext securityContext) {
        //To get the current logged in Username
        Principal principal = securityContext.getUserPrincipal();
        Integer userId = Integer.valueOf(principal.getName());

        Set<WishDto<DutyWishDto>> wishes = this.dutyWishService.getAllFutureDutyWishesOfUser(
            userId
        );

        Response.ResponseBuilder rb = Response
            .status(Response.Status.OK)
            .type("text/json");
        CustomResponseBuilder<Set<WishDto<DutyWishDto>>> crb =
            new CustomResponseBuilder<>(
            "success",
            200
        );

        if (wishes.isEmpty()) {
            rb = rb.entity(
                crb
                    .withMessage("Cant find any Duty wishes.")
                    .build()
            );
        } else {
            rb = rb.entity(
                crb
                    .withPayload(wishes)
                    .build()
            );
        }

        return rb.build();
    }
}