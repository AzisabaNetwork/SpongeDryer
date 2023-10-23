package net.azisaba.spongedryer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpongeDryer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        // イベントの処理
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return; // 右クリック以外の場合は処理を終了する
        }
        ItemStack clickedItem = e.getItem();
        if (!"§e§lスポンジ乾燥機".equals(getDisplayName(clickedItem))) return;
        int amount = e.getPlayer().isSneaking() ? 2304 : 1;
        ItemStack notRemoved = e.getPlayer().getInventory().removeItem(new ItemStack(Material.WET_SPONGE, amount)).get(0);
        int removedAmount = amount - (notRemoved != null ? notRemoved.getAmount() : 0);
        for (ItemStack stack : e.getPlayer().getInventory().addItem(new ItemStack(Material.SPONGE, removedAmount)).values()) {
            e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), stack);
        }
    }
    public static String getDisplayName(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        if (!meta.hasDisplayName()) return null;
        return meta.getDisplayName();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
