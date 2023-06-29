package me.Vark123.EpicRPGRespawn.PortalSystem.Portals;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPGRespawn.PortalSystem.APortal;
import me.Vark123.EpicRPGRespawn.PortalSystem.ITeleportEffect;
import me.Vark123.EpicRPGRespawn.Utils.RpgLocation;

public class StandardPortal extends APortal {

	public StandardPortal(String portalRegion, RpgLocation destiny) {
		super(portalRegion, destiny);
	}

	@Override
	public boolean canUsePortal(Player p) {
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
