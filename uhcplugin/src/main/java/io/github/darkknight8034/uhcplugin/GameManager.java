package io.github.darkknight8034.uhcplugin;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.GameMode;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;

import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.ChatColor;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Effect;

import io.github.darkknight8034.uhcplugin.Main;

public class GameManager
{

    private Main plugin;

    // Last game settings
    public long lastSeed;
    public int range;

    // Current game
    public int alive;
    public int borderSize;
    public World gameWorld;
    
    public GameManager(Main plugin)
    {

        this.plugin = plugin;

    }

    public void start(Player player, int range, long seed)
    {

        this.alive = this.plugin.getServer().getOnlinePlayers().size();

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
                world = createWorld("Game_World" + this.plugin.getServer().getWorlds().size(), seed);
                this.lastSeed = seed;

            }

        }
        else
        {

            world = this.gameWorld;

        }

        // Relocates players and creates world boarder
        this.plugin.border.setBorder(range + 50, world);
        plugin.relocate.relocate(world, range);

        prepPlayers();

        // Gives one life
        world.setDifficulty(Difficulty.HARD);
        world.setGameRule(GameRule.NATURAL_REGENERATION, true);

        // Msgs that game has started
        this.plugin.broadcast.title(ChatColor.RED + "The game has started!", "Good luck!", 1, 3, 1);

    }

    public void end(Player player)
    {

        World world;

        if (!this.plugin.configFile.getBoolean("game.end.returnToLobby"))
        {

            world = player.getWorld();

        }
        else
        {

            if (this.plugin.configFile.getString("game.end.lobby") == null)
            {

                world = this.plugin.getServer().getWorlds().get(0);

            }
            else
            {

                world = this.plugin.getServer().getWorld(this.plugin.configFile.getString("game.end.lobby"));
                if (world == null)
                {

                    player.sendMessage(ChatColor.RED + "No world was found with the name: \"" + this.plugin.configFile.getString("lobby") + "\"! Could not send players back to lobby.");

                }

            }

        }

        // Turns off hardcore
        world.setHardcore(false);

        // Brings players back to spawn
        this.plugin.relocate.relocate(world, 1);

        this.plugin.broadcast.send(ChatColor.GREEN + "The game has ended!");

        resetPlayers();

    }

    private void prepPlayers()
    {

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
            human.setFoodLevel(20);
            human.setSaturation(5);

        }

    }

    private void resetPlayers()
    {

        // Removes any effects on the players and sets to adventure mode
        for (LivingEntity p : this.plugin.getServer().getOnlinePlayers())
        {

            for (PotionEffect effect : p.getActivePotionEffects())
            {

                p.removePotionEffect(effect.getType());

            }

            HumanEntity human = (HumanEntity) p;
            human.setGameMode(GameMode.ADVENTURE);

        }

    }

    private World createWorld(String name, long seed)
    {

        WorldCreator creator = new WorldCreator(name);

        if (seed == 0)
        {

            return creator.createWorld();

        }

        creator.seed(seed);
        return creator.createWorld();

    }

    public void recreate(Player player)
    {

        start(player, this.range, lastSeed);

    }

}
