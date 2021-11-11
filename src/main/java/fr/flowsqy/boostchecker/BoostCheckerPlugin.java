package fr.flowsqy.boostchecker;

import fr.flowsqy.boostchecker.command.CommandManager;
import fr.flowsqy.boostchecker.io.BoostMessagesManager;
import fr.flowsqy.boostchecker.task.CheckTask;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.logging.Logger;

public class BoostCheckerPlugin extends JavaPlugin {

    private LuckPerms luckPerms;
    private BoostMessagesManager boostMessagesManager;

    @Override
    public void onEnable() {
        final Logger logger = getLogger();
        final File dataFolder = getDataFolder();

        if (!checkDataFolder(dataFolder)) {
            logger.warning("Can not write in the directory : " + dataFolder.getAbsolutePath());
            logger.warning("Disable the plugin");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        final RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null) {
            logger.warning("LuckPerms API is not loaded. Disabling the plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        luckPerms = provider.getProvider();

        final YamlConfiguration configuration = initFile(dataFolder, "config.yml");

        boostMessagesManager = new BoostMessagesManager(configuration);

        final CheckTask task = new CheckTask(this, configuration);
        new CommandManager(this, task, configuration);
    }

    private boolean checkDataFolder(File dataFolder) {
        if (dataFolder.exists())
            return dataFolder.canWrite();
        return dataFolder.mkdirs();
    }

    private YamlConfiguration initFile(File dataFolder, String fileName) {
        final File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            try {
                Files.copy(Objects.requireNonNull(getResource(fileName)), file.toPath());
            } catch (IOException ignored) {
            }
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public BoostMessagesManager getBoostMessagesManager() {
        return boostMessagesManager;
    }
}
