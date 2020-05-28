package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.rest.configuration.jwt.Secured;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponseBuilder;
import at.fhv.teamb.symphoniacus.rest.models.wish.Wish;
import at.fhv.teamb.symphoniacus.rest.service.DutyService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

/**
 * API class for {@link DutyDto}.
 *
 * @author Valentin Goronjic
 */
@Path("/duties")
public class DutyApi {

    private DutyService dutyService = new DutyService();

    /**
     * All Duties which is the current logged in User is assigned in a position.
     *
     * @return all Duties which is the current logged in User is assigned in a position
     */
    @GET
    @Secured
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDuties(@Context SecurityContext securityContext) {
        //To get the current logged in Username
        Principal principal = securityContext.getUserPrincipal();
        String username = principal.getName();

        Set<DutyDto> duties = this.dutyService.getAllDuties(Integer.valueOf(username));
        if (duties == null) {
            duties = new HashSet<>();
        }
        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<Set<DutyDto>>("success",200)
                        .withPayload(duties)
                        .build()
                )
                .build();
    }

    /**
     * Get a specific Duty to a given Id.
     *
     * @param id              of the Duty to return.
     * @param securityContext to get the current Logged in UserId.
     * @return
     */
    @GET
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDuty(
            @PathParam("id") Integer id,
            @Context SecurityContext securityContext
    ) {
        DutyDto duty = this.dutyService.getDuty(id);

        if (duty == null) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .type("text/json")
                .entity(new CustomResponseBuilder<Void>("Client Failure", 404)
                    .withMessage("Duty not Found")
                    .build()
                )
                .build();
        }
        return Response
            .status(Response.Status.OK)
            .type("text/json")
            .entity(new CustomResponseBuilder<DutyDto>("success",200)
                .withPayload(duty)
                .build()
            )
            .build();
    }

    /**
     * Get all wishes of a given duty ID.
     *
     * @param id of the Duty.
     */
    @GET
    @Secured
    @Path("/{id}/wishes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWishesOfDuty(@PathParam("id") Integer id,
                                             @Context SecurityContext securityContext) {

        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<DutyDto>("success",200)
                        .withMessage("getAllWishesOfDutyID: " + id)
                        .build()
                )
                .build();

    }

    /**
     * Get one specific wish of a specific duty.
     *
     * @param dutyId of the given duty.
     * @param wishId of the requested duty.
     */
    @GET
    @Secured
    @Path("/{d_id}/wishes/{w_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneWisheOfDuty(@PathParam("d_id") Integer dutyId,
                                             @PathParam("w_id") Integer wishId,
                                             @Context SecurityContext securityContext) {

        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<DutyDto>("success",200)
                        .withMessage("get one wish with id: " + wishId + " and duty id: " + dutyId)
                        .build()
                )
                .build();
    }

    /**
     * Update one specific wish of a specific duty.
     *
     * @param dutyId of the given duty.
     * @param wishId of the requested duty.
     */
    @PUT
    @Secured
    @Path("/{d_id}/wishes/{w_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWisheOfDuty(@PathParam("d_id") Integer dutyId,
                                       @PathParam("w_id") Integer wishId,
                                       Wish<?> wish,
                                       @Context SecurityContext securityContext) {

        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<DutyDto>("success",200)
                        .withMessage("update one wish with id: " + wishId + " duty id: " + dutyId)
                        .build()
                )
                .build();
    }

    /**
     * Delete one specific wish of a specific duty.
     *
     * @param dutyId of the given duty.
     * @param wishId of the requested duty.
     */
    @DELETE
    @Secured
    @Path("/{d_id}/wishes/{w_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWisheOfDuty(@PathParam("d_id") Integer dutyId,
                                      @PathParam("w_id") Integer wishId,
                                      @Context SecurityContext securityContext) {

        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<DutyDto>("success",200)
                        .withMessage("delete one wish with id: " + wishId + " duty id: " + dutyId)
                        .build()
                )
                .build();
    }
}