package me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.mutable.MutableDouble;
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
import org.bukkit.util.noise.PerlinNoiseGenerator;

import me.Vark123.EpicRPGRespawn.Main;
import me.Vark123.EpicRPGRespawn.Utils.Utils;

public class HalloweenWisp extends APortalEffect {

    private static final Random rand = new Random();
	private static Map<Player, BukkitTask> portalEffects = new ConcurrentHashMap<>();
	private final ItemStack it;
	
    private final double amplitude = 0.5; // Wysokość fal
    private final double radius = 2; // Podstawowy promień
    
    private MutableDouble pentagramAngle = new MutableDouble(rand.nextDouble(Math.PI*2));
    private Vector pentagramAxis = new Vector(0,1,0);
	
	public HalloweenWisp() {
		super("halloween_wisp", "§3§lBLEDNY OGNIK");
		
		it = new ItemStack(Material.SOUL_LANTERN);{
			ItemMeta im = it.getItemMeta();
			im.setDisplayName(getDisplay());
			it.setItemMeta(im);
		}
	}

	@Override
	public void playShotEffect(Location loc) {
		animatePentagram(loc.clone(), 2, 0.1);
	}

	@Override
	public void startEffect(Player caster) {
		if(portalEffects.containsKey(caster))
			portalEffects.get(caster).cancel();

		Location loc = caster.getLocation();
		caster.playSound(loc, Sound.ENTITY_WITHER_AMBIENT, 1, 0.8f);
		
		List<Location> wispLocations = generateChaoticWave(loc);
		int[] wispPositions = new int[4];
		int positionStep = wispLocations.size() / wispPositions.length;
		for(int i = 0; i < wispPositions.length; ++i) {
			wispPositions[i] = positionStep * i % wispLocations.size();
		}
		
		BukkitTask task = new BukkitRunnable() {
			
			@Override
			public void run() {
				if(!caster.isOnline())
					cancel();
				if(isCancelled())
					return;
				
				{
					for(int i = 0; i < wispPositions.length; ++i) {
						loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, wispLocations.get(wispPositions[i]), 4, .1f, .1f, .1f, .02f);
						wispPositions[i] += 1;
						wispPositions[i] %= wispLocations.size();
					}
				}
				{
					drawPentagram(loc.clone().add(0,0.1,0));
					pentagramAngle.add(0.03);
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
		
		animatePentagram(caster.getLocation().clone(), 0.1, 2);
	}

	@Override
	public ItemStack getItem() {
		return it;
	}
	
	private List<Location> generateChaoticWave(Location center) {
		List<Location> points = new LinkedList<>();
        PerlinNoiseGenerator generator = new PerlinNoiseGenerator(rand);
        
        double angleStep = Math.PI / 32;
        for (double angle = 0; angle <= 2 * Math.PI; angle += angleStep) {
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            double y = center.getY() + 1.3 + generator.noise(x, z) * amplitude * 2;
            points.add(new Location(center.getWorld(), x, y, z));
        }

        return points;
	}
	
	private void animatePentagram(Location loc, double fromY, double toY) {
		double step = (toY - fromY) / 20.;
		new BukkitRunnable() {
			int timer = 2 * 10;
			double currentY = fromY;
			@Override
			public void run() {
				if(isCancelled() || timer <= 0)
					return;
				--timer;
				
				Location tmp = loc.clone().add(0, currentY, 0);
				currentY += step;
				drawPentagram(tmp);
			}
		}.runTaskTimer(Main.inst(), 0, 2);
	}
	
	private void drawPentagram(Location loc) {
		for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 32) {
	        double x = loc.getX() + radius * Math.cos(angle);
	        double z = loc.getZ() + radius * Math.sin(angle);
	        loc.getWorld().spawnParticle(Particle.SMOKE, x, loc.getY(), z, 0);
	    }
		Utils.drawPentagram(Particle.FLAME, loc.clone().add(0, 0.1, 0), pentagramAxis, 5, radius, 0.05, 2, pentagramAngle.doubleValue());
	}

}
