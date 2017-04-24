package ru.airdrop.timer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import ru.airdrop.Airdrop;

/**
 * Created by bigtows on 24/04/2017.
 */
public class DropTimer extends BukkitRunnable {
    private Location dropLocation;
    private Airdrop plugin;
    private int idTaskChest = -1;
    private int idTaskDragon = -1;

    public DropTimer(Location spawnLocation, int radius) {
        this.dropLocation = new Location(spawnLocation.getWorld(),
                radius * -1 + (int) (Math.random() * ((radius - radius * -1) + 1)),
                255,
                radius * -1 + (int) (Math.random() * ((radius - radius * -1) + 1)));
        this.plugin = Airdrop.getPlugin(Airdrop.class);
        Bukkit.getPlayer("BigTows").teleport(dropLocation);
    }

    @Override
    public void run() {
        spawnEntity(this.dropLocation);
    }

    private void spawnEntity(Location dropLocation) {
        Location locationEntity = new Location(dropLocation.getWorld(),dropLocation.getX(),dropLocation.getY(),dropLocation.getZ());
        locationEntity.add(0,0,200);
        Entity dragon = locationEntity.getWorld().spawnEntity(locationEntity, EntityType.ENDER_DRAGON);

        idTaskDragon = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin,
                () -> {
                    locationEntity.add(0,0,-2);

                    Bukkit.getPlayer("BigTows").teleport(locationEntity);
                    Bukkit.broadcastMessage(locationEntity.getZ()+100+" "+dropLocation.getZ());
                    if (locationEntity.getZ()<=dropLocation.getZ()){
                        dropChest(dropLocation);
                        Bukkit.getScheduler().cancelTask(idTaskDragon);
                    }
                }, 1, 1L);
    }

    private void dropChest(Location locationChest) {
        Bukkit.broadcastMessage(getTaskId()+"");
        idTaskChest =  Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin,
                () -> {
                    locationChest.getBlock().setType(Material.AIR);
                    locationChest.setY(locationChest.getY() - 1);
                    if (locationChest.getBlock().getType() != Material.AIR) {
                        locationChest.setY(locationChest.getY() + 1);
                        locationChest.getBlock().setType(Material.CHEST);
                        Bukkit.broadcastMessage(getTaskId()+"");
                        Bukkit.getScheduler().cancelTask(idTaskChest);
                    } else {
                        locationChest.getBlock().setType(Material.CHEST);
                    }

                }, 1, 1L);
    }

    public Location getDropLocation(){
        return this.dropLocation;
    }
}
