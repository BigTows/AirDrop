package ru.airdrop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.airdrop.listener.Events;
import ru.airdrop.util.Generate;
import ru.airdrop.util.Settings;

public final class Airdrop extends JavaPlugin implements Listener{

    PluginManager pm = this.getServer().getPluginManager();

    @Override
    public void onEnable() {

        this.pm.registerEvents(new Events(this),this);
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spawn")){
            return Generate.spawnDragon(new Location(getServer().getWorld("world"),-900,160,1),this);
        }
        return false;
    }
}

