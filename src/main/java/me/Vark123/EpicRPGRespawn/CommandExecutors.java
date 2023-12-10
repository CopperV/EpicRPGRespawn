package me.Vark123.EpicRPGRespawn;

import org.bukkit.Bukkit;

import me.Vark123.EpicRPGRespawn.Misc.EpicTpCommand;
import me.Vark123.EpicRPGRespawn.Misc.PortalCreatorCommand;
import me.Vark123.EpicRPGRespawn.Misc.RespCreatorCommand;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespCmdSystem.PlayerRespawnCommand;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects.Commands.PortalCommand;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects.Commands.PortalEffectCommand;

public final class CommandExecutors {
	
	private CommandExecutors() {}

	public final static void setExecutors() {
		Bukkit.getPluginCommand("powrot").setExecutor(new PlayerRespawnCommand());
		Bukkit.getPluginCommand("epictp").setExecutor(new EpicTpCommand());
		Bukkit.getPluginCommand("odrodzenie").setExecutor(new RespCreatorCommand());
		Bukkit.getPluginCommand("prtl").setExecutor(new PortalCreatorCommand());
		Bukkit.getPluginCommand("portale").setExecutor(new PortalCommand());
		Bukkit.getPluginCommand("portaleffect").setExecutor(new PortalEffectCommand());
	}
	
}
