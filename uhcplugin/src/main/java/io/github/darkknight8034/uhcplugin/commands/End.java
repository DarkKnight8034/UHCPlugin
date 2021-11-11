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
        World world;

        if (!this.plugin.configFile.getBoolean("game.end.returnToLobby"))
        {

            world = player.getWorld();

        }
        else
        {

            if (this.plugin.configFile.getString("game.end.lobby") == null)
            {

                world = this.plugin.getServer().getWorlds().get(0);

            }
            else
            {

                world = this.plugin.getServer().getWorld(this.plugin.configFile.getString("game.end.lobby"));
                if (world == null)
                {

                    player.sendMessage(ChatColor.RED + "No world was found with the name: \"" + this.plugin.configFile.getString("lobby") + "\"! Could not send players back to lobby.");

                }

            }

        }

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
