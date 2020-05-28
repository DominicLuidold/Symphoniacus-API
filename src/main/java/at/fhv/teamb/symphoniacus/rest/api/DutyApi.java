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
     * Get a specific Duty to a given Id.
     *
     * @param id of the Duty to return.
     * @param securityContext to get the current Logged in UserId.
     * @return
     */
    @GET
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomResponse getDuty(@PathParam("id") Integer id,
                                  @Context SecurityContext securityContext) {
        DutyDto duty = dutyService.getDuty(id);

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

        Set<DutyDto> duties = dutyService.getAllDuties(Integer.valueOf(username));

        if (duties != null) {
            return new CustomResponseBuilder<Set<DutyDto>>("success", 200)
                    .withPayload(duties)
                    .build();
        }

        return new CustomResponseBuilder<Void>("Client Failure", 400)
                .withMessage("No Duties Found")
                .build();
    }
}
