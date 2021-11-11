package io.github.darkknight8034.uhcplugin.utils;

import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.World;

import io.github.darkknight8034.uhcplugin.Main;
import io.github.darkknight8034.uhcplugin.tasks.Shrink;

public class Border
{
    
    private Main plugin;

    public Border(Main plugin)
    {

        this.plugin = plugin;

    }

    public void setBorder(int distance, World world)
    {

        WorldBorder border = world.getWorldBorder();
        // Makes sure border is centered in world
        border.setCenter(0, 0);

        // Sets border
        border.setSize(distance * 2);
        this.plugin.borderSize = distance *  2;

    }

    public void shrink(int distance, World world)
    {

        int incremental = distance / this.plugin.configFile.getInt("game.rules.borderShrinkTime");

        BukkitTask shrinkTask = new Shrink(this.plugin, incremental, world).runTaskTimer(this.plugin, 0, 20);

    }

}