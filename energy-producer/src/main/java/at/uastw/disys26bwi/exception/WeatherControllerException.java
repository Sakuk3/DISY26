package at.uastw.disys26bwi.exception;

public class WeatherControllerException extends RuntimeException {
    public WeatherControllerException(String message) {
        super(message);
    }

    public WeatherControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}