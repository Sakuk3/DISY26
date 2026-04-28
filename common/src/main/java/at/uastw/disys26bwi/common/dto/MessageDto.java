package at.uastw.disys26bwi.common.dto;

import java.time.LocalDateTime;

public class MessageDto {

    private String message;
    private LocalDateTime timestamp;

    public MessageDto() {
    }

    public MessageDto(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MessageDto{message='" + message + "', timestamp=" + timestamp + "}";
    }
}
