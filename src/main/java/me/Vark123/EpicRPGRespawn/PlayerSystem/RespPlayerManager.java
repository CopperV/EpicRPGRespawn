package me.Vark123.EpicRPGRespawn.PlayerSystem;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Vark123.EpicRPG.Main;
import me.Vark123.EpicRPGRespawn.FileSystem.FileManager;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects.APortalEffect;
import me.Vark123.EpicRPGRespawn.RespSystem.RespManager;

public final class RespPlayerManager {

	private static final RespPlayerManager inst = new RespPlayerManager();
	
	private final Map<Player, RespPlayer> players;
	private final Map<Player, BukkitTask> respController;
	
	public final int PLAYER_COOLDOWN = 20*3;
	public final int ADMIN_COOLDOWN = 5;
	
	private RespPlayerManager() {
		players = new ConcurrentHashMap<>();
		respController = new ConcurrentHashMap<>();
	}
	
	public static final RespPlayerManager get() {
		return inst;
	}
	
	public void addPlayer(RespPlayer respPlayer) {
		players.put(respPlayer.getPlayer(), respPlayer);
	}
	
	public void removePlayer(RespPlayer respPlayer) {
		removePlayer(respPlayer.getPlayer());
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public boolean respPlayerExists(Player player) {
		return players.containsKey(player);
	}
	
	public Optional<RespPlayer> getRespPlayer(Player player){
		if(!players.containsKey(player))
			return Optional.empty();
		return Optional.of(players.get(player));
	}
	
	public void clearContainer() {
		players.values().forEach(respPlayer -> {
			FileManager.get().savePlayer(respPlayer);
		});
		respController.values().forEach(task -> {
			task.cancel();
		});
		players.clear();
		respController.clear();
	}
	
	public boolean playerWaitToTeleport(Player p) {
		return respController.containsKey(p);
	}
	
	public Optional<BukkitTask> getRespTask(Player p) {
		if(!respController.containsKey(p))
			return Optional.empty();
		return Optional.of(respController.get(p));
	}
	
	public void cancelResp(Player p) {
		if(!respController.containsKey(p))
			return;
		respController.get(p).cancel();
		respController.remove(p);

		RespPlayer respPlayer = players.get(p);
		Optional<APortalEffect> effect = Optional.ofNullable(respPlayer.getCurrentEffect());
		effect.ifPresent(_effect -> _effect.stopEffect(p));
	}
	
	public void respPlayerCooldown(Player p) {
		if(respController.containsKey(p)) {
			p.sendMessage(Main.getInstance().getPrefix()+" §aJuz czekasz na teleportacje. Poczekaj chwile!");
			return;
		}
		if(!players.containsKey(p))
			return;
		
		RespPlayer respPlayer = players.get(p);
		String resp = respPlayer.getRespLoc();
		if(!RespManager.get().getResps().containsKey(resp))
			return;
		Location loc = RespManager.get()
				.getResps().get(resp)
				.getRespLoc().bukkitLocation();
		
		int cd = p.hasPermission("respawn.admin") ?
				ADMIN_COOLDOWN : PLAYER_COOLDOWN;
		int seconds = cd/20;
		
		Optional<APortalEffect> effect = Optional.ofNullable(respPlayer.getCurrentEffect());
		effect.ifPresent(_effect -> _effect.startEffect(p));
		
		p.sendMessage(Main.getInstance().getPrefix()+" §aPoczekaj "+seconds+" sekundy na teleportacje");
		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				effect.ifPresent(_effect -> _effect.stopEffect(p));
				if(this.isCancelled())
					return;
				p.teleport(loc);
				respController.remove(p);
				p.sendMessage("§aPrzeteleportowales sie do punktu odrodzenia");
				effect.ifPresent(_effect -> _effect.playShotEffect(p.getLocation()));
			}
		}.runTaskLater(me.Vark123.EpicRPGRespawn.Main.inst(), cd);
		respController.put(p, task);
	}
	
}
