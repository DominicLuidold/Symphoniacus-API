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

    public DutyDto getDuty(Integer id) {
        // THIS SHOULD RETURN A DTO INSTEAD
        Duty duty = new DutyManager().loadDutyDetails(id).get();

        return new DutyDto.DutyDtoBuilder().withDutyId(
                duty.getEntity().getDutyId()
        ).build();
    }
}
