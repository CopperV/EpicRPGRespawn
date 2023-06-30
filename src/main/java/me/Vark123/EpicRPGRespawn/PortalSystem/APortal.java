package me.Vark123.EpicRPGRespawn.PortalSystem;

import org.bukkit.entity.Player;

import lombok.Getter;
import me.Vark123.EpicRPGRespawn.Utils.RpgLocation;

@Getter
public abstract class APortal {

	private String portalRegion;
	private RpgLocation destiny;
	
	public APortal(String portalRegion, RpgLocation destiny) {
		this.portalRegion = portalRegion;
		this.destiny = destiny;
	}
	
	public abstract boolean canUsePortal(Player p);
	public abstract void teleport(Player p);
	public abstract void teleport(Player p, ITeleportEffect effect);
	
}
