package me.Vark123.EpicRPGRespawn.RespSystem;

import lombok.Getter;
import lombok.ToString;
import me.Vark123.EpicRPGRespawn.Utils.RpgLocation;

@Getter
@ToString
public class RespawnPoint {

	private String respRegion;
	private RpgLocation respLoc;
	private String portalPerm;
	
	public RespawnPoint(String respRegion, RpgLocation respLoc, String portalPerm) {
		this.respRegion = respRegion;
		this.respLoc = respLoc;
		this.portalPerm = portalPerm;
	}
	
}
