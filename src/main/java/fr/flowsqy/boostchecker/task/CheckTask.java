package fr.flowsqy.boostchecker.task;

import fr.flowsqy.boostchecker.BoostCheckerPlugin;
import fr.flowsqy.boostchecker.io.BoostMessages;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

public class CheckTask {

    private final BoostCheckerPlugin plugin;

    public CheckTask(BoostCheckerPlugin plugin) {
        this.plugin = plugin;
    }

    public void perform(Player player){
        final User user = plugin.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        final Set<AbstractMap.SimpleEntry<Instant, BoostMessages>> boosts = new HashSet<>();
        for(InheritanceNode node : user.getNodes(NodeType.INHERITANCE)){
            final BoostMessages message = plugin.getBoostMessagesManager().getBoostMessages().get(node.getGroupName());
            if(message != null && !node.hasExpired()){
                boosts.add(new AbstractMap.SimpleEntry<>(node.getExpiry(), message));
            }
        }
        if(boosts.isEmpty()){
            player.sendMessage("No Boost");
        }
        else{
            player.sendMessage("-----HEADER");
            for(AbstractMap.SimpleEntry<Instant, BoostMessages> boost : boosts){
                final Instant instant = boost.getKey();
                if(instant == null){
                    player.sendMessage(boost.getValue().getInfinite());
                }
                else{
                    player.sendMessage(boost.getValue().getNormal(instant));
                }
            }
            player.sendMessage("-----FOOTER");
        }
    }

}
