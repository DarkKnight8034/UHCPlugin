package io.github.darkknight8034.uhcplugin.commands;

// Command imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import io.github.darkknight8034.uhcplugin.Main;

public class Recreate implements CommandExecutor
{
    
    private Main plugin;

    public Recreate(Main plugin)
    {

        this.plugin = plugin;
        plugin.getCommand("test").setExecutor(this);
        
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        this.plugin.start.startWithSeed(this.plugin.lastSeed, (Player) sender, cmd, label, args);

        return false;

    }

}