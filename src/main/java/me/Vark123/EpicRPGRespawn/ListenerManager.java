package me.Vark123.EpicRPGRespawn;

import org.bukkit.Bukkit;

import me.Vark123.EpicRPGRespawn.Misc.VanillaPortalListener;
import me.Vark123.EpicRPGRespawn.PortalSystem.PlayerPortalEntryListener;

public final class ListenerManager {
	
	private ListenerManager() {}

	public final static void registerListeners() {
		Main inst = Main.inst();
		
		Bukkit.getPluginManager().registerEvents(new PlayerPortalEntryListener(), inst);
		Bukkit.getPluginManager().registerEvents(new VanillaPortalListener(), inst);
	}
	
}