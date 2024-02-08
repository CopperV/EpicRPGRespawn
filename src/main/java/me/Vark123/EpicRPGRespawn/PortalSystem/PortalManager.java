package me.Vark123.EpicRPGRespawn.PortalSystem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects.APortalEffect;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects.BloodPortalEffect;
import me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects.SandPortalEffect;

@Getter
public final class PortalManager {

	private static final PortalManager inst = new PortalManager();
	
	private Map<String, APortal> portals;
	private Map<String, APortalEffect> portalEffects;
	
	private PortalManager() {
		portals = new ConcurrentHashMap<>();
		portalEffects = new ConcurrentHashMap<>();
		
		registerPortalEffect(new BloodPortalEffect());
		registerPortalEffect(new SandPortalEffect());
	}
	
	public static final PortalManager get() {
		return inst;
	}
	
	public void registerPortal(APortal portal) {
		portals.put(portal.getPortalRegion(), portal);
	}
	
	public void registerPortalEffect(APortalEffect portalEffect) {
		portalEffects.put(portalEffect.getId(), portalEffect);
	}
	
}
