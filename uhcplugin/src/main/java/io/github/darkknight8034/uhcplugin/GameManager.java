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

import java.util.HashMap;
import java.util.ArrayList;

import io.github.darkknight8034.uhcplugin.Main;
import io.github.darkknight8034.uhcplugin.events.StartGame;
import io.github.darkknight8034.uhcplugin.events.GameRecap;

public class GameManager
{

    private Main plugin;

    // Last game settings
    public long lastSeed;
    public int range;

    // Current game
    public int alive;
    public HashMap<String, Boolean> perPlayer;
    public int borderSize;
    public World gameWorld;

    // Recap
    public HashMap<String, Integer> kills;
    public ArrayList<String> position;
    
    public GameManager(Main plugin)
    {

        this.plugin = plugin;
        this.kills = new HashMap<String, Integer>();

        for (Player p : this.plugin.getServer().getOnlinePlayers())
        {

            this.kills.put(p.getDisplayName(), 0);

        }

    }

    public void start(Player player, int range, long seed)
    {

        resetCache();
        this.plugin.connManager.sendEvent(new StartGame());

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

            perPlayer.put(p.getDisplayName(), true);

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

    private void resetCache()
    {

        for (String player : this.kills.keySet())
        {

            this.kills.put(player, 0);

        }

        this.alive = this.plugin.getServer().getOnlinePlayers().size();

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

    public void add_kill(String killer, String killed)
    {

        if (killer == "%%natural%%")
        {

            this.alive -= 1;
        
        }
        else
        {

            this.alive -= 1;
            this.kills.put(killer, this.kills.get(killer) + 1);

        }

        this.perPlayer.put(killed, false);

        if (this.alive < 3)
        {

            // Adds killed position if they in the last 3
            position.add(0, killed);

            // If only one left, end game and add them as the winner
            if (this.alive == 1)
            {

                for (String k: this.perPlayer.keySet())
                {

                    if (this.perPlayer.get(k))
                    {

                        position.add(0, k);

                        this.plugin.connManager.sendEvent(new GameRecap(this.kills, this.position));

                        this.plugin.broadcast.title("Game has ended!", "Congradulations: " + k, 1, 3, 1);
                        this.plugin.broadcast.send("The top three are: " + position);
                        
                    }

                }

            }

        } 

    }

}
