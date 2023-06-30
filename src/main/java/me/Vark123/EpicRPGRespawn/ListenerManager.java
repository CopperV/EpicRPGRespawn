package me.Vark123.EpicRPGRespawn;

import org.bukkit.Bukkit;

import me.Vark123.EpicRPGRespawn.BlackrockExtension.BlackrockEntryListener;
import me.Vark123.EpicRPGRespawn.Misc.PlayerInstantRespawnListener;
import me.Vark123.EpicRPGRespawn.Misc.VanillaPortalListener;
import me.Vark123.EpicRPGRespawn.PlayerSystem.Listeners.PlayerJoinListener;
import me.Vark123.EpicRPGRespawn.PlayerSystem.Listeners.PlayerQuitListener;
import me.Vark123.EpicRPGRespawn.PlayerSystem.Listeners.PlayerRespawnEntryListener;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespCmdSystem.PlayerRespawnMoveListener;
import me.Vark123.EpicRPGRespawn.PortalSystem.PlayerPortalEntryListener;

public final class ListenerManager {
	
	private ListenerManager() {}

	public final static void registerListeners() {
		Main inst = Main.inst();
		
		Bukkit.getPluginManager().registerEvents(new PlayerPortalEntryListener(), inst);
		Bukkit.getPluginManager().registerEvents(new VanillaPortalListener(), inst);

		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), inst);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), inst);
		Bukkit.getPluginManager().registerEvents(new PlayerRespawnEntryListener(), inst);
		Bukkit.getPluginManager().registerEvents(new PlayerRespawnMoveListener(), inst);
		Bukkit.getPluginManager().registerEvents(new PlayerInstantRespawnListener(), inst);
		
		Bukkit.getPluginManager().registerEvents(new BlackrockEntryListener(), inst);
	}
	
}
