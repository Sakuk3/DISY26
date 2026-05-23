package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.entity.AssociationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationRepository extends JpaRepository<AssociationEntity, Long> {
  AssociationEntity findByCode(String code);
}
