package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.entity.HourlyPercentagesEntity;
import at.uastw.disys26bwi.entity.HourlyPercentagesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface HourlyPercentagesRepository
  extends JpaRepository<HourlyPercentagesEntity, HourlyPercentagesId> {

  List<HourlyPercentagesEntity> findByAssociationId(long associationId);

  List<HourlyPercentagesEntity> findByHourBucketBetween(
    OffsetDateTime from,
    OffsetDateTime to
  );
}
