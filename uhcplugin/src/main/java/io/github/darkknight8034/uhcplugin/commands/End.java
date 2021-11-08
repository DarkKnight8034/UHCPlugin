package io.github.darkknight8034.uhcplugin.commands;

// Command imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

// World imports
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
// Potion imports
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;

import io.github.darkknight8034.uhcplugin.Main;

public class End implements CommandExecutor
{

    private Main plugin;

    public End(Main plugin)
    {

        this.plugin = plugin;
        plugin.getCommand("end").setExecutor(this);

    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        Player player = (Player) sender;
        World world = player.getWorld();

        // Turns off hardcore
        world.setHardcore(false);

        // Brings players back to spawn
        this.plugin.relocate.relocate(world, 1);

        this.plugin.broadcast.send(ChatColor.GREEN + "The game has ended!");

        // Removes any effects on the players and sets to adventure mode
        for (LivingEntity p : this.plugin.getServer().getOnlinePlayers())
        {

            for (PotionEffect effect : p.getActivePotionEffects())
            {

                p.removePotionEffect(effect.getType());

            }

            HumanEntity human = (HumanEntity) p;
            human.setGameMode(GameMode.ADVENTURE);

        }

        return false;

    }

}
