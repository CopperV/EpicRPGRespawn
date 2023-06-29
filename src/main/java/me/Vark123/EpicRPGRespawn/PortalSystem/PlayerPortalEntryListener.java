package me.Vark123.EpicRPGRespawn.PortalSystem;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;

public class PlayerPortalEntryListener implements Listener {

	@EventHandler
	public void onEntry(PlayerMoveEvent e) {
		if(e.isCancelled())
			return;
		if(e.getFrom().getBlock().getLocation()
				.equals(e.getTo().getBlock().getLocation()))
			return;
		
		Player p = e.getPlayer();
		Map<String, APortal> portals = PortalManager.get().getPortals();
		WorldGuard.getInstance().getPlatform()
			.getRegionContainer().createQuery()
			.getApplicableRegions(BukkitAdapter.adapt(e.getTo()))
			.getRegions().stream()
			.map(region -> {
				return region.getId();
			}).filter(region -> {
				return portals.keySet().contains(region);
			}).findAny().ifPresent(region -> {
				APortal portal = portals.get(region);
				if(!portal.canUsePortal(p))
					return;
				portal.teleport(p);
				p.sendMessage("§7[§aTELEPORTACJA§7]");
			});
	}
	
}
