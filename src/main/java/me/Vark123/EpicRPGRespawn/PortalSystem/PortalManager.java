package me.Vark123.EpicRPGRespawn.PortalSystem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

@Getter
public final class PortalManager {

	private static final PortalManager inst = new PortalManager();
	
	private Map<String, APortal> portals;
	
	private PortalManager() {
		portals = new ConcurrentHashMap<>();
	}
	
	public static final PortalManager get() {
		return inst;
	}
	
	public void registerPortal(APortal portal) {
		portals.put(portal.getPortalRegion(), portal);
	}
	
}
