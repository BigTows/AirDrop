package ru.airdrop;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.airdrop.listener.Events;
import ru.airdrop.util.Generate;

public final class Airdrop extends JavaPlugin implements Listener{

    PluginManager pm = this.getServer().getPluginManager();

    @Override
    public void onEnable() {
        this.pm.registerEvents(new Events(this),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spawn")){
            Generate.spawnDragon(new Location(getServer().getWorld("world"),-1333,160,1),this);
        }
        return true;
    }
}

