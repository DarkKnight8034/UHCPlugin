package io.github.darkknight8034.uhcplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import io.github.darkknight8034.uhcplugin.Main;

public class Relocate implements CommandExecutor
{
    
    private Main plugin;

    public Relocate(Main plugin)
    {

        this.plugin = plugin;
        plugin.getCommand("relocate").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        Player player = (Player) sender;
        player.sendMessage(args);

        // for (Player p : this.plugin.getServer().getOnlinePlayers())
        // {

            

        // }

        return false;

    }

}
