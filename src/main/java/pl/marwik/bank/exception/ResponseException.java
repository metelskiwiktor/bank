package pl.marwik.bank.exception;

public class ResponseException {
    private String errorMessage;
    private String errorCode;
    private String timeline;

    public ResponseException(String errorMessage, String errorCode, String timeline) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.timeline = timeline;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }
}
