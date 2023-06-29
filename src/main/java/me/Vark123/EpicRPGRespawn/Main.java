package me.Vark123.EpicRPGRespawn;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.Vark123.EpicRPGRespawn.FileSystem.FileManager;

@Getter
public class Main extends JavaPlugin {

	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		FileManager.get();
		CommandExecutors.setExecutors();
		ListenerManager.registerListeners();
		
		super.onEnable();
	}

	@Override
	public void onDisable() {
		
		super.onDisable();
	}
	
	public static final Main inst() {
		return instance;
	}

}
