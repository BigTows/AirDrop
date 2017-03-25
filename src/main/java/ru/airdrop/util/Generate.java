package ru.airdrop.util;

import com.sun.javafx.tk.Toolkit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
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
public class Generate extends JavaPlugin {
    public static int idTaskDragon = 0;
    public static int idTaskChest = 0;

    /**
     * TODO add status AirDrop
     * @param location
     * @param airdrop
     */
    public static void spawnDragon(Location location,Airdrop airdrop) {
        Location locDragon = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() + 100);
        Entity dragon = locDragon.getWorld().spawnEntity(locDragon, EntityType.ENDER_DRAGON);
        Server server = Bukkit.getServer();
        idTaskDragon = server.getScheduler().scheduleSyncRepeatingTask(airdrop,
                new Runnable() {
                    @Override
                    public void run() {
                        Location loc = new Location(dragon.getLocation().getWorld(), dragon.getLocation().getX(), dragon.getLocation().getY(), dragon.getLocation().getZ() - 2);
                        dragon.teleport(loc);
                        if (dragon.getLocation().getZ() >= location.getZ() && dragon.getLocation().getZ() <=location.getZ()+1) {
                            Location chest = loc;
                            chest.setZ(chest.getZ() - 4);
                            chest.getBlock().setType(Material.CHEST);
                            dropChest(chest,airdrop);
                            Bukkit.broadcastMessage(chest.toString());
                        } else if (dragon.getLocation().getZ() <= location.getZ()-100) {
                            dragon.remove();
                            Bukkit.broadcastMessage(dragon.getLocation().getZ()+" : "+location.getZ());
                            Bukkit.getScheduler().cancelTask(idTaskDragon);
                        }
                    }
                }, 1, 1L);

    }

    public static void dropChest(Location locChest, Airdrop airdrop){
        Server server = Bukkit.getServer();
        idTaskChest = server.getScheduler().scheduleSyncRepeatingTask(airdrop,
                new Runnable() {
                    @Override
                    public void run() {
                        locChest.getBlock().setType(Material.AIR);
                        Location underChest = new Location(locChest.getWorld(),locChest.getX(),locChest.getY()-1,locChest.getZ());
                        if (underChest.getBlock().getType()!=Material.AIR){
                            locChest.getBlock().setType(Material.CHEST);
                            Bukkit.getScheduler().cancelTask(idTaskChest);
                        }else {
                            locChest.setY(locChest.getY() - 1);
                            locChest.getBlock().setType(Material.CHEST);
                        }

                    }
                }, 1, 1L);
    }

}
