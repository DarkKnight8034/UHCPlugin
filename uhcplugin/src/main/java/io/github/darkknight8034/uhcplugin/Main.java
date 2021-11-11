package io.github.darkknight8034.uhcplugin;

import org.bukkit.plugin.java.JavaPlugin;

// Other files
import io.github.darkknight8034.uhcplugin.commands.Relocate;
import io.github.darkknight8034.uhcplugin.commands.Start;
import io.github.darkknight8034.uhcplugin.commands.End;
import io.github.darkknight8034.uhcplugin.utils.Broadcast;

// Config imports
import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

// Main plugin class
public class Main extends JavaPlugin
{

    // Commands
    public Relocate relocate;
    public Start start;
    public End end;

    // Util classs
    public Broadcast broadcast;

    // Events
    public EventListener eventListener;

    // Config
    public FileConfiguration configFile;

    public long lastSeed;
    
    @Override
    public void onEnable()
    {

        getLogger().info("UHC Plugin enabled!");

        // Commands
        this.relocate = new Relocate(this);
        this.start = new Start(this);
        this.end = new End(this);
        this.broadcast = new Broadcast(this);
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
