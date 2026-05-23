package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.entity.HourlyUsageEntity;
import at.uastw.disys26bwi.entity.HourlyUsageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface HourlyUsageRepository
  extends JpaRepository<HourlyUsageEntity, HourlyUsageId> {

  List<HourlyUsageEntity> findByAssociationId(long associationId);

  List<HourlyUsageEntity> findByHourBucketBetween(
    OffsetDateTime from,
    OffsetDateTime to
  );
}
