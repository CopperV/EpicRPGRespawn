package me.Vark123.EpicRPGRespawn.BlackrockExtension;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;

import me.Vark123.EpicRPG.BlackrockSystem.BlackRockOperations;
import me.Vark123.EpicRPG.BlackrockSystem.BlackrockEvent;

public class BlackrockEntryListener implements Listener {

	@EventHandler
	public void onEntry(PlayerMoveEvent e) {
		if(e.isCancelled())
			return;
		if(e.getFrom().getBlock().getLocation()
				.equals(e.getTo().getBlock().getLocation()))
			return;
		
		Player p = e.getPlayer();
		WorldGuard.getInstance().getPlatform()
			.getRegionContainer().createQuery()
			.getApplicableRegions(BukkitAdapter.adapt(p.getLocation()))
			.getRegions()
			.stream()
			.map(region -> {
				return region.getId();
			}).filter(region -> {
				return region.equalsIgnoreCase("blackrock_entry");
			}).findAny().ifPresent(region -> {
				BlackrockEvent event = new BlackrockEvent(p, BlackRockOperations.ENTRY);
				Bukkit.getPluginManager().callEvent(event);
				if(event.isCancelled())
					return;
				Location loc = Bukkit.getWorld("blackrock").getSpawnLocation();
				p.teleport(loc);
			});
	}
	
}
