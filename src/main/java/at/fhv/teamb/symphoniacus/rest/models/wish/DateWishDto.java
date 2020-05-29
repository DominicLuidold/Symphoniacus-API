package at.fhv.teamb.symphoniacus.rest.models.wish;

import java.time.LocalDateTime;

/**
 * Model class for {@link DateWishDto}.
 *
 * @author Tobias Moser
 */
public class DateWishDto {
    private LocalDateTime start;
    private LocalDateTime end;

    public DateWishDto(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
