package at.uastw.disys26bwi.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

public class HourlyEnergyViewId implements Serializable {

  private String association;
  private OffsetDateTime hour;

  public String getAssociation() {
    return association;
  }

  public void setAssociation(String association) {
    this.association = association;
  }

  public OffsetDateTime getHour() {
    return hour;
  }

  public void setHour(OffsetDateTime hour) {
    this.hour = hour;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HourlyEnergyViewId that)) return false;
    return Objects.equals(association, that.association) &&
      Objects.equals(hour, that.hour);
  }

  @Override
  public int hashCode() {
    return Objects.hash(association, hour);
  }
}
