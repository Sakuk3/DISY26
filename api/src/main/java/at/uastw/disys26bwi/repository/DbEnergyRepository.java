package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.apiSpec.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.apiSpec.dto.HistoricEnergyDto;
import at.uastw.disys26bwi.entity.HourlyEnergyView;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Component
public class DbEnergyRepository implements EnergyRepository {

    private final EnergyViewRepository repository;

    public DbEnergyRepository(EnergyViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public CurrentEnergyDto getCurrentEnergy() {
        return repository.findTopByOrderByHourDesc()
                .map(row -> new CurrentEnergyDto(
                        row.getHour().toLocalDateTime(),
                        toDouble(row.getSelfConsumptionPct()),
                        toDouble(row.getGridDependencyPct())
                ))
                .orElseGet(() -> new CurrentEnergyDto(
                        LocalDateTime.now(),
                        0.0,
                        0.0
                ));
    }

    @Override
    public HistoricEnergyDto getHistoricEnergy(LocalDateTime start, LocalDateTime end) {
        var startOffset = start.atOffset(ZoneOffset.UTC);
        var endOffset = end.atOffset(ZoneOffset.UTC);

        var rows = repository.findByHourBetweenOrderByHourAsc(startOffset, endOffset);

        BigDecimal communityProduced = rows.stream()
                .map(HourlyEnergyView::getCommunityProducedKwh)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal communityUsed = rows.stream()
                .map(HourlyEnergyView::getCommunityUsedKwh)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal gridUsed = rows.stream()
                .map(HourlyEnergyView::getGridUsedKwh)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new HistoricEnergyDto(
                start,
                end,
                toDouble(communityProduced),
                toDouble(communityUsed),
                toDouble(gridUsed)
        );
    }

    private static double toDouble(BigDecimal value) {
        if (value == null) {
            return 0.0;
        }

        return value.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}