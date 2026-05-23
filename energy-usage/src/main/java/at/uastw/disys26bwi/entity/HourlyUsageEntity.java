package at.uastw.disys26bwi.entity;

import jakarta.persistence.*;

@Entity(name = "hourly_usage")
@IdClass(HourlyUsageId.class)
public class HourlyUsageEntity {

  @Id
  @Column(name = "association_id")
  private long associationId;

  @Id
  @Column(name = "hour_bucket")
  private java.time.OffsetDateTime hourBucket;

  @Column(name = "community_produced_kwh")
  private java.math.BigDecimal communityProducedKwh;

  @Column(name = "community_used_kwh")
  private java.math.BigDecimal communityUsedKwh;

  @Column(name = "grid_used_kwh")
  private java.math.BigDecimal gridUsedKwh;

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

  public java.math.BigDecimal getCommunityProducedKwh() {
    return communityProducedKwh;
  }

  public void setCommunityProducedKwh(java.math.BigDecimal communityProducedKwh) {
    this.communityProducedKwh = communityProducedKwh;
  }

  public java.math.BigDecimal getCommunityUsedKwh() {
    return communityUsedKwh;
  }

  public void setCommunityUsedKwh(java.math.BigDecimal communityUsedKwh) {
    this.communityUsedKwh = communityUsedKwh;
  }

  public java.math.BigDecimal getGridUsedKwh() {
    return gridUsedKwh;
  }

  public void setGridUsedKwh(java.math.BigDecimal gridUsedKwh) {
    this.gridUsedKwh = gridUsedKwh;
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
