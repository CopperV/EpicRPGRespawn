package me.Vark123.EpicRPGRespawn.Misc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPGRespawn.FileSystem.FileManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespawnPoint;
import me.Vark123.EpicRPGRespawn.Utils.RpgLocation;

public class RespCreatorCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("odrodzenie"))
			return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cKomenda tylko dla graczy");
			return false;
		}
		
		Player p = (Player) sender;
		if(!p.hasPermission("respawn.cmd")) {
			p.sendMessage("§cNie masz uprawnien do tej komendy");
			return false;
		}
		switch(args.length) {
			case 0:
				sendCorrectUsage(p);
				break;
			case 1:
				if(args[0].equalsIgnoreCase("lista")) {
					p.sendMessage("§2Lista dostepnych punktow odrodzen");
					RespManager.get()
						.getResps().values()
						.forEach(resp -> {
							p.sendMessage("§a- "+resp.toString());
						});
				} else {
					sendCorrectUsage(p);
				}
				break;
			case 2:
				if(args[0].equalsIgnoreCase("usun")) {
					String respName = args[1];
					if(!RespManager.get().getResps().containsKey(respName)) {
						p.sendMessage("§cNie istnieje respawn o nazwie "+respName);
						break;
					}
					RespawnPoint resp = RespManager.get().getResps().remove(respName);
					FileManager.get().deleteResp(resp);
				} else {
					sendCorrectUsage(p);
				}
				break;
			case 3:
				if(args[0].equalsIgnoreCase("ustaw")) {
					RpgLocation respLoc = new RpgLocation(p.getLocation());
					RespawnPoint resp = new RespawnPoint(args[0], respLoc, args[1]);
					FileManager.get().saveResp(resp);
					RespManager.get().getResps().put(resp.getRespRegion(), resp);
					p.sendMessage("§aPomyslnie stworzono punkt odrodzenia");
				} else {
					sendCorrectUsage(p);
				}
				break;
		}
		return true;
	}
	
	private void sendCorrectUsage(Player p) {
		p.sendMessage("§cPoprawne uzycie komendy to:");
		p.sendMessage("§c/odrodzenie ustaw <region> <perm>");
		p.sendMessage("§c/odrodzenie usun <nazwa>");
		p.sendMessage("§c/odrodzenie lista");
		p.sendMessage("§c/odrodzenie save");
	}

}
