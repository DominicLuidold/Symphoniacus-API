package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.rest.configuration.jwt.Secured;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponse;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponseBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/datewishes")
public class DateWishApi {

    /**
     * Get all date wishes of a User.
     */
    @GET
    @Secured
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse getAllWishesOfUser(@Context SecurityContext securityContext) {
        return new CustomResponseBuilder<Void>("Get all date wishes of a User", 200).build();
    }

    /**
     * Add a new date wish.
     */
    @POST
    @Secured
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse addNewWish(@Context SecurityContext securityContext) {
        return new CustomResponseBuilder<Void>("Add a new date wish", 200).build();
    }

    /**
     * Get the details of a date wish.
     */
    @GET
    @Secured
    @Path("/{id  : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse getWishDetails(@PathParam("id") Integer id,
                                          @Context SecurityContext securityContext) {
        return new CustomResponseBuilder<Void>("Get date wish details", 200).build();
    }

    /**
     * Update data of a existing date wish.
     */
    @PUT
    @Secured
    @Path("/{id  : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse updateWishDetails(@PathParam("id") Integer id,
                                             @Context SecurityContext securityContext) {
        return new CustomResponseBuilder<Void>("updateWishDetails", 200).build();
    }

    /**
     * Delete a date wish of the given ID.
     */
    @DELETE
    @Secured
    @Path("/{id  : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse deleteWish(@PathParam("id") Integer id,
                                          @Context SecurityContext securityContext) {
        return new CustomResponseBuilder<Void>("delete date Wish", 200).build();
    }

}