package sp.yeyu.presents;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public enum PresentItems implements Supplier<Material> {

    WHITE(Material.WHITE_DYE),
    ORANGE(Material.ORANGE_DYE),
    MAGENTA(Material.MAGENTA_DYE),
    LIGHT_BLUE(Material.LIGHT_BLUE_DYE),
    YELLOW(Material.YELLOW_DYE),
    LIME(Material.LIME_DYE),
    PINK(Material.PINK_DYE),
    GRAY(Material.GRAY_DYE),
    LIGHT_GRAY(Material.LIGHT_GRAY_DYE),
    CYAN(Material.CYAN_DYE),
    PURPLE(Material.PURPLE_DYE),
    BLUE(Material.BLUE_DYE),
    BROWN(Material.BROWN_DYE),
    GREEN(Material.GREEN_DYE),
    RED(Material.RED_DYE),
    BLACK(Material.BLACK_DYE),
    PAPER(Material.PAPER);

    private final Material dye;

    PresentItems(Material dye) {
        this.dye = dye;
    }

    /**
     * Gets a result.
     *
     * @return a result
     */
    @Override
    public Material get() {
        return dye;
    }

    public static int indexOf(Material material) {
        for (int i = 0; i < values().length; i++) {
            Material dye = values()[i].get();
            if (dye == material) return i;
        }
        return -1;
    }

    public static int indexOf(ItemStack item) {
        return item == null ? -1 : indexOf(item.getType());
    }

}
