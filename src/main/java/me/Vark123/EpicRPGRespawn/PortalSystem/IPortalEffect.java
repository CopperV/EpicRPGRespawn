package me.Vark123.EpicRPGRespawn.PortalSystem;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IPortalEffect {

	public void playShotEffect(Location loc);
	public void startEffect(Player caster);
	public void stopEffect(Player caster);
	
}
