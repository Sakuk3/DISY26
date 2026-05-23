package at.uastw.disys26bwi.entity;

import jakarta.persistence.*;

@Entity(name = "energy_events")
public class EnergyEventEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "association_id")
  private long associationId;

  @Column(name = "event_type")
  private String eventType;

  private java.math.BigDecimal kwh;

  @Column(name = "occurred_at")
  private java.time.OffsetDateTime occurredAt;

  @Column(name = "created_at")
  private java.time.OffsetDateTime createdAt;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getAssociationId() {
    return associationId;
  }

  public void setAssociationId(long associationId) {
    this.associationId = associationId;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public java.math.BigDecimal getKwh() {
    return kwh;
  }

  public void setKwh(java.math.BigDecimal kwh) {
    this.kwh = kwh;
  }

  public java.time.OffsetDateTime getOccurredAt() {
    return occurredAt;
  }

  public void setOccurredAt(java.time.OffsetDateTime occurredAt) {
    this.occurredAt = occurredAt;
  }

  public java.time.OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(java.time.OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
