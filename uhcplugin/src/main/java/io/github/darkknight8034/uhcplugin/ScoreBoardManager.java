package io.github.darkknight8034.uhcplugin;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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

            this.scoreboard = this.manager.getNewScoreboard();
            Objective objective = this.scoreboard.registerNewObjective("health", "health", "Health");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);

        }

        if (this.plugin.configFile.getBoolean("game.settings.display.killCounter"))
        {

            this.scoreboard = this.manager.getNewScoreboard();
            Objective objective = this.scoreboard.registerNewObjective("kills", "playerKillCount", ChatColor.RED + "Player Kills");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        }   

        addPlayers(this.scoreboard);

    }

    public void addPlayers(Scoreboard scoreboard)
    {

        for (Player p: this.plugin.getServer().getOnlinePlayers())
        {

            p.setScoreboard(scoreboard);

        }

    }

}
