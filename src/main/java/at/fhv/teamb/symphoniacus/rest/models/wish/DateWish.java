package at.fhv.teamb.symphoniacus.rest.models.wish;

import java.time.LocalDateTime;

/**
 * Model class for {@link DateWish}.
 *
 * @author Tobias Moser
 */
public class DateWish {
    private LocalDateTime start;
    private LocalDateTime end;

    public DateWish(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
