package me.Vark123.EpicRPGRespawn.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class RpgLocation {
	
	private Location loc;
	
	private String world;
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;
	
	public RpgLocation(String world, double x, double y, double z, float pitch, float yaw) {
		this(world, x, y, z);
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public RpgLocation(String world, double x, double y, double z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public RpgLocation(Location loc) {
		super();
		this.loc = loc;
	}
	
	public Location bukkitLocation() {
		if(loc == null) {
			loc = new Location(
					Bukkit.getWorld(world), 
					x, y, z, 
					yaw, pitch);
		}
		return loc;
	}

}
