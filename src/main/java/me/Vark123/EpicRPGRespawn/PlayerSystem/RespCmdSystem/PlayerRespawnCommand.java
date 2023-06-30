package me.Vark123.EpicRPGRespawn.PlayerSystem.RespCmdSystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;

public class PlayerRespawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("powrot"))
			return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cTej komendy moze uzyc tylko gracz");
			return false;
		}
		
		Player p = (Player) sender;
		RespPlayerManager.get().respPlayerCooldown(p);
		return true;
	}

}
