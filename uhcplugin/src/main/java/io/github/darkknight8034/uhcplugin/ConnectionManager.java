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

    private FileWriter file;
    private Main plugin;

    public ConnectionManager(Main plugin)
    {

        this.plugin = plugin;
        try
        {

            this.file = new FileWriter((String) plugin.configFile.get("discord.eventPath"));

        }
        catch (IOException e)
        {
         
            this.plugin.getLogger().info("ERROR! There was a problem opening the file: " + plugin.configFile.getString("discord.eventPath"));

        }

    }

    public void sendEvent(Event event)
    {

        HashMap map;

        if (event.getClass().isInstance(PlayerDeathEvent.class))
        {

            map = kill((PlayerDeathEvent) event);

        }

        try
        {

            this.file.write(map.toString());

        }
        catch(IOException e) 
        {

            this.plugin.getLogger().info("There was an error writing to the file: " + this.plugin.configFile.getString("discord.eventPath"))

        }

    }

    private HashMap<String, Object> kill(PlayerDeathEvent event)
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