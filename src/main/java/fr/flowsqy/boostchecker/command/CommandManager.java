package fr.flowsqy.boostchecker.command;

import fr.flowsqy.boostchecker.BoostCheckerPlugin;
import fr.flowsqy.boostchecker.task.CheckTask;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Objects;

public class CommandManager {

    private final CheckTask task;

    public CommandManager(BoostCheckerPlugin plugin, CheckTask task, YamlConfiguration configuration) {
        this.task = task;
        final PluginCommand command = plugin.getCommand("bonus");
        Objects.requireNonNull(command);
        final String noPerm = configuration.getString("noperm");
        if(noPerm != null){
            command.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', noPerm));
        }
    }

}
