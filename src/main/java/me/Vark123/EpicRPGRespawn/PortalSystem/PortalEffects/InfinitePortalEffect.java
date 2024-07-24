package me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import me.Vark123.EpicRPGRespawn.Main;

public class InfinitePortalEffect extends APortalEffect {

	private static final Random rand = new Random();
	private static Map<Player, BukkitTask> portalEffects = new ConcurrentHashMap<>();
	private static final Vector baseVector = new Vector(1,0,0);
	private final ItemStack it;
	private final double radius = 2;

	public InfinitePortalEffect() {
		super("infinite", "§3§lPORTAL NIESKONCZONOSCI");
		
		it = new ItemStack(Material.LAPIS_LAZULI);{
			ItemMeta im = it.getItemMeta();
			im.setDisplayName(getDisplay());
			it.setItemMeta(im);
		}
	}

	@Override
	public void playShotEffect(Location loc) {
		Vector dir = loc.getDirection().setY(0).normalize();
		Vector reverseDir = dir.clone().multiply(-1);
		loc.add(0, 1.25, 0).add(dir);

		double rotate = dir.getZ() > 0 ? baseVector.angle(dir) * -1 : baseVector.angle(dir);
		List<Location> frameLocations = new LinkedList<>();

		for(double angle = 0; angle < Math.PI * 2; angle += Math.PI*2/32.) {
			double y = radius * Math.sin(angle);
			double _x = radius * Math.cos(angle);
			
			double x = Math.sin(rotate) * _x;
			double z = Math.cos(rotate) * _x;
			frameLocations.add(loc.clone().add(x, y, z));
		}
		
		new BukkitRunnable() {
			int timer = 3*10;
			@Override
			public void run() {
				if(timer <= 0)
					cancel();
				if(isCancelled())
					return;

				for(Location tmp : frameLocations)
					tmp.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, tmp, 0,
							reverseDir.getX(), reverseDir.getY(), reverseDir.getZ(),
							rand.nextDouble(0.2) + 0.1);
				for(int i = 0; i < 10; ++i) {
					double speed = rand.nextDouble(0.2) + 0.1;
					double radius = rand.nextDouble(1.8) + 0.05;
					double angle = rand.nextDouble(Math.PI * 2);
					
					double y = radius * Math.sin(angle);
					double _x = radius * Math.cos(angle);
					
					double x = Math.sin(rotate) * _x;
					double z = Math.cos(rotate) * _x;
					
					Location tmp = loc.clone().add(x, y, z);
					tmp.getWorld().spawnParticle(Particle.SMOKE_LARGE, tmp, 0,
							reverseDir.getX(), reverseDir.getY(), reverseDir.getZ(),
							speed);
				}
				for(int i = 0; i < 25; ++i) {
					double speed = rand.nextDouble(0.2) + 0.1;
					double radius = rand.nextDouble(1.8) + 0.05;
					double angle = rand.nextDouble(Math.PI * 2);
					
					double y = radius * Math.sin(angle);
					double _x = radius * Math.cos(angle);
					
					double x = Math.sin(rotate) * _x;
					double z = Math.cos(rotate) * _x;
					
					Location tmp = loc.clone().add(x, y, z);
					tmp.getWorld().spawnParticle(Particle.SMOKE_NORMAL, tmp, 0,
							reverseDir.getX(), reverseDir.getY(), reverseDir.getZ(),
							speed);
				}
				
				--timer;
			}
		}.runTaskTimer(Main.inst(), 0, 2);
	}

	@Override
	public void startEffect(Player caster) {
		if(portalEffects.containsKey(caster))
			portalEffects.get(caster).cancel();
		
		Location loc = caster.getLocation().clone().add(0, 1.25, 0);
		caster.playSound(loc, Sound.BLOCK_PORTAL_TRIGGER, 1, 0.8f);
		
		Vector dir = loc.getDirection().setY(0).normalize();
		Vector reversedDir = dir.clone().multiply(-1);
		loc.add(dir);

		double rotate = dir.getZ() > 0 ? baseVector.angle(dir) * -1 : baseVector.angle(dir);
		
		List<Location> frameLocations = new LinkedList<>();
		
		for(double angle = 0; angle < Math.PI * 2; angle += Math.PI*2/32.) {
			double y = radius * Math.sin(angle);
			double _x = radius * Math.cos(angle);
			
			double x = Math.sin(rotate) * _x;
			double z = Math.cos(rotate) * _x;
			frameLocations.add(loc.clone().add(x, y, z));
		}
		
		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				if(!caster.isOnline())
					cancel();
				if(isCancelled())
					return;

				for(Location tmp : frameLocations)
					tmp.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, tmp, 0,
							reversedDir.getX(), reversedDir.getY(), reversedDir.getZ(),
							rand.nextDouble(0.2) + 0.1);
				for(int i = 0; i < 10; ++i) {
					double speed = rand.nextDouble(0.2) + 0.1;
					double radius = rand.nextDouble(1.8) + 0.05;
					double angle = rand.nextDouble(Math.PI * 2);
					
					double y = radius * Math.sin(angle);
					double _x = radius * Math.cos(angle);
					
					double x = Math.sin(rotate) * _x;
					double z = Math.cos(rotate) * _x;
					
					Location tmp = loc.clone().add(x, y, z);
					tmp.getWorld().spawnParticle(Particle.SMOKE_LARGE, tmp, 0,
							reversedDir.getX(), reversedDir.getY(), reversedDir.getZ(),
							speed);
				}
				for(int i = 0; i < 25; ++i) {
					double speed = rand.nextDouble(0.2) + 0.1;
					double radius = rand.nextDouble(1.8) + 0.05;
					double angle = rand.nextDouble(Math.PI * 2);
					
					double y = radius * Math.sin(angle);
					double _x = radius * Math.cos(angle);
					
					double x = Math.sin(rotate) * _x;
					double z = Math.cos(rotate) * _x;
					
					Location tmp = loc.clone().add(x, y, z);
					tmp.getWorld().spawnParticle(Particle.SMOKE_NORMAL, tmp, 0,
							reversedDir.getX(), reversedDir.getY(), reversedDir.getZ(),
							speed);
				}
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
		
		new BukkitRunnable() {
			double r = 0;
			double step = 0.05;
			double step2 = 0.05;
			Location originLoc = caster.getLocation().add(0, 1.25, 0);
			Vector originalDir = originLoc.getDirection().setY(0).normalize();
			double rotate = originalDir.getZ() > 0 ? baseVector.angle(originalDir) * -1 : baseVector.angle(originalDir);
			double offset = radius;
			@Override
			public void run() {
				if(r < 0)
					cancel();
				if(isCancelled())
					return;

				Vector dir = originalDir.clone().multiply(offset);
				Location loc = originLoc.clone().add(dir);
				
				double localRadius = Math.sqrt(radius * radius - offset * offset);
						
				for(double angle = 0; angle < Math.PI * 2; angle += Math.PI*2/32.) {
					double y = localRadius * Math.sin(angle);
					double _x = localRadius * Math.cos(angle);
					
					double x = Math.sin(rotate) * _x;
					double z = Math.cos(rotate) * _x;
					Location tmp = loc.clone().add(x, y, z);
					tmp.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, tmp, 1,
							0, 0, 0, 0);
				}
				for(int i = 0; i < 10; ++i) {
					if(localRadius <= 0)
						continue;
					double radius = rand.nextDouble(localRadius * 0.9) + 0.05;
					if(radius > localRadius)
						continue;
					
					double angle = rand.nextDouble(Math.PI * 2);
					
					double y = radius * Math.sin(angle);
					double _x = radius * Math.cos(angle);
					
					double x = Math.sin(rotate) * _x;
					double z = Math.cos(rotate) * _x;
					
					Location tmp = loc.clone().add(x, y, z);
					tmp.getWorld().spawnParticle(Particle.SMOKE_LARGE, tmp, 1,
							0, 0, 0, 0);
				}
				for(int i = 0; i < 25; ++i) {
					if(localRadius <= 0)
						continue;
					double radius = rand.nextDouble(localRadius * 0.9) + 0.05;
					if(radius > localRadius)
						continue;
					
					double angle = rand.nextDouble(Math.PI * 2);
					
					double y = radius * Math.sin(angle);
					double _x = radius * Math.cos(angle);
					
					double x = Math.sin(rotate) * _x;
					double z = Math.cos(rotate) * _x;
					
					Location tmp = loc.clone().add(x, y, z);
					tmp.getWorld().spawnParticle(Particle.SMOKE_NORMAL, tmp, 1,
							0, 0, 0, 0);
				}
				
				r += step;
				offset -= step2;
				if(r >= radius)
					step *= -1;
			}
		}.runTaskTimer(Main.inst(), 0, 1);
	}

	@Override
	public ItemStack getItem() {
		return it;
	}

}
