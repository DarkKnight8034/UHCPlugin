package io.github.darkknight8034.uhcplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.Random;

import io.github.darkknight8034.uhcplugin.Main;

public class Relocate implements CommandExecutor
{
    
    private Main plugin;
    private Random random = new Random();

    public Relocate(Main plugin)
    {

        this.plugin = plugin;
        plugin.getCommand("relocate").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        Player player = (Player) sender;

        World world = player.getWorld();
        int range = 0;
        try 
        {

            range = Integer.parseInt(args[0]);

        }
        catch (Exception e)
        {

            player.sendMessage("Make sure to enter a spread distance!");

        }

        for (Player p : this.plugin.getServer().getOnlinePlayers())
        {

            double x = (double) random.nextInt(range);
            double z = (double) random.nextInt(range);
            double y = (double) (world.getHighestBlockYAt((int) x, (int) z) + 1);

            Location location = new Location(world, x, y, z);
            p.teleport(location);
            p.sendMessage("Teleported you to: " + x + ", " + z);


        }

        return false;

    }

}
