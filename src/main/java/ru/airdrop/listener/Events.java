package ru.airdrop.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.airdrop.Airdrop;

/**
 * Created by bigtows on 24/03/2017.
 */
public class Events implements Listener {
    private final Airdrop plugin;
    public Events(Airdrop instance){
        this.plugin=instance;
    }

    @EventHandler
    public void onLol(){

    }
}
