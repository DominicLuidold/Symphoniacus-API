package at.fhv.teamb.symphoniacus.rest.models;

/**
 * Build a custom Response for the Symphoniacus Api.
 *
 * @author Tobias Moser
 **/
public class CustomResponseBuilder<T> {
    private String status;
    private int code;
    private T payload;

    public CustomResponseBuilder(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public CustomResponseBuilder<T> withPayload(T payload) {
        this.payload = payload;
        return this;
    }

    /**
     * Constructs a new CustomResponse with the previously set options in the builder.
     *
     * @return Constructed CustomResponse.
     */
    public CustomResponse<T> build() {
        return new CustomResponse<T>(
                this.status,
                this.code,
                this.payload
        );
    }
}