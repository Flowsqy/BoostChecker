package fr.flowsqy.boostchecker.command;

import fr.flowsqy.boostchecker.BoostCheckerPlugin;
import fr.flowsqy.boostchecker.task.CheckTask;

public class CommandManager {

    private final CheckTask task;

    public CommandManager(BoostCheckerPlugin plugin, CheckTask task) {
        this.task = task;
    }
}
