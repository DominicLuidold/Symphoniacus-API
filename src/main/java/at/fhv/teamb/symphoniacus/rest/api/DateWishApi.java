package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.rest.configuration.jwt.Secured;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponseBuilder;
import at.fhv.teamb.symphoniacus.rest.models.wish.DateWishDto;
import at.fhv.teamb.symphoniacus.rest.models.wish.WishDto;
import at.fhv.teamb.symphoniacus.rest.service.DateWishService;

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
import java.util.Optional;
import java.util.Set;

/**
 * API class for {@link DateWishDto}.
 *
 * @author Tobias Moser
 */
@Path("/datewishes")
@Singleton
public class DateWishApi {
    DateWishService dateWishService = new DateWishService();

    /**
     * Get all date wishes of a User.
     */
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWishesOfUser(@Context SecurityContext securityContext) {
        //To get the current logged in Username
        Principal principal = securityContext.getUserPrincipal();
        Integer userID = Integer.valueOf(principal.getName());

        Set<WishDto<DateWishDto>> wishes = this.dateWishService.getAllDateWishes(userID);

        if (wishes.size() == 0) {
            return Response
                    .status(Response.Status.OK)
                    .type("text/json")
                    .entity(new CustomResponseBuilder<Set<WishDto<DateWishDto>>>("success", 200)
                            .withMessage("Cant find any date wishes.")
                            .withPayload(wishes)
                            .build()
                    )
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<Set<WishDto<DateWishDto>>>("success", 200)
                        .withPayload(wishes)
                        .build()
                )
                .build();
    }

    /**
     * Add a new date wish.
     */
    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewWish(
            @Context SecurityContext securityContext,
            WishDto<DateWishDto> dateWish
    ) {
        Optional<WishDto<DateWishDto>> persDateWish = this.dateWishService.addNewDateWish(dateWish);

        if (persDateWish.isPresent()) {
            return Response
                    .status(Response.Status.OK)
                    .type("text/json")
                    .entity(new CustomResponseBuilder<WishDto<DateWishDto>>("success", 201)
                            .withPayload(persDateWish.get())
                            .build()
                    )
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<WishDto<DateWishDto>>("failure", 400)
                        .withMessage("Cant save new date wishe.")
                        .build()
                )
                .build();

    }

    /**
     * Get the details of a date wish.
     */
    @GET
    @Secured
    @Path("/{id  : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWishDetails(@PathParam("id") Integer id,
                                          @Context SecurityContext securityContext) {
        Optional<WishDto<DateWishDto>> persDateWish = this.dateWishService.detailsDateWish(id);

        if (persDateWish.isPresent()) {
            return Response
                    .status(Response.Status.OK)
                    .type("text/json")
                    .entity(new CustomResponseBuilder<WishDto<DateWishDto>>("success", 201)
                            .withPayload(persDateWish.get())
                            .build()
                    )
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<WishDto<DateWishDto>>("failure", 400)
                        .withMessage("Cant find Wish.")
                        .build()
                )
                .build();
    }

    /**
     * Update data of a existing date wish.
     */
    @PUT
    @Secured
    @Path("/{id  : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateWishDetails(@PathParam("id") Integer id, String dateWish,
                                             @Context SecurityContext securityContext) {

        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<Void>("updateWishDetails", 200)
                        .build()
                )
                .build();
    }

    /**
     * Delete a date wish of the given ID.
     */
    @DELETE
    @Secured
    @Path("/{id  : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWish(@PathParam("id") Integer id,
                                          @Context SecurityContext securityContext) {
        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<Void>("delete date Wish", 200)
                        .build()
                )
                .build();
    }
}