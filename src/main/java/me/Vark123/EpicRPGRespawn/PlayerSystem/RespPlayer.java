package me.Vark123.EpicRPGRespawn.PlayerSystem;

import java.util.Set;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects.APortalEffect;

@Getter
@AllArgsConstructor
public class RespPlayer {
	
	private Player player;
	@Setter
	private String respLoc;
	
	@Setter
	private APortalEffect currentEffect;
	private Set<APortalEffect> unlockedEffects;
	
}
