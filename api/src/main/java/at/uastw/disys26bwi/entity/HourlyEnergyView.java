package at.uastw.disys26bwi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;


//@Entity marks it as a JPA entity.
@Entity
//@Table(name = "v_hourly_energy") explicitly tells Hibernate which database view/table this class maps to.
@Table(name = "v_hourly_energy")
@IdClass(HourlyEnergyViewId.class)
public class HourlyEnergyView {

  @Id
  private String association;

  @Id
  private java.time.OffsetDateTime hour;

  @Column(name = "community_produced_kwh")
  private java.math.BigDecimal communityProducedKwh;

  @Column(name = "community_used_kwh")
  private java.math.BigDecimal communityUsedKwh;

  @Column(name = "grid_used_kwh")
  private java.math.BigDecimal gridUsedKwh;

  @Column(name = "self_consumption_pct")
  private java.math.BigDecimal selfConsumptionPct;

  @Column(name = "grid_dependency_pct")
  private java.math.BigDecimal gridDependencyPct;

  public String getAssociation() {
    return association;
  }

  public void setAssociation(String association) {
    this.association = association;
  }

  public java.time.OffsetDateTime getHour() {
    return hour;
  }

  public void setHour(java.time.OffsetDateTime hour) {
    this.hour = hour;
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
}
