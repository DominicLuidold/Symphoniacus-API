package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.dto.DutyCategoryDto;
import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceDto;
import at.fhv.teamb.symphoniacus.application.dto.SeriesOfPerformancesDto;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Service implementation for {@link DutyDto}.
 *
 * @author Valentin Goronjic
 */
public class DutyService {

    private DutyManager dutyManager = new DutyManager();

    /**
     * Returns a duty, currently only id, with this id.
     *
     * @param id Id of Duty
     * @return DutyDto
     */
    public DutyDto getDuty(Integer id) {
        Optional<Duty> duty = new DutyManager().loadDutyDetails(id);

        if (duty.isPresent()) {
            return dutyToDto(duty.get());
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
        System.out.println("asdf");
        return this.dutyManager.findFutureUnscheduledDutiesForMusician(userId);
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

    private DutyDto dutyToDto(Duty duty) {
        Set<MusicalPieceDto> musicalPieces = new HashSet<>();

        for (IMusicalPieceEntity mp : duty
                .getEntity().getSeriesOfPerformances().getMusicalPieces()) {
            MusicalPieceDto musicalPieceDto =
                    new MusicalPieceDto.MusicalPieceDtoBuilder(mp.getMusicalPieceId())
                    .withName(mp.getName())
                    .withCategory(mp.getCategory())
                    .build();
            musicalPieces.add(musicalPieceDto);
        }


        SeriesOfPerformancesDto seriesOfPerformancesDto =
                new SeriesOfPerformancesDto.SeriesOfPerformancesDtoBuilder(
                        duty.getEntity().getSeriesOfPerformances().getSeriesOfPerformancesId()
                )
                .withMusicalPieces(musicalPieces)
                .build();

        DutyCategoryDto dutyCategory =
                new DutyCategoryDto.DutyCategoryDtoBuilder(
                        duty.getEntity().getDutyCategory().getDutyCategoryId()
                )
                .withType(duty.getEntity().getDutyCategory().getType())
                .build();

        DutyDto dutyDto = new DutyDto.DutyDtoBuilder()
                .withDutyId(duty.getEntity().getDutyId())
                .withDescription(duty.getEntity().getDescription())
                .withTimeOfDay(duty.getEntity().getTimeOfDay())
                .withDutyCategory(dutyCategory)
                .withStart(duty.getEntity().getStart())
                .withEnd(duty.getEntity().getEnd())
                .withSeriesOfPerformances(seriesOfPerformancesDto)
                .build();

        return dutyDto;
    }
}
