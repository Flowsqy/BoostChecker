package fr.flowsqy.boostchecker;

import fr.flowsqy.boostchecker.command.CommandManager;
import fr.flowsqy.boostchecker.io.BoostMessagesManager;
import fr.flowsqy.boostchecker.task.CheckTask;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BoostCheckerPlugin extends JavaPlugin {

    private LuckPerms luckPerms;
    private BoostMessagesManager boostMessagesManager;

    @Override
    public void onEnable() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null) {
            getLogger().warning("LuckPerms API is not loaded. Disabling the plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        luckPerms = provider.getProvider();
        boostMessagesManager = new BoostMessagesManager();

        final CheckTask task = new CheckTask(this);
        new CommandManager(this, task);
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public BoostMessagesManager getBoostMessagesManager() {
        return boostMessagesManager;
    }
}
