package me.Vark123.EpicRPGRespawn;

import java.io.File;
import java.math.BigInteger;

import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;
import me.Vark123.EpicRPGRespawn.FileSystem.FileManager;

@Getter
public final class Config {

	private static final Config config = new Config();
	
	private ResourcePack resourcepack;
	
	private Config() {
		
	}
	
	public static final Config get() {
		return config;
	}
	
	public void init() {
		File f = FileManager.get().getConfig();
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
		
		String url = fYml.getString("rescource-pack.url");
		String hash = fYml.getString("rescource-pack.hash");
		this.resourcepack = new ResourcePack(url, hash);
	}
	
	@Getter
	public class ResourcePack {
		private String url;
		private byte[] hash;
		public ResourcePack(String url, String hash) {
			this.url = url;
			this.hash = new BigInteger(hash.toUpperCase(), 16).toByteArray();
		}
	}

}
