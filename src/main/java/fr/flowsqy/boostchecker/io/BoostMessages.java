package fr.flowsqy.boostchecker.io;

import fr.flowsqy.boostchecker.time.DurationFormatter;

import java.time.Instant;

public class BoostMessages {

    private final String infinite;
    private final String normal;
    private final DurationFormatter formatter;

    public BoostMessages(String infinite, String normal, DurationFormatter formatter) {
        this.infinite = infinite;
        this.normal = normal;
        this.formatter = formatter;
    }

    public String getInfinite() {
        return infinite;
    }

    public String getNormal(Instant end) {
        final String duration = formatter.format(end.toEpochMilli() - Instant.now().toEpochMilli());
        return normal.replace("%duration%", duration);
    }
}
