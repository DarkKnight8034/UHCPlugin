package io.github.darkknight8034.uhcplugin.commands;

// Command imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

// World imports
import org.bukkit.entity.Player;

import io.github.darkknight8034.uhcplugin.Main;

public class End implements CommandExecutor
{

    private Main plugin;

    public End(Main plugin)
    {

        this.plugin = plugin;
        plugin.getCommand("end").setExecutor(this);

    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        this.plugin.gameManager.end((Player) sender);

        return false;

    }

}
