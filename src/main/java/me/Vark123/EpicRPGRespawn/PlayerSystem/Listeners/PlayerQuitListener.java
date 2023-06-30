package me.Vark123.EpicRPGRespawn.PlayerSystem.Listeners;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Vark123.EpicRPGRespawn.FileSystem.FileManager;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayer;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		cleanPlayerMess(e.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		cleanPlayerMess(e.getPlayer());
	}
	
	private void cleanPlayerMess(Player p) {
		Optional<RespPlayer> oRespPlayer = RespPlayerManager.get().getRespPlayer(p);
		RespPlayerManager.get().removePlayer(p);
		oRespPlayer.ifPresent(respPlayer -> {
			FileManager.get().savePlayer(respPlayer);
		});
	}

}
