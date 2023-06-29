package me.Vark123.EpicRPGRespawn.Misc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class VanillaPortalListener implements Listener {

	@EventHandler
	public void onPortalEntry(PlayerPortalEvent e) {
		e.setCancelled(true);
	}
	
}
