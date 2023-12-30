package me.Vark123.EpicRPGRespawn.PortalSystem.Portals;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.Getter;
import me.Vark123.EpicRPGRespawn.PortalSystem.APortal;
import me.Vark123.EpicRPGRespawn.PortalSystem.IPortalEffect;
import me.Vark123.EpicRPGRespawn.Utils.RpgLocation;

@Getter
public class PermPortal extends APortal {

	private String perm;
	
	public PermPortal(String portalRegion, RpgLocation destiny, String perm) {
		super(portalRegion, destiny);
		this.perm = perm;
	}

	@Override
	public boolean canUsePortal(Player p) {
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
	public void teleport(Player p, IPortalEffect effect) {
		Location loc = getDestiny().bukkitLocation();
		if(loc == null 
				|| loc.getWorld() == null)
			return;
		effect.playShotEffect(p.getLocation().clone());
		p.teleport(loc);
		effect.playShotEffect(p.getLocation().clone());
	}

}
