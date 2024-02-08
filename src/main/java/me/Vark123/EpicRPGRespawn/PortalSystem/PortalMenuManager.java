package me.Vark123.EpicRPGRespawn.PortalSystem;

import org.apache.commons.lang3.mutable.MutableInt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import me.Vark123.EpicRPGRespawn.Main;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayer;
import me.Vark123.EpicRPGRespawn.PlayerSystem.RespPlayerManager;

public final class PortalMenuManager {

	private static final PortalMenuManager inst = new PortalMenuManager();
	
	private final ItemStack noEffect;
	
	private PortalMenuManager() {
		noEffect = new ItemStack(Material.TINTED_GLASS);{
			ItemMeta im = noEffect.getItemMeta();
			im.setDisplayName("§7ZADEN");
			noEffect.setItemMeta(im);
		}
	}
	
	public static final PortalMenuManager get() {
		return inst;
	}
	
	public void openMenu(Player p) {
		RespPlayer rp = RespPlayerManager.get().getRespPlayer(p).get();
		int size = rp.getUnlockedEffects().size();
		if(size > 54)
			size = 54;
		int rows = size % 9;
		if(rows <= 0)
			rows = 1;
		
		RyseInventory.builder()
			.rows(rows)
			.title("§7[§5§lEFEKTY PORTALI§7]")
			.provider(getProvider(p, size))
			.build(Main.inst())
			.open(p);
	}
	
	private InventoryProvider getProvider(Player p, int size) {
		RespPlayer rp = RespPlayerManager.get().getRespPlayer(p).get();
		return new InventoryProvider() {
			@Override
			public void init(Player player, InventoryContents contents) {
				MutableInt index = new MutableInt();
				if(rp.getCurrentEffect() != null)
					contents.set(index.getAndIncrement(), IntelligentItem.of(noEffect, e -> {
						p.sendMessage(me.Vark123.EpicRPG.Main.getInstance().getPrefix()
								+" §azdjales z siebie efekt portalu §7["+rp.getCurrentEffect().getDisplay()+"§7]");
						rp.setCurrentEffect(null);
						p.closeInventory();
					}));
				
				rp.getUnlockedEffects().stream()
					.filter(effect -> rp.getCurrentEffect() == null || !effect.equals(rp.getCurrentEffect()))
					.forEach(effect -> {
						contents.set(index.getAndIncrement(), IntelligentItem.of(effect.getItem(), e -> {
							rp.setCurrentEffect(effect);
							p.sendMessage(me.Vark123.EpicRPG.Main.getInstance().getPrefix()
									+" §austawiles efekt portali §7["+effect.getDisplay()+"§7]");
							p.closeInventory();
						}));
					});
			}
		};
	}
	
}
