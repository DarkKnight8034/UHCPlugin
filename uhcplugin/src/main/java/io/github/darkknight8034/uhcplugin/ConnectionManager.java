package io.github.darkknight8034.uhcplugin;

import io.github.darkknight8034.uhcplugin.Main;

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

        HashMap map = new HashMap<String, Object>();
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

        // Sends event data
        try
        {

            if (allowed)
            {
                
                file.write(map.toString());

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

        HashMap map = new HashMap<String, Object>();
        Player player = event.getEntity();

        map.put("event", "PlayerDeathEvent");
        map.put("killed", player.getUniqueId());

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

}