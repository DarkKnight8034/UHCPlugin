package io.github.darkknight8034.uhcplugin.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.World;

import io.github.darkknight8034.uhcplugin.Main;
import io.github.darkknight8034.uhcplugin.utils.Border;

public class Shrink extends BukkitRunnable
{
    
    private Main plugin;
    private int seconds;
    private int incrememtal;
    private World world;

    public Shrink(Main plugin, int incrememtal, World world)
    {

        this.plugin = plugin;
        this.seconds = plugin.configFile.getInt("game.settings.borderShrinkTime");
        this.incrememtal = incrememtal;
        this.world = world;

    }

    @Override
    public void run()
    {

        this.seconds --;
        this.plugin.border.setBorder((int) world.getWorldBorder().getSize() - this.incrememtal, this.world);

        if (this.seconds <= 0)
        {

            this.cancel();

        }
            
    }

}
