package at.uastw.disys26bwi.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

public class HourlyUsageId implements Serializable {

  private long associationId;
  private OffsetDateTime hourBucket;

  public long getAssociationId() {
    return associationId;
  }

  public void setAssociationId(long associationId) {
    this.associationId = associationId;
  }

  public OffsetDateTime getHourBucket() {
    return hourBucket;
  }

  public void setHourBucket(OffsetDateTime hourBucket) {
    this.hourBucket = hourBucket;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HourlyUsageId that)) return false;
    return associationId == that.associationId &&
      Objects.equals(hourBucket, that.hourBucket);
  }

  @Override
  public int hashCode() {
    return Objects.hash(associationId, hourBucket);
  }
}
