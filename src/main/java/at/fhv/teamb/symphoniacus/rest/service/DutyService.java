package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Service implementation for {@link DutyDto}.
 *
 * @author Valentin Goronjic
 * @author Tobias Moser
 */
@Singleton
public class DutyService {

    private final DutyManager dutyManager = new DutyManager();
    private static final Logger LOG = LogManager.getLogger(DutyService.class);

    /**
     * Returns a duty, currently only id, with this id.
     *
     * @param id Id of Duty
     * @return DutyDto
     */
    public DutyDto getDuty(Integer id) {
        Optional<DutyDto> duty = new DutyManager().loadDutyDetailsDto(id);

        return duty.orElse(null);
    }

    /**
     * Get all Duties which is the current logged in User is assigned in a position.
     *
     * @param userId of the current logged in User
     * @return all Duties of the user
     */
    public Set<DutyDto> getAllDuties(Integer userId) {
        Set<DutyDto> duties;
        try {
            duties = this.dutyManager.findFutureUnscheduledDutiesForMusician(userId);

        } catch (Exception e) {
            LOG.error(e);
            return new HashSet<>();
        }
        return duties;
    }
}
