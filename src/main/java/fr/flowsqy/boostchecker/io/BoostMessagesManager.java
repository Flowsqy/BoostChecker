package fr.flowsqy.boostchecker.io;

import fr.flowsqy.boostchecker.time.DurationFormatter;
import fr.flowsqy.boostchecker.time.TimeFormatter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;

public class BoostMessagesManager {

    private final Map<String, BoostMessages> boostMessages;

    public BoostMessagesManager(YamlConfiguration configuration) {
        boostMessages = new HashMap<>();
        fillBoostMessages(configuration);
    }

    private void fillBoostMessages(YamlConfiguration configuration){
        final ConfigurationSection boostsSection = configuration.getConfigurationSection("boost");
        if(boostsSection == null)
            return;

        final DurationFormatter defaultFormatter = deserializeDurationFormatter(
                configuration.getConfigurationSection("time"),
                new DurationFormatter(
                        "%day%%hour%%minute%%second%",
                        " ",
                        new TimeFormatter(
                                "",
                                "%time% jour ",
                                "%time% jours "
                        ),
                        new TimeFormatter(
                                "",
                                "%time% heure ",
                                "%time% heures "
                        ),
                        new TimeFormatter(
                                "",
                                "%time% minute ",
                                "%time% minutes "
                        ),
                        new TimeFormatter(
                                "",
                                "%time% seconde ",
                                "%time% secondes "
                        )
                )
        );

        for(String key : boostsSection.getKeys(false)){
            final ConfigurationSection boostSection = boostsSection.getConfigurationSection(key);
            if(boostSection == null)
                continue;
            final String group = boostSection.getString("boost");
            if(group == null)
                continue;
            final String infinite = boostSection.getString("infinite");
            if(infinite == null)
                continue;
            final String normal = boostSection.getString("normal");
            if(normal == null)
                continue;
            final DurationFormatter durationFormatter = deserializeDurationFormatter(
                    boostSection.getConfigurationSection("time"),
                    defaultFormatter
            );

            boostMessages.put(group, new BoostMessages(
                    ChatColor.translateAlternateColorCodes('&', infinite),
                    ChatColor.translateAlternateColorCodes('&', normal),
                    durationFormatter
            ));
        }
    }

    public Map<String, BoostMessages> getBoostMessages() {
        return boostMessages;
    }
    
    private DurationFormatter deserializeDurationFormatter(ConfigurationSection section, DurationFormatter defaultFormatter){
        if(section == null)
            return defaultFormatter;
        final String format = section.getString("format");
        if(format == null)
            return defaultFormatter;
        final String empty = section.getString("empty");
        if(empty == null)
            return defaultFormatter;
        final TimeFormatter dayFormatter = deserializeTimeFormatter(section.getConfigurationSection("day"));
        if(dayFormatter == null)
            return defaultFormatter;
        final TimeFormatter hourFormatter = deserializeTimeFormatter(section.getConfigurationSection("hour"));
        if(hourFormatter == null)
            return defaultFormatter;
        final TimeFormatter minuteFormatter = deserializeTimeFormatter(section.getConfigurationSection("minute"));
        if(minuteFormatter == null)
            return defaultFormatter;
        final TimeFormatter secondFormatter = deserializeTimeFormatter(section.getConfigurationSection("second"));
        if(secondFormatter == null)
            return defaultFormatter;
        return new DurationFormatter(
                ChatColor.translateAlternateColorCodes('&', format),
                ChatColor.translateAlternateColorCodes('&', empty),
                dayFormatter,
                hourFormatter,
                minuteFormatter,
                secondFormatter
        );
    }
    
    private TimeFormatter deserializeTimeFormatter(ConfigurationSection section){
        if(section == null)
            return null;
        final String none = section.getString("none");
        if(none == null)
            return null;
        final String one = section.getString("one");
        if(one == null)
            return null;
        final String multiple = section.getString("multiple");
        if(multiple == null)
            return null;
        return new TimeFormatter(
                ChatColor.translateAlternateColorCodes('&', none),
                ChatColor.translateAlternateColorCodes('&', one),
                ChatColor.translateAlternateColorCodes('&', multiple)
        );
    }
    
}
