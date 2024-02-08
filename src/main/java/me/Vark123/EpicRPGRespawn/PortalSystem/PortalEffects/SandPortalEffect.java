package me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import lombok.Getter;
import lombok.Setter;
import me.Vark123.EpicRPGRespawn.Main;

public class SandPortalEffect extends APortalEffect {

	private static final Random rand = new Random();
	private static Map<Player, BukkitTask> portalEffects = new ConcurrentHashMap<>();
	private final ItemStack it;
	private final BlockData sandData;
	
	public SandPortalEffect() {
		super("sand", "§6§lPORTAL CZASU");
		
		sandData = Bukkit.createBlockData(Material.SAND);
		it = new ItemStack(Material.CLOCK);{
			ItemMeta im = it.getItemMeta();
			im.setDisplayName(getDisplay());
			it.setItemMeta(im);
		}
	}
	
	@Override
	public void playShotEffect(Location loc) {
		final double r = 5;
		final double heightModifier = 18;
		final double step = 0.25;
		List<Location> sandLocations = new LinkedList<>();
		for(double _r = step; _r <= r; _r += step) {
			double y = _r / heightModifier;
			for(double angle = 0; angle < Math.PI * 2; angle += Math.PI*2/(8.*_r)) {
				double x = Math.sin(angle) * _r;
				double z = Math.cos(angle) * _r;
				
				sandLocations.add(loc.clone().add(x, y, z));
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
				
				sandLocations.forEach(tmp -> {
					Vector dir = new Vector(loc.getX() - tmp.getX(),
							loc.getY() - tmp.getY(),
							loc.getZ() - tmp.getZ()).normalize();
					tmp.getWorld().spawnParticle(Particle.WAX_ON, tmp,
							0, dir.getX(), dir.getY(), dir.getZ(),
							tmp.distanceSquared(loc)*3);
				});
				--timer;
			}
		}.runTaskTimer(Main.inst(), 0, 2);
	}

	@Override
	public void startEffect(Player caster) {
		if(portalEffects.containsKey(caster))
			portalEffects.get(caster).cancel();
		
		Location loc = caster.getLocation();
		caster.playSound(loc, Sound.ENTITY_WITHER_AMBIENT, 1, 0.8f);
		
		final double r = 5;
		final double heightModifier = 18;
		final double step = 0.25;
		List<Location> sandLocations = new LinkedList<>();
		for(double _r = step; _r <= r; _r += step) {
			double y = _r / heightModifier;
			for(double angle = 0; angle < Math.PI * 2; angle += Math.PI*2/(8.*_r)) {
				double x = Math.sin(angle) * _r;
				double z = Math.cos(angle) * _r;
				
				sandLocations.add(loc.clone().add(x, y, z));
			}
		}
		
		Vector vec1 = new Vector(rand.nextDouble()+1, rand.nextDouble(), rand.nextDouble())
				.normalize();
		Vector vec2 = new Vector(rand.nextDouble(), rand.nextDouble()+1, rand.nextDouble())
				.normalize();
		Vector vec3 = new Vector(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()+1)
				.normalize();
		
		List<Circle> circles = new LinkedList<>();
		circles.add(new Circle(2.5, vec1, vec1.clone().setX(-vec1.getX()), 2*Math.PI/140.));
		circles.add(new Circle(4, vec2, vec2.clone().setY(-vec2.getY()), 2*Math.PI/165.));
		circles.add(new Circle(5.5, vec3, vec3.clone().setZ(-vec3.getZ()), 2*Math.PI/180.));
		
		BukkitTask task = new BukkitRunnable() {
			final double speedModifier = 1.02;
			@Override
			public void run() {
				if(!caster.isOnline())
					cancel();
				if(isCancelled())
					return;
				
				circles.forEach(circle -> {
					double radius = circle.getRadius();
					for(double theta = 0; theta < 2*Math.PI; theta += 2*Math.PI / 64.) {
						Vector v1 = circle.getVec1().clone().multiply(radius * Math.sin(theta));
						Vector v2 = circle.getVec2().clone().multiply(radius * Math.cos(theta));
						
						v1.add(v2);
						Location tmp = loc.clone().add(v1);
						tmp.getWorld().spawnParticle(Particle.BLOCK_CRACK, tmp, 1,
								0,0,0,0, sandData);
						tmp.getWorld().spawnParticle(Particle.CRIT, tmp, 1,
								0,0,0,0);
					}
					circle.update(speedModifier);
				});
				
				
				sandLocations.forEach(tmp -> {
					Vector dir = new Vector(loc.getX() - tmp.getX(),
							loc.getY() - tmp.getY(),
							loc.getZ() - tmp.getZ()).normalize();
					tmp.getWorld().spawnParticle(Particle.WAX_ON, tmp,
							0, dir.getX(), dir.getY(), dir.getZ(),
							tmp.distanceSquared(loc) * 3);
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
	
	@Getter
	private class Circle {
		
		private double radius;
		@Setter
		private double speed;
		
		private Vector vec1;
		private Vector vec2;
		
		public Circle(double radius, Vector axis1, Vector axis2, double speed) {
			this.radius = radius;
			this.speed = speed;
			this.vec1 = axis1;
			Vector tmp = axis1.getCrossProduct(axis2);
			this.vec2 = axis1.clone().rotateAroundAxis(tmp, Math.PI/2.).normalize();
		}
		
		public void update(double modifier) {
			vec1.rotateAroundAxis(vec2, speed);
			speed *= modifier;
		}
		
	}

}
