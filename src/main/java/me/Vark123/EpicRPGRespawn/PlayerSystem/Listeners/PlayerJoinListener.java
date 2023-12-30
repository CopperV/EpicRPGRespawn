package me.Vark123.EpicRPGRespawn.PlayerSystem.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.Vark123.EpicRPG.Players.PlayerManager;
import me.Vark123.EpicRPG.Players.RpgPlayer;
import me.Vark123.EpicRPGRespawn.Main;
import me.Vark123.EpicRPGRespawn.FileSystem.FileManager;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayer;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespManager;

public class PlayerJoinListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		RespPlayer respPlayer = FileManager.get().loadPlayer(p);
		
		if(!RespManager.get().getResps().containsKey(respPlayer.getRespLoc())) {
			respPlayer.setRespLoc("spawn1");
		}
		
		RpgPlayer rpg = PlayerManager.getInstance().getRpgPlayer(p);
		if(rpg.getInfo().isTutorial()) {
			respPlayer.setRespLoc("tut");
		}
		
		RespPlayerManager.get().addPlayer(respPlayer);
		
		Location resp = RespManager.get()
				.getResps().get(respPlayer.getRespLoc())
				.getRespLoc().bukkitLocation();
		new BukkitRunnable() {
			@Override
			public void run() {
				p.teleport(resp);
			}
		}.runTaskLater(Main.inst(), 10);
	}
	
}
