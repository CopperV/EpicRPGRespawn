package me.Vark123.EpicRPGRespawn.PortalSystem.PortalEffects;

import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.Vark123.EpicRPGRespawn.PortalSystem.IPortalEffect;

@Getter
@AllArgsConstructor
public abstract class APortalEffect implements IPortalEffect {

	private String id;
	private String display;
	
	public abstract ItemStack getItem();
	
}
