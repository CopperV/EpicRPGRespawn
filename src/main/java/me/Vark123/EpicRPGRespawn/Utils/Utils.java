package me.Vark123.EpicRPGRespawn.Utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public final class Utils {

	private Utils() {}
	
	public static void drawPentagram(Particle particle, Location center, Vector axis, int points, double radius, double lineOffset, int step, double rotation) {
		double angle = Math.PI*2 / (double) points;
		Vector normalizedAxis = axis.clone().normalize();

	    Location[] vertices = new Location[points];
    	Vector base = getPerpendicularVectorUsingCrossProduct(axis).normalize();
	    
		for(int i = 0; i < points; ++i) {
			double _angle = i*angle + rotation;
			Vector v = base.clone().rotateAroundAxis(normalizedAxis.clone(), _angle)
					.normalize()
					.multiply(radius);
			vertices[i] = center.clone().add(v);
		}
		
		for(int i = 0; i < vertices.length; ++i) {
			Location loc1 = vertices[i];
			Location loc2 = vertices[(i+step)%vertices.length];
			drawLine(particle, loc1, loc2, lineOffset, 0, 0, -1f, 0, 0.05f);
		}
	}
	
	public static void drawLine(Particle particle, Location start, Location end, double step, 
			int amount, float offsetX, float offsetY, float offsetZ, float speed) {
		Vector dir = new Vector(
				end.getX() - start.getX(),
				end.getY() - start.getY(),
				end.getZ() - start.getZ()
		).normalize().multiply(step);
		Location loc = start.clone();
		while(start.distanceSquared(loc) <= start.distanceSquared(end)) {
			loc.getWorld().spawnParticle(particle, loc, amount, offsetX, offsetY, offsetZ, speed);
			loc.add(dir);
		}
	}
	
	public static Vector getPerpendicularVectorUsingCrossProduct(Vector mainAxis) {
	    Vector arbitraryVector = new Vector(1, 0, 0);

	    if (mainAxis.getX() == 1 && mainAxis.getY() == 0 && mainAxis.getZ() == 0) {
	        arbitraryVector = new Vector(0, 1, 0);
	    }

	    return mainAxis.clone().crossProduct(arbitraryVector).normalize();
	}
	
}
