package io.github.darkknight8034.uhcplugin;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.bukkit.command.Command;

import io.github.darkknight8034.uhcplugin.Main;

public class ScoreBoardManager
{
    
    private Main plugin;
    public ScoreboardManager manager;

    public Scoreboard scoreboard;

    public ScoreBoardManager(Main plugin)
    {

        this.plugin = plugin;
        this.manager = plugin.getServer().getScoreboardManager();

        if (this.plugin.configFile.getBoolean("game.settings.display.healthDisplay"))
        {

            this.plugin.broadcast.command("scoreboard objectives add Hearts health");
            this.plugin.broadcast.command("scoreboard objectives setdisplay belowName Hearts");

        }

        if (this.plugin.configFile.getBoolean("game.settings.display.killCounter"))
        {

            this.plugin.broadcast.command("scoreboard objectives add Kills playerKillCount " + ChatColor.RED + "Player Kills");
            this.plugin.broadcast.command("scoreboard objectives setdisplay sidebar Kills");
            
        }   

        // addPlayers(this.scoreboard);

    }

    public void addPlayers(Scoreboard scoreboard)
    {

        for (Player p: this.plugin.getServer().getOnlinePlayers())
        {

            p.setScoreboard(scoreboard);

        }

    }

}
