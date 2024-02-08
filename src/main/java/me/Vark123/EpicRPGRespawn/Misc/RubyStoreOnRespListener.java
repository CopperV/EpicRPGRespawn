package me.Vark123.EpicRPGRespawn.Misc;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;

import me.Vark123.EpicRPG.RubySystem.RubyStorageEvent;
import me.Vark123.EpicRPGRespawn.RespSystem.RespManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespawnPoint;

public class RubyStoreOnRespListener implements Listener {

	@EventHandler
	public void onStore(RubyStorageEvent e) {
		if(e.isCancelled())
			return;
		
		Player p = e.getP();
		Location loc = p.getLocation();
		
		Map<String, RespawnPoint> resps = RespManager.get().getResps();
		WorldGuard.getInstance().getPlatform()
			.getRegionContainer().createQuery()
			.getApplicableRegions(BukkitAdapter.adapt(loc))
			.getRegions().stream()
			.map(region -> region.getId())
			.filter(resps::containsKey)
			.findAny()
			.ifPresent(region -> {
				e.setUse(0);
			});
	}
	
}
