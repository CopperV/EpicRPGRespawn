package me.Vark123.EpicRPGRespawn.FileSystem;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Vark123.EpicRPGRespawn.Main;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayer;
import me.Vark123.EpicRPGRespawn.PortalSystem.APortal;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalManager;
import me.Vark123.EpicRPGRespawn.PortalSystem.Portals.PremiumPortal;
import me.Vark123.EpicRPGRespawn.PortalSystem.Portals.StandardPortal;
import me.Vark123.EpicRPGRespawn.RespSystem.RespManager;
import me.Vark123.EpicRPGRespawn.RespSystem.RespawnPoint;
import me.Vark123.EpicRPGRespawn.Utils.RpgLocation;

public final class FileManager {
	
	private static final FileManager inst = new FileManager();

	private final File portals = new File(Main.inst().getDataFolder(), "portals.yml");
	private final File resps = new File(Main.inst().getDataFolder(), "respawn.yml");
	private final File users = new File(Main.inst().getDataFolder(), "users");
	
	private FileManager() {
		init();
	}
	
	public static final FileManager get() {
		return inst;
	}
	
	private void init() {
		if(!Main.inst().getDataFolder().exists())
			Main.inst().getDataFolder().mkdir();
		
		if(!users.exists())
			users.mkdir();
		
		try {
			if(!resps.exists())
				resps.createNewFile();
			if(!portals.exists())
				portals.createNewFile();
		} catch(IOException e) {
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(Main.inst());
			return;
		}
		
		loadPortals();
		loadResps();
	}
	
	public boolean playerFileExists(Player p) {
		String fileName = p.getUniqueId().toString()+".yml";
		File f = new File(users, fileName);
		return f.exists();
	}
	
	public RespPlayer loadPlayer(Player p) {
		File f = getPlayerFile(p);
		if(f == null) {
			return new RespPlayer(p, "tut");
		}
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
		String resp = fYml.getString("respawn");
		return new RespPlayer(p, resp);
	}
	
	public void savePlayer(RespPlayer respPlayer) {
		File f = getPlayerFile(respPlayer.getPlayer());
		if(f == null) {
			System.err.println("Cannot save player resp info ["+respPlayer.getPlayer().getName()+"]");
			return;
		}
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
		fYml.set("respawn", respPlayer.getRespLoc());
		fYml.set("display", respPlayer.getPlayer().getName());
		try {
			fYml.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getPlayerFile(Player p) {
		UUID uid = p.getUniqueId();
		String fileName = uid.toString()+".yml";
		File f = new File(users, fileName);
		if(f.exists())
			return f;
		try {
			f.createNewFile();
			YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
			fYml.set("respawn", "tut");
			fYml.set("UUID", uid.toString());
			fYml.set("display", p.getName());
			fYml.save(f);
			return f;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void savePortal(APortal portal) {
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(portals);
		String key = portal.getPortalRegion();
		Location loc = portal.getDestiny().bukkitLocation();
		fYml.set(key+".world", loc.getWorld().getName());
		fYml.set(key+".region", portal.getPortalRegion());
		fYml.set(key+".x", loc.getX());
		fYml.set(key+".y", loc.getY());
		fYml.set(key+".z", loc.getZ());
		fYml.set(key+".pitch", loc.getPitch());
		fYml.set(key+".yaw", loc.getYaw());
		if(portal instanceof PremiumPortal) {
			PremiumPortal premiumPortal = (PremiumPortal) portal;
			fYml.set(key+".perm", premiumPortal.getPerm());
		}
		try {
			fYml.save(portals);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveResp(RespawnPoint point) {
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(resps);
		String key = point.getRespRegion();
		Location loc = point.getRespLoc().bukkitLocation();
		fYml.set(key+".world", loc.getWorld().getName());
		fYml.set(key+".region", point.getRespRegion());
		fYml.set(key+".perm", point.getPortalPerm());
		fYml.set(key+".x", loc.getX());
		fYml.set(key+".y", loc.getY());
		fYml.set(key+".z", loc.getZ());
		fYml.set(key+".pitch", loc.getPitch());
		fYml.set(key+".yaw", loc.getYaw());
		try {
			fYml.save(resps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteResp(RespawnPoint point) {
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(resps);
		fYml.getKeys(false).stream().filter(key -> {
			return fYml.getString(key+".region").equals(point.getRespRegion());
		}).forEach(key -> {
			fYml.set(key, null);
		});
		try {
			fYml.save(resps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadPortals() {
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(portals);
		
		fYml.getKeys(false).forEach(key -> {
			String type = fYml.contains(key+".type") ?
					fYml.getString(key+".type") : "standard";
			String region = fYml.getString(key+".region");
			
			String world = fYml.getString(key+".world");
			double x = fYml.getDouble(key+".x");
			double y = fYml.getDouble(key+".y");
			double z = fYml.getDouble(key+".z");
			float pitch = (float) fYml.getDouble(key+".pitch");
			float yaw = (float) fYml.getDouble(key+".yaw");
			
			String perm = fYml.getString(key+".perm");
			
			RpgLocation rpgLoc = new RpgLocation(world, x, y, z, pitch, yaw);
			APortal portal;
			switch(type.toLowerCase()) {
				case "standard":
					portal = new StandardPortal(region, rpgLoc);
					break;
				case "premium":
					portal = new PremiumPortal(region, rpgLoc, perm);
					break;
				default:
					portal = new StandardPortal(region, rpgLoc);
					break;
			}
			PortalManager.get().registerPortal(portal);
		});
	}
	
	private void loadResps() {
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(resps);
		
		fYml.getKeys(false).forEach(key -> {
			String region = fYml.getString(key+".region");
			
			String world = fYml.getString(key+".world");
			double x = fYml.getDouble(key+".x");
			double y = fYml.getDouble(key+".y");
			double z = fYml.getDouble(key+".z");
			float pitch = (float) fYml.getDouble(key+".pitch");
			float yaw = (float) fYml.getDouble(key+".yaw");
			
			String perm = fYml.getString(key+".perm");
			
			RpgLocation rpgLoc = new RpgLocation(world, x, y, z, pitch, yaw);
			RespawnPoint resp = new RespawnPoint(region, rpgLoc, perm);
			RespManager.get().registerResp(resp);
		});
	}
	
}
