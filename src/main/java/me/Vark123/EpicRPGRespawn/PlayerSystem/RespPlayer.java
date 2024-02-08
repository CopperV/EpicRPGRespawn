package me.Vark123.EpicRPGRespawn.PlayerSystem;

import java.util.Date;
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
	private String respLoc;
	@Setter
	private long lastModified = new Date().getTime();
	
	@Setter
	private APortalEffect currentEffect;
	private Set<APortalEffect> unlockedEffects;
	
	public boolean canModifyResp() {
		long present = new Date().getTime();
		return (present - lastModified) > (2 * 1000);
	}
	
	public void setRespLoc(String respLoc) {
		this.lastModified = new Date().getTime();
		this.respLoc = respLoc;
	}
	
}
