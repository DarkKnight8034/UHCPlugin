package io.github.darkknight8034.uhcplugin.utils;

import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.Location;

import java.util.Random;
import java.lang.Math;

import io.github.darkknight8034.uhcplugin.Main;

public class Relocate
{
    
    private Main plugin;
    private Random random = new Random();

    public Relocate(Main plugin)
    {

        this.plugin = plugin;

    }

    // Relocates players within a range
    public void relocate(World world, int range)
    {

        for (Player p : this.plugin.getServer().getOnlinePlayers())
        {

            int x = random.nextInt(range);
            double positive = Math.round(Math.random());
            x *= positive * -1;

            int z = random.nextInt(range);
            positive = Math.round(Math.random());
            x *= positive * -1;

            int y = world.getHighestBlockYAt((int) x, (int) z) + 2;

            Location location = new Location(world, x, y, z);
            p.teleport(location);
            p.sendMessage("Teleported you to: " + x + ", " + z);


        }

    }

}
