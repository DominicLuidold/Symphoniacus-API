package at.fhv.teamb.symphoniacus.rest.api;

import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.rest.configuration.jwt.Secured;
import at.fhv.teamb.symphoniacus.rest.service.DutyService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * API class for {@link DutyDto}.
 *
 * @author Valentin Goronjic
 */
@Path("/duty/")
public class DutyApi {

    private DutyService dutyService = new DutyService();

    @GET
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DutyDto getDuty(@PathParam("id") Integer id) {
        return dutyService.getDuty(id);
    }
}
