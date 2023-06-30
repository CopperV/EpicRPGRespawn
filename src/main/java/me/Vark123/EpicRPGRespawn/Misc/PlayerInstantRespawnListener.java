package me.Vark123.EpicRPGRespawn.Misc;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.Vark123.EpicRPGRespawn.Main;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayer;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespManager;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand;

public class PlayerInstantRespawnListener implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if(p == null)
			return;
		new BukkitRunnable() {
			@Override
			public void run() {
				if(!p.isDead())
					return;
				((CraftPlayer)p).getHandle().b.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.a));
			}
		}.runTask(Main.inst());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		Optional<RespPlayer> oRespPlayer = RespPlayerManager.get().getRespPlayer(p);
		if(oRespPlayer.isEmpty())
			return;
		String respRegion = oRespPlayer.get().getRespLoc();
		if(!RespManager.get().getResps().containsKey(respRegion))
			return;
		Location loc = RespManager.get()
				.getResps().get(respRegion)
				.getRespLoc().bukkitLocation();
		e.setRespawnLocation(loc);
	}
	
}
