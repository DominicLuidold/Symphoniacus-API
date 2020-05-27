package at.fhv.teamb.symphoniacus.rest.models;

/**
 * Build a custom Response for the Symphoniacus Api.
 *
 * @author Tobias Moser
 **/
public class CustomResponse<T> {
    private final String status;
    private final int code;
    private final T payload;

    CustomResponse(String status, int code, T payload) {
        this.status = status;
        this.code = code;
        this.payload = payload;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public T getPayload() {
        return payload;
    }

    public String toJson() {
        return JsonHelper.toJson(this);
    }
}