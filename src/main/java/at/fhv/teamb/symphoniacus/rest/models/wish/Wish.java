package at.fhv.teamb.symphoniacus.rest.models.wish;

/**
 * Model class for {@link Wish}.
 *
 * @author Tobias Moser
 */
public class Wish<T> {
    private final WishType wishType;
    private final WishTargetType target;
    private final String status;
    private final String reason;
    private final T details;


    private Wish(WishType wishtype,
                 WishTargetType target,
                 String status,
                 String reason,
                 T details
    ) {
        this.wishType = wishtype;
        this.target = target;
        this.status = status;
        this.reason = reason;
        this.details = details;
    }

    public WishType getWishType() {
        return wishType;
    }

    public WishTargetType getTarget() {
        return target;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public T getDetails() {
        return details;
    }

    public static class WishBuilder<T> {
        private WishType wishtype;
        private WishTargetType target;
        private String status;
        private String reason;
        private T details;

        public WishBuilder withWishType(WishType wishType) {
            this.wishtype = wishType;
            return this;
        }

        public WishBuilder withTarget(WishTargetType target) {
            this.target = target;
            return this;
        }

        public WishBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public WishBuilder withReason(String reason) {
            this.reason = reason;
            return this;
        }

        public WishBuilder withDetails(T details) {
            this.details = details;
            return this;
        }
    }
}