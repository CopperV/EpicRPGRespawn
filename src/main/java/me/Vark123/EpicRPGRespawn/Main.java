package me.Vark123.EpicRPGRespawn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.Vark123.EpicRPGRespawn.FileSystem.FileManager;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayer;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespManager;

@Getter
public class Main extends JavaPlugin {

	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		FileManager.get();
		CommandExecutors.setExecutors();
		ListenerManager.registerListeners();
		
		Bukkit.getOnlinePlayers().forEach(p -> {
			RespPlayer respPlayer = FileManager.get().loadPlayer(p);
			if(!RespManager.get().getResps().containsKey(respPlayer.getRespLoc())) {
				respPlayer.setRespLoc("spawn1");
			}
			RespPlayerManager.get().addPlayer(respPlayer);
		});
		
		super.onEnable();
	}

	@Override
	public void onDisable() {
		RespPlayerManager.get().clearContainer();
		RespManager.get().getResps().clear();
		PortalManager.get().getPortals().clear();
		super.onDisable();
	}
	
	public static final Main inst() {
		return instance;
	}

}
