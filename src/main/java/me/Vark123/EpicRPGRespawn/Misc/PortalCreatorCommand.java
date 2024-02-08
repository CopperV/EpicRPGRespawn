package me.Vark123.EpicRPGRespawn.Misc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPGRespawn.FileSystem.FileManager;
import me.Vark123.EpicRPGRespawn.PortalSystem.APortal;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalManager;
import me.Vark123.EpicRPGRespawn.PortalSystem.Portals.PermPortal;
import me.Vark123.EpicRPGRespawn.PortalSystem.Portals.PremiumPortal;
import me.Vark123.EpicRPGRespawn.PortalSystem.Portals.StandardPortal;
import me.Vark123.EpicRPGRespawn.Utils.RpgLocation;

public class PortalCreatorCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("prtl"))
			return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cKomenda tylko dla graczy");
			return false;
		}
		Player p = (Player) sender;
		if(!p.hasPermission("prtl.cmd")) {
			p.sendMessage("Nie masz uprawnien do tej komendy");
			return false;
		}
		if(args.length<2) {
			sender.sendMessage("§4Uzycie: §a/prtl [region] [standard/premium/perm] <perm>");
			return false;
		}
		
		RpgLocation loc = new RpgLocation(p.getLocation());
		APortal portal;
		String perm = null;
		switch(args[1].toLowerCase()) {
			case "standard":
				portal = new StandardPortal(args[0], loc);
				break;
			case "premium":
				if(args.length > 2) 
					perm = args[2];
				portal = new PremiumPortal(args[0], loc, perm);
				break;
			case "perm":
				if(args.length > 2) 
					perm = args[2];
				portal = new PermPortal(args[0], loc, perm);
				break;
			default:
				p.sendMessage("§cNiepoprawny typ portalu");
				return false;
		}
		
		FileManager.get().savePortal(portal);
		PortalManager.get().getPortals().put(portal.getPortalRegion(), portal);
		p.sendMessage("§aUtworzyles nowy portal");
		return true;
	}

}
