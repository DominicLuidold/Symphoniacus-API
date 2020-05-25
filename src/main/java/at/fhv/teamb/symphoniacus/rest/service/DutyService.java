package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.domain.Duty;

/**
 * Service implementation for {@link DutyDto}.
 *
 * @author Valentin Goronjic
 */
public class DutyService {

    /**
     * Returns a duty, currently only id, with this id.
     *
     * @param id Id of Duty
     * @return DutyDto
     */
    public DutyDto getDuty(Integer id) {
        // THIS SHOULD RETURN A DTO INSTEAD -> CHANGE THIS
        Duty duty = new DutyManager().loadDutyDetails(id).get();

        return new DutyDto.DutyDtoBuilder().withDutyId(
            duty.getEntity().getDutyId()
        ).build();
    }
}
