package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
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

    private DutyManager dutyManager = new DutyManager();
    private static final Logger LOG = LogManager.getLogger(DutyService.class);

    /**
     * Returns a duty, currently only id, with this id.
     *
     * @param id Id of Duty
     * @return DutyDto
     */
    public DutyDto getDuty(Integer id) {
        Optional<DutyDto> duty = new DutyManager().loadDutyDetailsDto(id);

        if (duty.isPresent()) {
            return duty.get();
        }
        return null;
    }

    /**
     * Get all Duties which is the current logged in User is assigned in a position.
     *
     * @param userId of the current logged in User
     * @return all Duties of the user
     */
    public Set<DutyDto> getAllDuties(Integer userId) {



        Set<DutyDto> duties = null;
        try {
            duties = this.dutyManager.findFutureUnscheduledDutiesForMusician(userId);

        } catch (Exception e) {
            LOG.error(e);
        }
        return duties;
        /*
        Set<DutyDto> duties = new HashSet<>();

        MusicianManager musicianManager = new MusicianManager();

        Optional<Musician> musician;

        try {
            musician = musicianManager.loadMusician(userId);
        } catch (Exception e) {
            return null;
        }

        if (musician.isPresent()) {
            for (IDutyPositionEntity dp : musician.get().getAssignedDutyPositions()) {
                Optional<Duty> duty = new DutyManager().loadDutyDetails(dp.getDuty().getDutyId());
                if (duty.isPresent()) {
                    duties.add(dutyToDto(duty.get()));
                }
            }
            return duties;
        }

        return null;
        */
    }
}
