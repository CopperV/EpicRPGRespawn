package me.Vark123.EpicRPGRespawn.RespSystem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

@Getter
public final class RespManager {

	private static final RespManager inst = new RespManager();
	
	private final Map<String, RespawnPoint> resps;
	
	private RespManager() {
		resps = new ConcurrentHashMap<>();
	}
	
	public static final RespManager get() {
		return inst;
	}
	
	public void registerResp(RespawnPoint resp) {
		resps.put(resp.getRespRegion(), resp);
	}
	
}
