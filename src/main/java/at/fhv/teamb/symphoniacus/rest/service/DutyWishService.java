package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.rest.models.wish.DutyWishDto;
import at.fhv.teamb.symphoniacus.rest.models.wish.WishDto;
import at.fhv.teamb.symphoniacus.rest.models.wish.WishTargetType;
import at.fhv.teamb.symphoniacus.rest.models.wish.WishType;
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

    public Optional<Set<WishDto<DutyWishDto>>> getAllWishesOfDuty(Integer dutyId) {
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
}
