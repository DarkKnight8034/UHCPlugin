package io.github.darkknight8034.uhcplugin;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.darkknight8034.uhcplugin.commands.Relocate;
import io.github.darkknight8034.uhcplugin.commands.Start;
import io.github.darkknight8034.uhcplugin.commands.End;
import io.github.darkknight8034.uhcplugin.utils.Broadcast;

// Main plugin class
public class Main extends JavaPlugin
{

    public Relocate relocate;
    public Start start;
    public End end;

    public Broadcast broadcast;
    
    @Override
    public void onEnable()
    {

        getLogger().info("UHC Plugin enabled!");

        // Commands
        this.relocate = new Relocate(this);
        this.start = new Start(this);
        this.end = new End(this);
        this.broadcast = new Broadcast(this);

    }

    @Override
    public void onDisable()
    {

        getLogger().info("UHC Plugin disabled!");

    }

}
