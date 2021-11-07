package io.github.darkknight8034.uhcplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    
    @Override
    public void onEnable()
    {

        getLogger().info("UHC Plugin enabled!");

    }

    @Override
    public void onDisable()
    {

        getLogger().info("UHC Plugin disabled!");

    }

}
