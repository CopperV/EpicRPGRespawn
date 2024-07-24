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

public class EasterPortalEffect extends APortalEffect {

	private static final Random rand = new Random();
	private static Map<Player, BukkitTask> portalEffects = new ConcurrentHashMap<>();
	private final ItemStack it;
	
	private final Vector leftVector = new Vector(1,-.4,-1).normalize();
	private final Vector downVector = new Vector(1,-.4,1).normalize();
	private final Vector rightVector = new Vector(-1,-.4,1).normalize();
	private final Vector upVector = new Vector(-1,-.4,-1).normalize();
	
	public EasterPortalEffect() {
		super("easter", "§2§lWIOSNA LUDOW");
		
		it = new ItemStack(Material.OAK_LEAVES);{
			ItemMeta im = it.getItemMeta();
			im.setDisplayName(getDisplay());
			it.setItemMeta(im);
		}
	}

	@Override
	public void playShotEffect(Location loc) {
		loc.add(0, 2, 0);
		
		List<Location> leftLocations = new LinkedList<>();
		List<Location> upLocations = new LinkedList<>();
		List<Location> rightLocations = new LinkedList<>();
		List<Location> downLocations = new LinkedList<>();
		
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(-2.5, i, 0);
			leftLocations.add(tmp);
		}
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(0, i, -2.5);
			downLocations.add(tmp);
		}
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(2.5, i, 0);
			rightLocations.add(tmp);
		}
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(0, i, 2.5);
			upLocations.add(tmp);
		}
		
		new BukkitRunnable() {
			int timer = 3*10;
			@Override
			public void run() {
				if(timer <= 0)
					cancel();
				if(isCancelled())
					return;
				
				leftLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							leftVector.getX(), leftVector.getY(), leftVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				downLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							downVector.getX(), downVector.getY(), downVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				rightLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							rightVector.getX(), rightVector.getY(), rightVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				upLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							upVector.getX(), upVector.getY(), upVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				
				--timer;
			}
		}.runTaskTimer(Main.inst(), 0, 2);
	}

	@Override
	public void startEffect(Player caster) {
		if(portalEffects.containsKey(caster))
			portalEffects.get(caster).cancel();

		Location loc = caster.getLocation().clone().add(0, 2, 0);
		caster.playSound(loc, Sound.BLOCK_PORTAL_TRAVEL, 1, 1.4f);
		
		List<Location> leftLocations = new LinkedList<>();
		List<Location> upLocations = new LinkedList<>();
		List<Location> rightLocations = new LinkedList<>();
		List<Location> downLocations = new LinkedList<>();
		
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(-2.5, i, 0);
			leftLocations.add(tmp);
		}
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(0, i, -2.5);
			downLocations.add(tmp);
		}
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(2.5, i, 0);
			rightLocations.add(tmp);
		}
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(0, i, 2.5);
			upLocations.add(tmp);
		}
		
		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				if(!caster.isOnline())
					cancel();
				if(isCancelled())
					return;
				
				leftLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							leftVector.getX(), leftVector.getY(), leftVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				downLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							downVector.getX(), downVector.getY(), downVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				rightLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							rightVector.getX(), rightVector.getY(), rightVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				upLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							upVector.getX(), upVector.getY(), upVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
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

		Location loc = caster.getLocation().clone().add(0, 2, 0);
		
		List<Location> leftLocations = new LinkedList<>();
		List<Location> upLocations = new LinkedList<>();
		List<Location> rightLocations = new LinkedList<>();
		List<Location> downLocations = new LinkedList<>();
		
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(-2.5, i, 0);
			leftLocations.add(tmp);
		}
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(0, i, -2.5);
			downLocations.add(tmp);
		}
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(2.5, i, 0);
			rightLocations.add(tmp);
		}
		for(double i = -2; i <= 2; i += 0.1) {
			Location tmp = loc.clone().add(0, i, 2.5);
			upLocations.add(tmp);
		}
		
		new BukkitRunnable() {
			int timer = 3*10;
			@Override
			public void run() {
				if(timer <= 0)
					cancel();
				if(isCancelled())
					return;
				
				leftLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							leftVector.getX(), leftVector.getY(), leftVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				downLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							downVector.getX(), downVector.getY(), downVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				rightLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							rightVector.getX(), rightVector.getY(), rightVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				upLocations.forEach(tmp -> {
					tmp.getWorld().spawnParticle(Particle.TOTEM, tmp, 0,
							upVector.getX(), upVector.getY(), upVector.getZ(),
							0.1 + rand.nextDouble(1.35));
				});
				
				--timer;
			}
		}.runTaskTimer(Main.inst(), 0, 2);
	}

	@Override
	public ItemStack getItem() {
		return it;
	}
	
}
