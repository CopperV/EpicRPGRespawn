package me.Vark123.EpicRPGRespawn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager;
import lombok.Getter;
import me.Vark123.EpicRPGRespawn.FileSystem.FileManager;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayer;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespManager;

@Getter
public class Main extends JavaPlugin {

	private static Main instance;
	
	@Getter
	private InventoryManager manager;
	
	@Override
	public void onEnable() {
		instance = this;
		
		manager = new InventoryManager(instance);
		manager.invoke();
		
		FileManager.get();
		CommandExecutors.setExecutors();
		ListenerManager.registerListeners();
		Config.get().init();
		
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
