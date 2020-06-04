package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.application.WishRequestManager;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DateWishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.Set;

@Singleton
public class DateWishService {
    private static final Logger LOG = LogManager.getLogger(DateWishService.class);
    private WishRequestManager wishRequestManager = new WishRequestManager();

    /**
     * Get all date wishes of a User.
     */
    public Set<WishDto<DateWishDto>> getAllDateWishes(Integer userId) {
        return this.wishRequestManager.getAllNegativeDateWishesForUser(userId);
    }

    /**
     * Add a new date wish.
     */
    public Optional<WishDto<DateWishDto>> addNewDateWish(
            WishDto<DateWishDto> datewish,
            Integer userId
    ) {
        return this.wishRequestManager.addNewNegativeDateWish(datewish, userId);
    }

    /**
     * Get the details of a date wish.
     */
    public Optional<WishDto<DateWishDto>> getDateWishDetails(Integer wishID) {
        return this.wishRequestManager.getNegativeDateWish(wishID);
    }

    /**
     * Update data of a existing date wish.
     */
    public Optional<WishDto<DateWishDto>> updateDateWishDetails(
            WishDto<DateWishDto> datewish,
            Integer userId
    ) {
        return this.wishRequestManager.updateNegativeDateWish(datewish, userId);
    }

    /**
     * Delete a date wish of the given ID.
     */
    public Boolean deleteWish(Integer wishId) {
        return this.wishRequestManager.removeNegativeDateWish(wishId);
    }
}