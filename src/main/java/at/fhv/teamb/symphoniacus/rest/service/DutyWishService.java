package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DutyWishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishTargetType;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Singleton
public class DutyWishService {
    private static final Logger LOG = LogManager.getLogger(DutyWishService.class);

    Set<WishDto<DutyWishDto>> dutyWishes = new HashSet<>();

    /**
     * Get all wishes of a given duty ID.
     *
     * @param dutyId of the Duty.
     */
    public Optional<Set<WishDto<DutyWishDto>>> getAllDutyWishes(Integer dutyId) {
        DutyWishDto dutywish = new DutyWishDto(1, false);
        dutywish.addMusicalPiece(1, "Cavalleria rusticana");



        WishDto<DutyWishDto> wish = new WishDto.WishBuilder<DutyWishDto>()
                .withWishId(1)
                .withWishType(WishType.NEGATIVE)
                .withTarget(WishTargetType.DUTY)
                .withStatus("APPROVED")
                .withReason("I want to break free, i want to break free !!")
                .withDetails(dutywish)
                .build();
        dutyWishes.add(wish);

        return Optional.of(dutyWishes);

    }

    /**
     * Get one specific wish of a specific duty.
     *
     * @param dutyId of the given duty.
     */
    public Optional<WishDto<DutyWishDto>> getOneDutyWish(Integer dutyId) {

        DutyWishDto dutywish = new DutyWishDto(1, false);
        dutywish.addMusicalPiece(1, "Cavalleria rusticana");


        WishDto<DutyWishDto> wish = new WishDto.WishBuilder<DutyWishDto>()
                .withWishId(1)
                .withWishType(WishType.NEGATIVE)
                .withTarget(WishTargetType.DUTY)
                .withStatus("APPROVED")
                .withReason("I want to break free, i want to break free !!")
                .withDetails(dutywish)
                .build();

        return Optional.of(wish);
    }

    /**
     * Update one specific wish of a specific duty.
     *
     * @param dutyWish updated duty wish
     */
    public Optional<WishDto<DutyWishDto>> updateDutyWish(WishDto<DutyWishDto> dutyWish) {
        return Optional.of(dutyWish);
    }

    /**
     * Create one wish of a specific duty.
     *
     * @param newWish new wish to persist.
     */
    public Optional<WishDto<DutyWishDto>> createDutyWish(WishDto<DutyWishDto> newWish) {
        dutyWishes.add(newWish);
        return Optional.of(newWish);
    }

    /**
     * Delete one specific wish of a specific duty.
     *
     * @param wishId of the requested duty.
     */
    public Boolean deleteDutyWish(Integer wishId) {
        return false;
    }

}
