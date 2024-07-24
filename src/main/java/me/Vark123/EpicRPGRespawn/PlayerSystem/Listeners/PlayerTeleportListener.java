package me.Vark123.EpicRPGRespawn.PlayerSystem.Listeners;

import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;

import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayer;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespawnPoint;

public class PlayerTeleportListener implements Listener {

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if(e.isCancelled())
			return;
		
		Player p = e.getPlayer();
		Optional<RespPlayer> oRespPlayer = RespPlayerManager.get().getRespPlayer(p);
		if(oRespPlayer.isEmpty())
			return;
		RespPlayer respPlayer = oRespPlayer.get();
		if(!respPlayer.canModifyResp())
			return;
		
		Location loc = e.getTo();
		Map<String, RespawnPoint> resps = RespManager.get().getResps();
		WorldGuard.getInstance().getPlatform()
			.getRegionContainer().createQuery()
			.getApplicableRegions(BukkitAdapter.adapt(loc))
			.getRegions().stream()
			.map(region -> {
				return region.getId();
			}).filter(region -> {
				return resps.containsKey(region) 
						&& !respPlayer.getRespLoc().equals(region);
			}).findAny().ifPresent(region -> {
				RespawnPoint point = resps.get(region);
				if(!p.hasPermission(point.getPortalPerm())) {
					Bukkit.dispatchCommand(
							Bukkit.getConsoleSender(),
							"lp user "+p.getName()+" permission set "+point.getPortalPerm()+" true epicrpg"
					);
				}
				respPlayer.setRespLoc(region);
				p.playSound(p, Sound.ENTITY_VILLAGER_CELEBRATE, 1, 1);
				p.sendTitle(" ", "§a§lNOWY PUNKT ODRODZENIA", 5, 10, 15);
			});
	}
	
}
