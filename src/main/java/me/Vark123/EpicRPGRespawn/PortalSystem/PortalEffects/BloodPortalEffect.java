package me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import me.Vark123.EpicRPGRespawn.Main;

public class BloodPortalEffect extends APortalEffect {

	private static final Random rand = new Random();
	private static Map<Player, BukkitTask> portalEffects = new ConcurrentHashMap<>();
	private static DustOptions dust = new DustOptions(Color.RED, 0.8f);
	private final ItemStack it;
	
	
	public BloodPortalEffect() {
		super("blood", "§c§lKRWAWY PORTAL");
		
		it = new ItemStack(Material.REDSTONE);{
			ItemMeta im = it.getItemMeta();
			im.setDisplayName(getDisplay());
			it.setItemMeta(im);
		}
	}

	@Override
	public void playShotEffect(Location loc) {
		loc.add(0, 1.25, 0);

		double rotate = Math.toRadians(loc.getYaw()+90);
		double r1 = 1.6;
		double r2 = 0.7;
		
		List<Location> smokeLocations = new LinkedList<>();
		List<Location> redstoneLocations = new LinkedList<>();
		
		for(double angle = 0; angle < Math.PI * 2; angle += Math.PI*2/32.) {
			for(double i = 0; i < 0.75; i += 0.15) {
				double y = r1*i * Math.sin(angle);
				double _x = r2*i * Math.cos(angle);
				
				double x = Math.sin(rotate) * _x;
				double z = Math.cos(rotate) * _x;
				redstoneLocations.add(loc.clone().add(x, y, z));
			}
			for(double i = 0.75; i < 1; i += 0.15) {
				double y = r1*i * Math.sin(angle);
				double _x = r2*i * Math.cos(angle);
				
				double x = Math.sin(rotate) * _x;
				double z = Math.cos(rotate) * _x;
				smokeLocations.add(loc.clone().add(x, y, z));
			}
		}
		
		
		new BukkitRunnable() {
			int timer = 3*10;
			@Override
			public void run() {
				if(timer <= 0)
					cancel();
				if(isCancelled())
					return;
				
				for(Location tmp : redstoneLocations)
					tmp.getWorld().spawnParticle(Particle.REDSTONE, tmp, 1, 0, 0, 0, 0, dust);
				for(Location tmp : smokeLocations)
					tmp.getWorld().spawnParticle(Particle.SMOKE_NORMAL, tmp, 0, 0, 0.1, 0, 0.15);
				
				--timer;
			}
		}.runTaskTimer(Main.inst(), 0, 2);
	}

	@Override
	public void startEffect(Player caster) {
		if(portalEffects.containsKey(caster))
				portalEffects.get(caster).cancel();
		
		Location loc = caster.getLocation().add(0, 0.05, 0);
		caster.playSound(loc, Sound.ENTITY_WITHER_AMBIENT, 1, 0.8f);
		BukkitTask task = new BukkitRunnable() {
			double angle = 0;
			
			double radius1 = 1;
			int points1 = 8;
			
			double update = (Math.PI * 2) / 128;
			double radius2 = 2;
			int points2 = 5;
			@Override
			public void run() {
				if(!caster.isOnline())
					cancel();
				if(isCancelled())
					return;
				
				for(double theta = 0; theta < 2*Math.PI; theta += Math.PI * 2 / (double)points1) {
					for(double tempRadius = 0; tempRadius <= radius1; tempRadius += 0.5) {
						double x = Math.sin(theta) * tempRadius;
						double z = Math.cos(theta) * tempRadius;
						
						Location tmp = loc.clone().add(x,0,z);
						tmp.getWorld().spawnParticle(Particle.SMOKE_NORMAL, tmp, 0, 0, -.5, 0, 0.1);
					}
				}
				
				for(double theta = 0; theta < 2*Math.PI; theta += Math.PI * 2 / (double)points2) {
					double tmpAngle = theta + angle;
					for(double tempRadius = radius1 - 0.25; tempRadius <= radius2; tempRadius += 0.25) {
						double percent = tempRadius/radius2 * Math.PI;
						
						double x = Math.sin(tmpAngle-percent) * tempRadius;
						double z = Math.cos(tmpAngle-percent) * tempRadius;
						Location tmp = loc.clone().add(x,0,z);
						
						loc.getWorld().spawnParticle(Particle.REDSTONE, tmp, 1, 0, 0, 0, 0, dust);
					}
				}
				
				for(double theta = 0; theta < 2*Math.PI; theta += Math.PI * 2 / 16.) {
					double x = Math.sin(theta) * radius2;
					double z = Math.cos(theta) * radius2;
					
					Location tmp = loc.clone().add(x,0,z);
					tmp.getWorld().spawnParticle(Particle.SMOKE_LARGE, tmp, 0, 0, .1, 0, 0.15);
				}
				
				angle += update;
			}
		}.runTaskTimer(Main.inst(), 0, 2);
		
		portalEffects.put(caster, task);
	}

	@Override
	public void stopEffect(Player caster) {
		if(!portalEffects.containsKey(caster))
			return;

		portalEffects.get(caster).cancel();
		portalEffects.remove(caster);
		
		Location loc = caster.getLocation().add(0, 0.05, 0);
		caster.playSound(loc, Sound.ENTITY_WITHER_DEATH, 1, 0.8f);
		for(int i = 0; i < 20; ++i) {
			double radius = rand.nextDouble(0.8) + 0.4;
			double angle = rand.nextDouble(Math.PI * 2);
			
			double x = Math.sin(angle) * radius;
			double y = rand.nextDouble(4) - 1;
			double z = Math.cos(angle) * radius;
			
			Location target = loc.clone().add(x, y, z);
			Vector dir = new Vector(target.getX() - loc.getX(),
					target.getY() - loc.getY(),
					target.getZ() - loc.getZ()).normalize();
			
			loc.getWorld().spawnParticle(Particle.PORTAL, loc, 0, dir.getX(), dir.getY(), dir.getZ(), radius/5.f);
		}
	}

	@Override
	public ItemStack getItem() {
		return it;
	}

}
