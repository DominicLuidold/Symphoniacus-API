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

    private CustomResponse(String status, int code, T payload) {
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

    public static class CustomResponseBuilder<T> {
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
        public CustomResponse<?> build() {
            return new CustomResponse<>(
                    this.status,
                    this.code,
                    this.payload
            );
        }
    }

    public String toJson() {
        return JsonHelper.toJson(this);
    }
}
