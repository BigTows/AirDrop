package ru.airdrop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.airdrop.listener.Events;
import ru.airdrop.timer.DropTimer;
import ru.airdrop.util.Generate;

public final class Airdrop extends JavaPlugin implements Listener{

    private PluginManager pluginManager = this.getServer().getPluginManager();
    private DropTimer timerTask;
    private FileConfiguration config = this.getConfig();
    @Override
    public void onEnable() {
        this.pluginManager.registerEvents(new Events(this),this);
        this.saveDefaultConfig();
        timerTask = new DropTimer(Bukkit.getWorld(config.getString("Location.defaultworld")).getSpawnLocation(),config.getInt("Location.radius"));
        timerTask.runTaskTimer(this,1L,(long) config.getDouble("Timer.reCall")*60*20);
    }

    @Override
    public void onDisable() {
       timerTask.cancel();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spawn")){
            return Generate.spawnDragon(new Location(getServer().getWorld("world"),-900,160,1),this);
        }
        return false;
    }
}

