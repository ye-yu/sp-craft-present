package sp.yeyu.presents;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public enum PresentEvent implements Listener {
    INSTANCE;

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final Block block = event.getBlock();
        if (block.getType() != Material.PLAYER_HEAD) return;
        final ItemStack itemInHand = event.getItemInHand();
        Log.INSTANCE.info(String.format("Item in main hand: %s", itemInHand));
        Log.INSTANCE.info(String.format("Item in main hand is present: %s", Presents.isPresent(itemInHand)));
    }
}
