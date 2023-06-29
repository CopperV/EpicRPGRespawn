package me.Vark123.EpicRPGRespawn.PortalSystem.Portals;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.Vark123.EpicRPGRespawn.PortalSystem.APortal;
import me.Vark123.EpicRPGRespawn.PortalSystem.ITeleportEffect;
import me.Vark123.EpicRPGRespawn.Utils.RpgLocation;

@Getter
public class PremiumPortal extends APortal {

	private String perm;
	
	public PremiumPortal(String portalRegion, RpgLocation destiny, String perm) {
		super(portalRegion, destiny);
		this.perm = perm;
	}

	@Override
	public boolean canUsePortal(Player p) {
		if(!p.hasPermission("portal.portals")) {
			p.sendMessage("§cNie masz uprawnien do korzystania z portali!");
			return false;
		}
		if(perm != null && !p.hasPermission(perm)) {
			p.sendMessage("§cNie odblokowales jeszcze tego portalu!");
			return false;
		}
		return true;
	}

	@Override
	public void teleport(Player p) {
		Location loc = getDestiny().bukkitLocation();
		if(loc == null 
				|| loc.getWorld() == null)
			return;
		p.teleport(loc);
	}

	@Override
	public void teleport(Player p, ITeleportEffect effect) {
		Location loc = getDestiny().bukkitLocation();
		if(loc == null 
				|| loc.getWorld() == null)
			return;
		effect.playEffect(p.getLocation().clone());
		p.teleport(loc);
		effect.playEffect(p.getLocation().clone());
	}

}
