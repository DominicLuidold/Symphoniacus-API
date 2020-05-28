package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.rest.configuration.jwt.Secured;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponseBuilder;
import at.fhv.teamb.symphoniacus.rest.models.wish.DateWish;
import at.fhv.teamb.symphoniacus.rest.models.wish.Wish;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * API class for {@link DateWish}.
 *
 * @author Tobias Moser
 */
@Path("/datewishes")
public class DateWishApi {

    /**
     * Get all date wishes of a User.
     */
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWishesOfUser(@Context SecurityContext securityContext) {
        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<Void>("Get all date wishes of a User", 200)
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
    public Response addNewWish(@Context SecurityContext securityContext, Wish<DateWish> dateWish) {
        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<Void>("Add a new date wish", 200)
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

        return Response
                .status(Response.Status.OK)
                .type("text/json")
                .entity(new CustomResponseBuilder<Void>("Get date wish details", 200)
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