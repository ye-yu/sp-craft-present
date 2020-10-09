package sp.yeyu.presents;

import org.bukkit.Material;

import java.util.function.Supplier;

public enum PresentIngredients implements Supplier<Material> {

    RED(Material.RED_DYE),
    GRAY(Material.GRAY_DYE),
    PINK(Material.PINK_DYE),
    PURPLE(Material.PURPLE_DYE),

    ORANGE(Material.ORANGE_DYE),
    YELLOW(Material.YELLOW_DYE),
    GREEN(Material.GREEN_DYE),
    BLACK(Material.BLACK_DYE),

    CYAN(Material.CYAN_DYE),
    LIGHT_GRAY(Material.LIGHT_GRAY_DYE),
    LIME(Material.LIME_DYE),
    BROWN(Material.BROWN_DYE),

    MAGENTA(Material.MAGENTA_DYE),
    LIGHT_BLUE(Material.LIGHT_BLUE_DYE),
    BLUE(Material.BLUE_DYE),
    WHITE(Material.WHITE_DYE),

    PAPER(Material.PAPER);

    private final Material dye;

    PresentIngredients(Material dye) {
        this.dye = dye;
    }

    /**
     * Gets a dye or a paper material.
     *
     * @return a present material
     */
    @Override
    public Material get() {
        return dye;
    }

}
