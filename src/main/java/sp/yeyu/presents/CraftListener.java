package sp.yeyu.presents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class CraftListener implements Listener {

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        final int stackHash = Presents.hashItemStack(event.getInventory().getMatrix());
        if (stackHash < 0) return;
        final Presents craftedPresent = Presents.getOrNull(stackHash);
        if (craftedPresent == null) return;
        event.getInventory().setResult(craftedPresent.get());
    }
}
