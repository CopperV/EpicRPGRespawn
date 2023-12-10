package me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPGRespawn.PortalSystem.PortalMenuManager;

public class PortalCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("portale"))
			return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cTej komendy moze uzyc tylko gracz");
			return false;
		}
		
		Player p = (Player) sender;
		PortalMenuManager.get().openMenu(p);
		return true;
	}

}
