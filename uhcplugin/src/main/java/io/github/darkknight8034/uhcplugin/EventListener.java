package io.github.darkknight8034.uhcplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

// Death handler
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.GameMode;
import org.bukkit.World;

import io.github.darkknight8034.uhcplugin.Main;

public class EventListener implements Listener
{

    private Main plugin;

    public EventListener(Main plugin)
    {

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;

    }


    // Handles player kills
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {

        boolean thing = this.plugin.configFile.getBoolean("showKillCount");
        this.plugin.getLogger().info("Config showKillCount: " + thing);

        // Gets players involved in death
        Player player = event.getEntity();
        LivingEntity entity = (LivingEntity) player;
        Player killer = entity.getKiller();
        World world = player.getWorld();

        // Makes killed player a spectator
        HumanEntity killed = (HumanEntity) player;
        killed.setGameMode(GameMode.SPECTATOR);

        // Lightning strick effect
        world.strikeLightningEffect(player.getLocation());

        this.plugin.broadcast.send(killer.getDisplayName());

    }

}
