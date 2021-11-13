package fr.flowsqy.boostchecker.command;

import fr.flowsqy.boostchecker.BoostCheckerPlugin;
import fr.flowsqy.boostchecker.task.CheckTask;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Objects;

public class CommandManager {

    public CommandManager(BoostCheckerPlugin plugin, CheckTask task, YamlConfiguration configuration) {
        final PluginCommand command = plugin.getCommand("boost");
        Objects.requireNonNull(command);
        final String noPerm = configuration.getString("noperm");
        if (noPerm != null) {
            command.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', noPerm));
        }
        command.setTabCompleter((sender, cmd, label, args) -> Collections.emptyList());
        command.setExecutor((sender, cmd, label, args) -> {
            if (sender instanceof Player player) {
                task.perform(player);
            }
            return true;
        });
    }

}
