package me.Vark123.EpicRPGRespawn.PlayerSystem.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.Vark123.EpicRPG.Core.Events.RpgPlayerJoinEvent;
import me.Vark123.EpicRPG.Players.RpgPlayer;
import me.Vark123.EpicRPGRespawn.Config;
import me.Vark123.EpicRPGRespawn.Config.ResourcePack;
import me.Vark123.EpicRPGRespawn.Main;
import me.Vark123.EpicRPGRespawn.FileSystem.FileManager;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayer;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespManager;

public class PlayerJoinListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
//	public void onJoin(PlayerJoinEvent e) {
	public void onJoin(RpgPlayerJoinEvent e) {
		RpgPlayer rpg = e.getRpg();
		Player p = rpg.getPlayer();
		RespPlayer respPlayer = FileManager.get().loadPlayer(p);
		
		if(!RespManager.get().getResps().containsKey(respPlayer.getRespLoc())) {
			respPlayer.setRespLoc("spawn1");
		}
		
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
				ResourcePack var = Config.get().getResourcepack();
				p.teleport(resp);
				p.setResourcePack(var.getUrl());
//				p.setResourcePack(var.getUrl(), var.getHash());
			}
		}.runTaskLater(Main.inst(), 10);
	}
	
}
