package sp.yeyu.presents;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Random;

public enum PresentEvent implements Listener {
    INSTANCE;

    private static final Random random = new Random(System.currentTimeMillis());

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        final Block block = event.getBlock();
        if (block.getType() != Material.PLAYER_HEAD) return;
        final ItemStack itemInHand = event.getItemInHand();
        if (!Presents.isPresent(itemInHand)) return;
        try {
            Data.INSTANCE.recordPresent(block, itemInHand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        final Block block = event.getBlock();
        if (!Data.INSTANCE.isPresentBlock(block.getLocation())) return;
        try {
            for (ItemStack itemStack : Data.INSTANCE.getItemsFromBlock(block.getLocation())) {
                event.getPlayer().getWorld().dropItem(block.getLocation(), itemStack);
            }
            event.getPlayer().getWorld().dropItem(block.getLocation(), getRandomPaper());
        } catch (IOException e) {
            Log.INSTANCE.errorWithDisable("Cannot get presents at " + block.getLocation() + " with reasons: ", e);
        }
    }

    private ItemStack getRandomPaper() {
        return new ItemStack(Material.PAPER, 1 + random.nextInt(3));
    }
}
