package at.uastw.disys26bwi.entity;

import jakarta.persistence.*;

@Entity(name = "hourly_percentages")
@IdClass(HourlyPercentagesId.class)
public class HourlyPercentagesEntity {

  @Id
  @Column(name = "association_id")
  private long associationId;

  @Id
  @Column(name = "hour_bucket")
  private java.time.OffsetDateTime hourBucket;

  @Column(name = "self_consumption_pct")
  private java.math.BigDecimal selfConsumptionPct;

  @Column(name = "grid_dependency_pct")
  private java.math.BigDecimal gridDependencyPct;

  @Column(name = "created_at")
  private java.time.OffsetDateTime createdAt;

  @Column(name = "updated_at")
  private java.time.OffsetDateTime updatedAt;

  public long getAssociationId() {
    return associationId;
  }

  public void setAssociationId(long associationId) {
    this.associationId = associationId;
  }

  public java.time.OffsetDateTime getHourBucket() {
    return hourBucket;
  }

  public void setHourBucket(java.time.OffsetDateTime hourBucket) {
    this.hourBucket = hourBucket;
  }

  public java.math.BigDecimal getSelfConsumptionPct() {
    return selfConsumptionPct;
  }

  public void setSelfConsumptionPct(java.math.BigDecimal selfConsumptionPct) {
    this.selfConsumptionPct = selfConsumptionPct;
  }

  public java.math.BigDecimal getGridDependencyPct() {
    return gridDependencyPct;
  }

  public void setGridDependencyPct(java.math.BigDecimal gridDependencyPct) {
    this.gridDependencyPct = gridDependencyPct;
  }

  public java.time.OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(java.time.OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public java.time.OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(java.time.OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
