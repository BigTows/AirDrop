package ru.airdrop.util;

import com.sun.javafx.tk.Toolkit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;
import ru.airdrop.Airdrop;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by bigtows on 24/03/2017.
 */
public class Generate extends JavaPlugin{
public static int idTask = 0;

    public static void spawnDrop(Location location, Airdrop airdrop){
        Location locDragon = new Location(location.getWorld(),location.getX(),location.getY(),location.getZ()+100);
        Entity dragon = locDragon.getWorld().spawnEntity(locDragon, EntityType.ENDER_DRAGON);

        idTask= airdrop.getServer().getScheduler().scheduleSyncRepeatingTask(airdrop, new Runnable() {
            @Override
            public void run() {
                Location loc = new Location(dragon.getLocation().getWorld(),dragon.getLocation().getX(),dragon.getLocation().getY(),dragon.getLocation().getZ()-2);
                dragon.teleport(loc);
                if (dragon.getLocation().getZ()<=location.getZ()){
                    Location chest = loc;
                    chest.setZ(chest.getZ()-4);
                    chest.getBlock().setType(Material.CHEST);
                    Bukkit.broadcastMessage(chest.toString());
                }else if (dragon.getLocation().getZ()>=location.getZ()-100){
                    dragon.remove();
                    Bukkit.broadcastMessage("Yes");
                    Bukkit.getScheduler().cancelTask(idTask);
                }
            }
        },1,1L);

    }

}
