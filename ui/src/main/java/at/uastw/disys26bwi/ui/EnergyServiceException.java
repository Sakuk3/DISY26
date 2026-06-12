package at.uastw.disys26bwi.ui;

class EnergyServiceException extends RuntimeException {
  public EnergyServiceException(String message) {
    super(message);
  }

  public EnergyServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
