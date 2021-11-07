package io.github.darkknight8034.uhcplugin.commands;

// Command imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

// World imports
import org.bukkit.WorldBorder;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

// UHC-like imports
import org.bukkit.Difficulty;
import org.bukkit.GameRule;

// Invincibility imports
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.LivingEntity;

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

        Player player = (Player) sender;
        World world = player.getWorld();


        // Determines spread range
        int range = 250;
        try 
        {

            range = Integer.parseInt(args[0]);

        }
        catch (Exception e)
        {

            player.sendMessage("Make sure to enter a spread distance!\nCommand is formatted: /<command> <spread_range>");

        }


        int duration = 20 * 60; // Not because i can't do math, easier to change. ticks/sec * seconds
        // Gives 1 minutes invincibility
        PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, 10);
        for (Player p : this.plugin.getServer().getOnlinePlayers())
        {

            LivingEntity entity = (LivingEntity) p;
            resistance.apply(entity);

        }


        // Relocates players and creates world boarder
        worldBorder(world, range + 50);
        plugin.relocate.relocate(world, range);


        // Gives one life
        world.setDifficulty(Difficulty.HARD);
        world.setGameRule(GameRule.NATURAL_REGENERATION, true);

        // Msgs that game has started
        this.plugin.broadcast.title(ChatColor.RED + "The game has started!", "Good luck!", 1, 5, 1);


        return false;
    
    }

    // Sets world border
    public void worldBorder(World world, int distance)
    {

        WorldBorder border = world.getWorldBorder();
        // Makes sure border is centered in world
        border.setCenter(0, 0);

        // Sets border
        border.setSize(distance * 2);

    }

}
