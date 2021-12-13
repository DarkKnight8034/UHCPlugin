package io.github.darkknight8034.uhcplugin.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import io.github.darkknight8034.uhcplugin.Main;

public class GameRecap extends Event
{
    
    public HashMap<String, Integer> kills;
    public ArrayList<String> podium;

    public GameRecap(HashMap<String, Integer> kills, ArrayList<String> podium)
    {

        this.kills = kills;
        this.podium = podium;
    
    }

    @Override
    public HandlerList getHandlers() {
        // TODO Auto-generated method stub
        return null;
    }

}
