package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DateWishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DutyWishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishDto;
import at.fhv.teamb.symphoniacus.rest.configuration.jwt.Secured;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponseBuilder;
import at.fhv.teamb.symphoniacus.rest.service.DutyService;
import at.fhv.teamb.symphoniacus.rest.service.DutyWishService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * API class for {@link DutyDto}.
 *
 * @author Valentin Goronjic
 * @author Tobias Moser
 */
@Singleton
@Path("/duties")
public class DutyApi {
    private static final Logger LOG = LogManager.getLogger(DutyApi.class);
    private DutyService dutyService = new DutyService();
    private DutyWishService dutyWishService = new DutyWishService();

    /**
     * All Duties which is the current logged in User is assigned in a position.
     *
     * @return all Duties which is the current logged in User is assigned in a position
     */
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDuties(@Context SecurityContext securityContext) {
        //To get the current logged in Username
        Principal principal = securityContext.getUserPrincipal();
        Integer userID = Integer.valueOf(principal.getName());

        Set<DutyDto> duties = this.dutyService.getAllDuties(userID);
        if (duties == null) {
            LOG.debug("No Duties found.");
            duties = new HashSet<>();
            return Response
                    .status(Response.Status.OK)
                    .type("text/json")
                    .entity(new CustomResponseBuilder<Set<DutyDto>>("failure", 200)
                            .withMessage("Duties not Found")
                            .withPayload(duties)
                            .build()
                    )
                    .build();
        }
        LOG.debug("Duties found.");
        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<Set<DutyDto>>("success", 200)
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
    @Path("/{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDuty(
            @PathParam("id") Integer id,
            @Context SecurityContext securityContext
    ) {
        Optional<DutyDto> duty = this.dutyService.getDuty(id);

        if (duty.isEmpty()) {
            LOG.debug("No Duty found.");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .type("text/json")
                    .entity(new CustomResponseBuilder<Void>("failure", 200)
                            .withMessage("Duty not Found")
                            .build()
                    )
                    .build();
        }
        LOG.debug("Found Duty");
        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<DutyDto>("success", 200)
                        .withPayload(duty.get())
                        .build()
                )
                .build();
    }

    /**
     * Get all wishes of a given duty ID.
     *
     * @param dutyId of the Duty.
     */
    @GET
    @Secured
    @Path("/{id : \\d+}/wishes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDutyWishesOfUserAndDuty(
            @PathParam("id") Integer dutyId,
            @Context SecurityContext securityContext
    ) {
        Principal principal = securityContext.getUserPrincipal();
        Integer userID = Integer.valueOf(principal.getName());

        Set<WishDto<DutyWishDto>> dutyWishes =
                this.dutyWishService.getAllDutyWishesOfUserAndDuty(dutyId, userID);

        Response.ResponseBuilder rb = Response
                .status(Response.Status.OK)
                .type("text/json");
        CustomResponseBuilder<Set<WishDto<DutyWishDto>>> crb =
                new CustomResponseBuilder<>("success", 200);

        if (dutyWishes.isEmpty()) {
            rb = rb
                    .entity(
                            crb
                                    .withMessage("No Duty Requests found")
                                    .build()
                    );

        }

        rb = rb
                .entity(
                        crb
                                .withPayload(dutyWishes)
                                .build()
                );

        return rb.build();
    }

    /**
     * Get one specific wish of a specific duty.
     *
     * @param dutyId of the given duty.
     * @param wishId of the requested duty.
     */
    @GET
    @Secured
    @Path("/{d_id : \\d+}/wishes/{w_id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneDutyWish(
            @PathParam("d_id") Integer dutyId,
            @PathParam("w_id") Integer wishId,
            @Context SecurityContext securityContext
    ) {

        Optional<WishDto<DutyWishDto>> dutyWish =
                this.dutyWishService.getOneDutyWish(dutyId);

        if (dutyWish.isPresent()) {
            return Response
                    .status(Response.Status.OK)
                    .type("text/json")
                    .entity(new CustomResponseBuilder<WishDto<DutyWishDto>>("success", 200)
                            .withPayload(dutyWish.get())
                            .build()
                    )
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<Set<WishDto<DutyWishDto>>>("success", 200)
                        .withMessage("No Duty Requests found")
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
    @Path("/{d_id : \\d+}/wishes/{w_id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDutyWish(
            @PathParam("d_id") Integer dutyId,
            @PathParam("w_id") Integer wishId,
            WishDto<DutyWishDto> dutyWish,
            @Context SecurityContext securityContext
    ) {
        Principal principal = securityContext.getUserPrincipal();
        Integer userID = Integer.valueOf(principal.getName());

        Optional<WishDto<DutyWishDto>> updatedWish =
                this.dutyWishService.updateDutyWish(dutyWish, userID);

        if (updatedWish.isPresent()) {
            return Response
                    .status(Response.Status.OK)
                    .type("text/json")
                    .entity(new CustomResponseBuilder<WishDto<DutyWishDto>>("success", 200)
                            .withPayload(updatedWish.get())
                            .build()
                    )
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<Set<WishDto<DutyWishDto>>>("success", 200)
                        .withMessage("No Duty Requests found")
                        .build()
                )
                .build();
    }

    /**
     * Create one specific wish of a specific duty.
     *
     * @param dutyId of the given duty.
     */
    @POST
    @Secured
    @Path("/{d_id : \\d+}/wishes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewDutyWish(
            @PathParam("d_id") Integer dutyId,
            WishDto<DutyWishDto> dutyWish,
            @Context SecurityContext securityContext
    ) {

        Principal principal = securityContext.getUserPrincipal();
        Integer userID = Integer.valueOf(principal.getName());

        Optional<WishDto<DutyWishDto>> newWish =
                this.dutyWishService.addNewDutyWish(dutyWish, userID);

        if (newWish.isPresent()) {
            return Response
                    .status(Response.Status.OK)
                    .type("text/json")
                    .entity(new CustomResponseBuilder<WishDto<DutyWishDto>>("success", 201)
                            .withPayload(newWish.get())
                            .build()
                    )
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .type("text/json")
                .entity(new CustomResponseBuilder<WishDto<DutyWishDto>>("success", 201)
                        .withPayload(newWish.get())
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
    @Path("/{d_id : \\d+}/wishes/{w_id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWisheOfDuty(
            @PathParam("d_id") Integer dutyId,
            @PathParam("w_id") Integer wishId,
            @Context SecurityContext securityContext
    ) {

        boolean deleted = this.dutyWishService.deleteDutyWish(wishId);

        if (deleted) {
            return Response
                    .status(Response.Status.OK)
                    .type("text/json")
                    .entity(new CustomResponseBuilder<WishDto<DateWishDto>>("success", 204)
                            .build()
                    )
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .type("text/json")
                .entity(new CustomResponseBuilder<WishDto<DateWishDto>>("failure", 404)
                        .build()
                )
                .build();
    }
}
