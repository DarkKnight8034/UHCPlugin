package io.github.darkknight8034.uhcplugin.commands;

// Command imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;


import io.github.darkknight8034.uhcplugin.Main;

// Start game command
public class Start implements CommandExecutor
{
    
    private Main plugin;

    public Start(Main plugin)
    {
        
        this.plugin = plugin;
        plugin.getCommand("start").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        int range = 250;
        try 
        {

            range = Integer.parseInt(args[0]);

        }
        catch (Exception e)
        {}


        this.plugin.gameManager.start((Player) sender, range, 0);


        return false;
    
    }

}
