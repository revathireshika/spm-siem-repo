package uk.co.gresearch.siembol.alerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.reinert.jjschema.Attributes;

@Attributes(title = "time unit type", description = "Time unit type for sliding window alert counting")
public enum TimeUnitTypeDto {
    @JsonProperty("minutes") MINUTES("minutes", 60 * 1000L),
    @JsonProperty("hours") HOURS("hours", 60 * 60 * 1000L),
    @JsonProperty("seconds") SECONDS("seconds", 1000L);

    private final String name;
    private final long milliSeconds;
    TimeUnitTypeDto(String name, long milliSeconds) {
        this.name = name;
        this.milliSeconds = milliSeconds;
    }

    public long convertToMs(long timeUnits) {
        return timeUnits * milliSeconds;
    }

    @Override
    public String toString() {
        return name;
    }
}
