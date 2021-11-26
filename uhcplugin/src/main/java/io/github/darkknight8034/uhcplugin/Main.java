package io.github.darkknight8034.uhcplugin;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.darkknight8034.uhcplugin.commands.Start;
import io.github.darkknight8034.uhcplugin.commands.End;

// Utils
import io.github.darkknight8034.uhcplugin.utils.Broadcast;
import io.github.darkknight8034.uhcplugin.utils.Relocate;
import io.github.darkknight8034.uhcplugin.utils.Border;

// Managers
import io.github.darkknight8034.uhcplugin.GameManager;
import io.github.darkknight8034.uhcplugin.ConnectionManager;

import org.bukkit.configuration.file.FileConfiguration;

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

    // Managers
    public GameManager gameManager;
    public ScoreBoardManager sbManager;
    public ConnectionManager connManager;

    // Events
    public EventListener eventListener;

    // Config
    public FileConfiguration configFile;
    
    @Override
    public void onEnable()
    {

        getLogger().info("UHC Plugin enabled!");
        loadConfig();

        // Commands
        this.relocate = new Relocate(this);
        this.start = new Start(this);
        this.end = new End(this);

        this.broadcast = new Broadcast(this);
        this.border = new Border(this);

        this.eventListener = new EventListener(this);

        this.gameManager = new GameManager(this);
        this.sbManager = new ScoreBoardManager(this);
        this.connManager = new ConnectionManager(this);

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
