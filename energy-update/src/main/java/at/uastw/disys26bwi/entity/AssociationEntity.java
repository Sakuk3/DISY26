package at.uastw.disys26bwi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "associations")
public class AssociationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code;

    @Column(name = "created_at", insertable = false, updatable = false)
    private java.time.OffsetDateTime createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public java.time.OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
