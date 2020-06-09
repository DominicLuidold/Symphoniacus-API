package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.application.WishRequestManager;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DutyWishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishDto;
import at.fhv.teamb.symphoniacus.application.type.WishStatusType;
import at.fhv.teamb.symphoniacus.application.type.WishTargetType;
import at.fhv.teamb.symphoniacus.application.type.WishType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.Set;

@Singleton
public class DutyWishService {
    private static final Logger LOG = LogManager.getLogger(DutyWishService.class);
    private WishRequestManager wishRequestManager = new WishRequestManager();

    /**
     * Get all wishes of a given duty ID.
     *
     * @param dutyId of the Duty.
     */
    public Set<WishDto<DutyWishDto>> getAllDutyWishesOfUserAndDuty(
        Integer dutyId,
        Integer userId
    ) {
        return this.wishRequestManager.getAllDutyWishesForUserAndDuty(userId, dutyId);
    }

    /**
     * Get all future duty wishes of user.
     * @param userId of user.
     */
    public Set<WishDto<DutyWishDto>> getAllFutureDutyWishesOfUser(
        Integer userId
    ) {
        return this.wishRequestManager.getAllFutureDutyWishesOfUser(userId);
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
            .withStatus(WishStatusType.REVIEW)
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
    public Optional<WishDto<DutyWishDto>> updateDutyWish(
        WishDto<DutyWishDto> dutyWish,
        Integer userId
    ) {
        return this.wishRequestManager.updateDutyWish(dutyWish, userId);
    }

    /**
     * Create one wish of a specific duty.
     *
     * @param newWish new wish to persist.
     */
    public Optional<WishDto<DutyWishDto>> addNewDutyWish(
        WishDto<DutyWishDto> newWish,
        Integer userId
    ) {
        return wishRequestManager.addNewDutyWish(newWish, userId);
    }

    /**
     * Delete one specific wish of a specific duty.
     *
     * @param wishId of the requested duty.
     */
    public boolean deleteDutyWish(Integer wishId) {
        return this.wishRequestManager.removeDutyWish(wishId);
    }
}