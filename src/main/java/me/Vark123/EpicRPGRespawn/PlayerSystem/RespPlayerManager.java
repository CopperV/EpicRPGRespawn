package me.Vark123.EpicRPGRespawn.PlayerSystem;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

public final class RespPlayerManager {

	private static final RespPlayerManager inst = new RespPlayerManager();
	
	private final Map<Player, RespPlayer> players;
	
	private RespPlayerManager() {
		players = new ConcurrentHashMap<>();
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
	
}
