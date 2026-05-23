package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.entity.EnergyEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface EnergyEventRepository extends JpaRepository<EnergyEventEntity, Long> {

  List<EnergyEventEntity> findByAssociationId(long associationId);

  List<EnergyEventEntity> findByOccurredAtBetween(
    OffsetDateTime from,
    OffsetDateTime to
  );
}
