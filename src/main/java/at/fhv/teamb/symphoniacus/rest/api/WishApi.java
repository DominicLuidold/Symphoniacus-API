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

@Path("/wishes")
public class WishApi {

    @GET
    @Secured
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse getAllWishesOfUser(@Context SecurityContext securityContext) {
        return new CustomResponseBuilder<Void>("getAllWishesOfUser", 200).build();
    }

    @POST
    @Secured
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse addNewWish(@Context SecurityContext securityContext) {
        return new CustomResponseBuilder<Void>("addNewWish", 200).build();
    }

    @GET
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse getWishDetails(@PathParam("id") Integer id,
                                          @Context SecurityContext securityContext) {
        return new CustomResponseBuilder<Void>("getWishDetails", 200).build();
    }

    @PUT
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse updateWishDetails(@PathParam("id") Integer id,
                                             @Context SecurityContext securityContext) {
        return new CustomResponseBuilder<Void>("updateWishDetails", 200).build();
    }

    @DELETE
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse deleteWish(@PathParam("id") Integer id,
                                          @Context SecurityContext securityContext) {
        return new CustomResponseBuilder<Void>("deleteWish", 200).build();
    }

}