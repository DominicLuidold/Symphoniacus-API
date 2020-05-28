package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.rest.configuration.jwt.Secured;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponse;
import at.fhv.teamb.symphoniacus.rest.models.CustomResponseBuilder;
import at.fhv.teamb.symphoniacus.rest.service.DutyService;
import java.security.Principal;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

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
    public CustomResponse getAllDuties(@Context SecurityContext securityContext) {
        //To get the current logged in Username
        Principal principal = securityContext.getUserPrincipal();
        String username = principal.getName();

        Set<DutyDto> duties = this.dutyService.getAllDuties(Integer.valueOf(username));

        if (duties != null) {
            return new CustomResponseBuilder<Set<DutyDto>>("success", 200)
                    .withPayload(duties)
                    .build();
        }

        return new CustomResponseBuilder<Void>("Client Failure", 400)
                .withMessage("No Duties Found")
                .build();
    }

    /**
     * Get a specific Duty to a given Id.
     *
     * @param id of the Duty to return.
     */
    @GET
    @Secured
    @Path("/{id  : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse getDuty(@PathParam("id") Integer id,
                                  @Context SecurityContext securityContext) {
        DutyDto duty = this.dutyService.getDuty(id);

        if (duty != null) {
            return new CustomResponseBuilder<DutyDto>("success", 200)
                    .withPayload(duty)
                    .build();
        }

        return new CustomResponseBuilder<Void>("Client Failure", 404)
                .withMessage("Duty not Found")
                .build();
    }

    /**
     * Get all wishes of a given duty ID.
     *
     * @param id of the Duty.
     */
    @GET
    @Secured
    @Path("/{id  : \\d+}/wishes")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse getAllWishesOfDuty(@PathParam("id") Integer id,
                                  @Context SecurityContext securityContext) {

        return new CustomResponseBuilder<Void>("success", 200)
                .withMessage("getAllWishesOfDutyID: " + id)
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
    @Path("/{d_id  : \\d+}/wishes/{w_id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse getOneWishesOfDuty(@PathParam("d_id") Integer dutyId,
                                             @PathParam("w_id") Integer wishId,
                                             @Context SecurityContext securityContext) {

        return new CustomResponseBuilder<Void>("success", 200)
                .withMessage("get one wish with id: " + wishId + " and duty id: " + dutyId)
                .build();
    }
}
