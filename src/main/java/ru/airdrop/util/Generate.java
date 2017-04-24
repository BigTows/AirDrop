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

    public static boolean spawnDragon(Location dropLocation, Airdrop airdrop) {
        if (idTaskDragon != 0) return false;
        Location locDragon = new Location(dropLocation.getWorld(), dropLocation.getX(), dropLocation.getY(), dropLocation.getZ() + 100);
        Entity dragon = locDragon.getWorld().spawnEntity(locDragon, EntityType.ENDER_DRAGON);
        idTaskDragon = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(airdrop,
                new Runnable() {
                    @Override
                    public void run() {
                        locDragon.setZ(locDragon.getZ() - 2);
                        dragon.teleport(locDragon);
                        if (dragon.getLocation().getZ() >= dropLocation.getZ() && dragon.getLocation().getZ() <= dropLocation.getZ() + 1) {
                            dropChest(new Location(locDragon.getWorld(), locDragon.getX(), locDragon.getY(), locDragon.getZ() - 4), airdrop);
                        } else if (dragon.getLocation().getZ() <= dropLocation.getZ() - 100) {
                            dragon.remove();
                            Bukkit.getScheduler().cancelTask(idTaskDragon);
                        }
                    }
                }, 1, 1L);
        return true;
    }

    public static void dropChest(Location locChest, Airdrop airdrop) {
        idTaskChest = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(airdrop,
                new Runnable() {
                    @Override
                    public void run() {
                        locChest.getBlock().setType(Material.AIR);
                        locChest.setY(locChest.getY() - 1);
                        if (locChest.getBlock().getType() != Material.AIR) {
                            locChest.setY(locChest.getY() + 1);
                            locChest.getBlock().setType(Material.CHEST);
                            idTaskDragon = 0;
                            Bukkit.getScheduler().cancelTask(idTaskChest);
                        } else {
                            locChest.getBlock().setType(Material.CHEST);
                        }

                    }
                }, 1, 1L);
    }

}
