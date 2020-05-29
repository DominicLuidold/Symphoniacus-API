package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.rest.models.wish.DateWishDto;
import at.fhv.teamb.symphoniacus.rest.models.wish.WishDto;
import at.fhv.teamb.symphoniacus.rest.models.wish.WishTargetType;
import at.fhv.teamb.symphoniacus.rest.models.wish.WishType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DateWishService {
    private static final Logger LOG = LogManager.getLogger(DateWishService.class);

    //only for testing
    Set<WishDto<DateWishDto>> dateWishes = new HashSet<>();


    /**
     * Get all date wishes of a User.
     */
    public Set<WishDto<DateWishDto>> getAllDateWishes(Integer userId) {
        WishDto<DateWishDto> wish1 = new WishDto.WishBuilder<DateWishDto>()
                .withWishId(1)
                .withWishType(WishType.NEGATIVE)
                .withTarget(WishTargetType.DATE)
                .withStatus("APPROVED")
                .withReason("I want to break free")
                .withDetails(new DateWishDto(LocalDateTime.now(),
                        LocalDateTime.of(3000, Month.APRIL, 1, 12, 30)))
                .build();

        WishDto<DateWishDto> wish2 = new WishDto.WishBuilder<DateWishDto>()
                .withWishId(2)
                .withWishType(WishType.NEGATIVE)
                .withTarget(WishTargetType.DATE)
                .withStatus("REJECTED")
                .withReason("I dont want to break free")
                .withDetails(new DateWishDto(LocalDateTime.now(),
                        LocalDateTime.of(4000, Month.DECEMBER, 8, 0, 0)))
                .build();

        this.dateWishes.add(wish1);
        this.dateWishes.add(wish2);

        return dateWishes;
    }

    /**
     * Add a new date wish.
     */
    public Optional<WishDto<DateWishDto>> addNewDateWish(WishDto<DateWishDto> datewish) {
        WishDto<DateWishDto> newWish = new WishDto.WishBuilder<DateWishDto>()
                .withWishId(69)
                .withWishType(datewish.getWishType())
                .withTarget(datewish.getTarget())
                .withStatus(datewish.getStatus())
                .withReason(datewish.getReason())
                .withDetails(new DateWishDto(
                        datewish.getDetails().getStart(),
                        datewish.getDetails().getEnd())
                )
                .build();
        this.dateWishes.add(newWish);
        return Optional.of(newWish);
    }

    /**
     * Update data of a existing date wish.
     */
    public Optional<WishDto<DateWishDto>> detailsDateWish(Integer wishID) {

        return Optional.empty();
    }
}
