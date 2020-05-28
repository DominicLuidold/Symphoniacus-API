package at.fhv.teamb.symphoniacus.rest.models.wish;

public class Wish<T> {
    private Wishtype wishtype;
    private WishTargetType target;
    private String status;
    private String reason;
    private T details;


    public Wish(Wishtype wishtype, WishTargetType target, String status, String reason, T details) {
        this.wishtype = wishtype;
        this.target = target;
        this.status = status;
        this.reason = reason;
        this.details = details;
    }




    public Wishtype getWishtype() {
        return wishtype;
    }

    public void setWishtype(Wishtype wishtype) {
        this.wishtype = wishtype;
    }

    public WishTargetType getTarget() {
        return target;
    }

    public void setTarget(WishTargetType target) {
        this.target = target;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getDetails() {
        return details;
    }

    public void setDetails(T details) {
        this.details = details;
    }
}
