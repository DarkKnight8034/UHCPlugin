package io.github.darkknight8034.uhcplugin.commands;

// Command imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

// World imports
import org.bukkit.WorldBorder;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

// UHC-like imports
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.GameRule;

// Invincibility imports
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;

import io.github.darkknight8034.uhcplugin.Main;

// Start game command
public class Start implements CommandExecutor
{
    
    private Main plugin;
    private World gameWorld;

    public Start(Main plugin)
    {
        
        this.plugin = plugin;
        plugin.getCommand("start").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        Player player = (Player) sender;
        World world;
        if (this.gameWorld == null)
        {

            if (!this.plugin.configFile.getBoolean("game.start.randomWorld"))
            {

                world = player.getWorld();

            }
            else
            {

                this.plugin.broadcast.title("Sending you to game world! Please wait.", "", 1, 5, 1);
                WorldCreator creator = new WorldCreator("Game_World " + this.plugin.getServer().getWorlds().size());
                world = creator.createWorld();
                this.plugin.lastSeed = world.getSeed();

            }

        }
        else
        {

            world = this.gameWorld;

        }


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


        // Relocates players and creates world boarder
        worldBorder(world, range + 50);
        plugin.relocate.relocate(world, range);


        int duration = 20 * 60; // Not because i can't do math, easier to change. ticks/sec * seconds
        // Effects and initalization
        PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, 10);
        for (Player p : this.plugin.getServer().getOnlinePlayers())
        {

            // Removes any effects
            for (PotionEffect effect : p.getActivePotionEffects())
            {

                p.removePotionEffect(effect.getType());

            }


            // Gives resistance
            LivingEntity entity = (LivingEntity) p;
            resistance.apply(entity);

            // Heals player
            p.setHealth(20);

            // Says deprecated, i dont care
            p.playEffect(p.getLocation(), Effect.ENDERDRAGON_GROWL, 1);

            HumanEntity human = (HumanEntity) p;
            human.setGameMode(GameMode.SURVIVAL);

        }


        // Gives one life
        world.setDifficulty(Difficulty.HARD);
        world.setGameRule(GameRule.NATURAL_REGENERATION, true);

        // Msgs that game has started
        this.plugin.broadcast.title(ChatColor.RED + "The game has started!", "Good luck!", 1, 3, 1);


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


    public void startWithSeed(long seed, Player sender, Command cmd, String label, String[] args)
    {

        WorldCreator creator = new WorldCreator("Game_World " + this.plugin.getServer().getWorlds().size());
        creator.seed(seed);
        this.gameWorld = creator.createWorld();

        onCommand(sender, cmd, label, args);

    }

}
