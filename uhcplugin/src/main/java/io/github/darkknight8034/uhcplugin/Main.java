package io.github.darkknight8034.uhcplugin;

import org.bukkit.plugin.java.JavaPlugin;

// Other files
import io.github.darkknight8034.uhcplugin.commands.Relocate;
import io.github.darkknight8034.uhcplugin.commands.Start;
import io.github.darkknight8034.uhcplugin.commands.End;

// Utils
import io.github.darkknight8034.uhcplugin.utils.Broadcast;
import io.github.darkknight8034.uhcplugin.utils.Border;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.World;

// Main plugin class
public class Main extends JavaPlugin
{

    // Commands
    public Relocate relocate;
    public Start start;
    public End end;

    // Util classs
    public Broadcast broadcast;
    public Border border;

    // Events
    public EventListener eventListener;

    // Config
    public FileConfiguration configFile;

    // Game vars
    public long lastSeed;
    public int alive;
    public int borderSize;
    public World gameWorld;
    
    @Override
    public void onEnable()
    {

        getLogger().info("UHC Plugin enabled!");

        // Commands
        this.relocate = new Relocate(this);
        this.start = new Start(this);
        this.end = new End(this);

        this.broadcast = new Broadcast(this);
        this.border = new Border(this);

        this.eventListener = new EventListener(this);


        loadConfig();

    }

    @Override
    public void onDisable()
    {

        saveConfig();
        getLogger().info("UHC Plugin disabled!");

    }


    public void loadConfig()
    {

        getConfig().options().copyDefaults(true);
        saveConfig();

        this.configFile = getConfig();
        
    }

}
