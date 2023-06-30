package me.Vark123.EpicRPGRespawn.PlayerSystem.RespCmdSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Vark123.EpicRPG.Main;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;

public class PlayerRespawnMoveListener implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(e.isCancelled())
			return;
		if(e.getFrom().getBlock().getLocation()
				.equals(e.getTo().getBlock().getLocation()))
			return;
		
		Player p = e.getPlayer();
		if(RespPlayerManager.get()
				.getRespTask(p).isEmpty())
			return;
		
		RespPlayerManager.get().cancelResp(p);
		p.sendMessage(Main.getInstance().getPrefix()+"§cPrzerwano teleportacje!");	
	}

}
