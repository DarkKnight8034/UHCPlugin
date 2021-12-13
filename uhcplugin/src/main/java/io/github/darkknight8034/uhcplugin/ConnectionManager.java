package io.github.darkknight8034.uhcplugin;

import io.github.darkknight8034.uhcplugin.Main;
import io.github.darkknight8034.uhcplugin.events.GameRecap;
import io.github.darkknight8034.uhcplugin.events.StartGame;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ConnectionManager
{

    private Main plugin;

    public ConnectionManager(Main plugin)
    {

        this.plugin = plugin;

    }

    public boolean sendEvent(Event event)
    {

        HashMap<String, Object> map = new HashMap<String, Object>();
        boolean allowed = true;
        FileWriter file;

        // Creates file writer
        try
        {

            file = new FileWriter((String) plugin.configFile.get("discord.eventPath"));

        }
        catch (IOException e)
        {
         
            this.plugin.getLogger().info("ERROR! There was a problem opening the file: " + plugin.configFile.getString("discord.eventPath"));
            return false;

        }

        
        // Gets event data
        if (event.getClass().isInstance(PlayerDeathEvent.class))
        {

            map = deathEvent((PlayerDeathEvent) event);
            allowed = this.plugin.configFile.getBoolean("discord.events.playerDeathEvent");

        }
        else if (event.getClass().isInstance(GameRecap.class))
        {

            map = gameRecap((GameRecap) event);
            allowed = this.plugin.configFile.getBoolean("discord.event.gameRecap");

        }
        else if (event.getClass().isInstance(StartGame.class))
        {

            map = startGame((StartGame) event);
            allowed = true; // Discord bot needs this, always allowed

        }

        // Sends event data
        try
        {

            if (allowed)
            {
                
                file.write(map.toString());
                file.close();

            }

        }
        catch(IOException e) 
        {

            this.plugin.getLogger().info("There was an error writing to the file: " + this.plugin.configFile.getString("discord.eventPath"));

        }

        return true;

    }

    // Converts PlayerDeathEvent into hashmap for json
    private HashMap<String, Object> deathEvent(PlayerDeathEvent event)
    {

        HashMap<String, Object> map = new HashMap<String, Object>();
        Player player = event.getEntity();

        map.put("event", "PlayerDeathEvent");
        map.put("killed", player.getDisplayName());

        // Can return null for killer if player died of natural causes
        Player killer = ((LivingEntity) player).getKiller();
        if (killer != null)
        {

            map.put("killer", killer.getDisplayName());

        }
        else
        {

            map.put("killer", "Natural Causes");

        }

        map.put("message", event.getDeathMessage());

        return map;

    }

    // Converts GameRecap into hashmap for json
    private HashMap<String, Object> gameRecap(GameRecap event)
    {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("event", "GameRecap");
        map.put("kills", event.kills);
        map.put("podium", event.podium);

        return map;

    }

    // Converts StartGame into hashmap for json
    private HashMap<String, Object> startGame(StartGame event)
    {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("event", "startGame");

        return map;

    }

}