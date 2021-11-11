package fr.flowsqy.boostchecker.time;

public class DurationFormatter {
    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;

    private final String timeFormat;
    private final TimeFormatter dayFormat;
    private final TimeFormatter hourFormat;
    private final TimeFormatter minuteFormat;
    private final TimeFormatter secondFormat;

    public DurationFormatter(String timeFormat, TimeFormatter dayFormat, TimeFormatter hourFormat, TimeFormatter minuteFormat, TimeFormatter secondFormat) {
        this.timeFormat = timeFormat;
        this.dayFormat = dayFormat;
        this.hourFormat = hourFormat;
        this.minuteFormat = minuteFormat;
        this.secondFormat = secondFormat;
    }

    public String format(long duration){
        String output = timeFormat;

        final long woDays = duration % DAY;
        final long days = (duration - woDays) / DAY;
        output = output.replace("%day%", dayFormat.format(days));

        final long woHours = woDays % HOUR;
        final long hours = (woDays - woHours) / HOUR;
        output = output.replace("%hour%", hourFormat.format(hours));

        final long woMinutes = woHours % MINUTE;
        final long minutes = (woHours - woMinutes) / MINUTE;
        output = output.replace("%minute%", minuteFormat.format(minutes));

        final long woSeconds = woMinutes % SECOND;
        long seconds = (woMinutes - woSeconds) / SECOND;
        if(seconds == 0 && woSeconds > 0){
            seconds = 1;
        }
        output = output.replace("%second%", secondFormat.format(seconds));

        return output;
    }

}
