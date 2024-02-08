package me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPG.Main;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayer;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalManager;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects.APortalEffect;

public class PortalEffectCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("portaleffect"))
			return false;
		if(!sender.hasPermission("epictp.cmd"))
			return false;
		if(args.length < 3) {
			sendCorrectUsage(sender);
			return false;
		}
		
		Player p = Bukkit.getPlayerExact(args[1]);
		if(p == null) {
			sender.sendMessage("§cGracz §a§o"+args[1]+" §cjest offline");
			return false;
		}
		RespPlayer rp = RespPlayerManager.get().getRespPlayer(p).get();
		
		APortalEffect effect = PortalManager.get().getPortalEffects().get(args[2]);
		if(effect == null) {
			sender.sendMessage("§cEfekt §a§o"+args[2]+" §cnie istnieje");
			return false;
		}
		
		switch(args[0].toLowerCase()) {
			case "add":
				rp.getUnlockedEffects().stream()
					.filter(_effect -> _effect.equals(effect))
					.findAny()
					.ifPresentOrElse(_effect -> {
						sender.sendMessage("§7"+p.getName()+" §aposiada juz efekt §r§7["+effect.getDisplay()+"§7]");
					}, () -> {
						rp.getUnlockedEffects().add(effect);
						sender.sendMessage("§7"+p.getName()+" §aotrzymal efekt §r§7["+effect.getDisplay()+"§7]");
						p.sendMessage(Main.getInstance().getPrefix()+" §aOtrzymales efekt portali §7["+effect.getDisplay()+"§7]");
					});
				break;
			case "remove":
				rp.getUnlockedEffects().stream()
					.filter(_effect -> _effect.equals(effect))
					.findAny()
					.ifPresentOrElse(_effect -> {
						rp.getUnlockedEffects().remove(effect);
						sender.sendMessage("Efekt §r§7["+effect.getDisplay()+"§7] §azostal usuniety §7"+p.getName());
						p.sendMessage(Main.getInstance().getPrefix()+" §aOdebrano Tobie efekt portali §7["+effect.getDisplay()+"§7]");
					}, () -> {
						sender.sendMessage("§7"+p.getName()+" §anie posiada efektu §r§7["+effect.getDisplay()+"§7]");
					});
				break;
			default:
				return false;
		}
		
		return true;
	}
	
	private void sendCorrectUsage(CommandSender sender) {
		sender.sendMessage("/pe add <player> <effect-id>");
		sender.sendMessage("/pe remove <player> <effect-id>");
	}


}
