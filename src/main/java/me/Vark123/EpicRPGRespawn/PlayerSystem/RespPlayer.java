package me.Vark123.EpicRPGRespawn.PlayerSystem;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class RespPlayer {
	
	private Player player;
	@Setter
	private String respLoc;
	
}
