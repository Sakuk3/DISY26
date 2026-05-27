package at.uastw.disys26bwi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenMeteoResponse(
        @JsonProperty("minutely_15") Minutely15 minutely15
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Minutely15(
            List<String> time,
            @JsonProperty("direct_radiation") List<Double> directRadiation
    ) {}
}