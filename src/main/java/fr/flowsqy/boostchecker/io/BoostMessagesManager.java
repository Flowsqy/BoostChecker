package fr.flowsqy.boostchecker.io;

import fr.flowsqy.plcommon.time.DurationFormatter;
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

    private void fillBoostMessages(YamlConfiguration configuration) {
        final ConfigurationSection boostsSection = configuration.getConfigurationSection("boost");
        if (boostsSection == null)
            return;

        final DurationFormatter defaultFormatter = DurationFormatter.deserializeDurationFormatter(
                configuration.getConfigurationSection("time"),
                DurationFormatter.DEFAULT_FORMATTER
        );

        for (String key : boostsSection.getKeys(false)) {
            final ConfigurationSection boostSection = boostsSection.getConfigurationSection(key);
            if (boostSection == null)
                continue;
            final String group = boostSection.getString("group");
            if (group == null)
                continue;
            final String infinite = boostSection.getString("infinite");
            if (infinite == null)
                continue;
            final String normal = boostSection.getString("normal");
            if (normal == null)
                continue;
            final DurationFormatter durationFormatter = DurationFormatter.deserializeDurationFormatter(
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

}
