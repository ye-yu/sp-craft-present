package sp.yeyu.presents;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemUtil {
    INSTANCE;
    public ItemStack of(Material material) {
        return new ItemStack(material);
    }
}
