package me.Vark123.EpicRPGRespawn.FileSystem;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Vark123.EpicRPGRespawn.Main;
import me.Vark123.EpicRPGRespawn.PortalSystem.APortal;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalManager;
import me.Vark123.EpicRPGRespawn.PortalSystem.Portals.PremiumPortal;
import me.Vark123.EpicRPGRespawn.PortalSystem.Portals.StandardPortal;
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
	
	//TODO
	private void loadResps() {
		
	}
	
}
