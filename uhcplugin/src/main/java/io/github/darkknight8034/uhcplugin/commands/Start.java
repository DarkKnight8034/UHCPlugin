package io.github.darkknight8034.uhcplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.WorldBorder;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import io.github.darkknight8034.uhcplugin.Main;

// Start game command
public class Start implements CommandExecutor
{
    
    private Main plugin;

    public Start(Main plugin)
    {
        
        this.plugin = plugin;
        plugin.getCommand("start").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        Player player = (Player) sender;
        World world = player.getWorld();

        // Determines spread range
        int range = 250;
        try 
        {

            range = Integer.parseInt(args[0]);

        }
        catch (Exception e)
        {

            player.sendMessage("Make sure to enter a spread distance!\nCommand is formatted: /<command> <spread_range>");

        }

        // Relocates players and creates world boarder
        worldBorder(world, range + 50);
        plugin.relocate.relocate(world, range);

        // Sets to hardcore
        world.setHardcore(true);

        // Msgs that game has started
        this.plugin.broadcast.send(ChatColor.RED + "The game has started!");

        return false;
    
    }

    // Sets world border
    public void worldBorder(World world, int distance)
    {

        WorldBorder border = world.getWorldBorder();
        // Makes sure border is centered in world
        border.setCenter(0, 0);

        // Sets border
        border.setSize(distance * 2);

    }

}
