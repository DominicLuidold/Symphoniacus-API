package at.fhv.teamb.symphoniacus.rest.models;

/**
 * Build a custom Response for the Symphoniacus Api.
 * Status -> 200 Success / 400 Client Failure  / 500 Server Error
 * @author Tobias Moser
 **/

public class CustomResponse<T> {
    private final String status;
    private final int code;
    private final String message;
    private final T payload;

    CustomResponse(String status, int code, String message, T payload) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.payload = payload;

    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getPayload() {
        return payload;
    }

    public String toJson() {
        return JsonHelper.toJson(this).replaceAll("null", "{}");
    }
}