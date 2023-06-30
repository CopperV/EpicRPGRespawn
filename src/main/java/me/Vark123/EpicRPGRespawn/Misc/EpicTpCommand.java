package me.Vark123.EpicRPGRespawn.Misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Vark123.EpicRPGRespawn.Main;

public class EpicTpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("epictp"))
			return false;
		if(args.length < 7) {
			sendCorrectUsage(sender);
			return false;
		}
		
		if(Bukkit.getPlayerExact(args[0])==null) {
			sender.sendMessage("§cGracz §a§o"+args[0]+" §cjest offline");
			return false;
		}
		Player p = Bukkit.getPlayer(args[0]);
		
		double x, y, z;
		float yaw, pitch;
		try {
			x = Double.parseDouble(args[1]);
			y = Double.parseDouble(args[2]);
			z = Double.parseDouble(args[3]);
			yaw = Float.parseFloat(args[4]);
			pitch = Float.parseFloat(args[5]);
		}catch(NumberFormatException e) {
			sender.sendMessage("§cBlad liczbowy: §e"+e.getMessage());
			return false;
		}
		
		World w = Bukkit.getWorld(args[6]);
		Location loc = new Location(w, x, y, z, yaw, pitch);
		new BukkitRunnable() {
			@Override
			public void run() {
				p.teleport(loc);
			}
		}.runTask(Main.inst());
		return true;
	}
	
	private void sendCorrectUsage(CommandSender sender) {
		sender.sendMessage("/epictp <nick> <x> <y> <z> <yaw> <pitch> <world>");
	}

}
