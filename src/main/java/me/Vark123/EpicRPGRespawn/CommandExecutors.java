package me.Vark123.EpicRPGRespawn;

import org.bukkit.Bukkit;

import me.Vark123.EpicRPGRespawn.PlayerSystem.RespCmdSystem.PlayerRespawnCommand;

public final class CommandExecutors {
	
	private CommandExecutors() {}

	public final static void setExecutors() {
		Bukkit.getPluginCommand("powrot").setExecutor(new PlayerRespawnCommand());
	}
	
}
