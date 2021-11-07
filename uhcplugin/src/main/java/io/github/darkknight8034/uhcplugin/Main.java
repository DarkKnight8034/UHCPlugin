package io.github.darkknight8034.uhcplugin;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.darkknight8034.uhcplugin.commands.Relocate;

public class Main extends JavaPlugin
{
    
    @Override
    public void onEnable()
    {

        getLogger().info("UHC Plugin enabled!");
        new Relocate(this);

    }

    @Override
    public void onDisable()
    {

        getLogger().info("UHC Plugin disabled!");

    }

}
