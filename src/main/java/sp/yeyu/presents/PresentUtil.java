package sp.yeyu.presents;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.function.Supplier;

public enum PresentUtil implements Supplier<ItemStack> {
    RED_BOX("https://textures.minecraft.net/texture/6cef9aa14e884773eac134a4ee8972063f466de678363cf7b1a21a85b7", Material.RED_DYE);

    private static final Random random = new Random(System.currentTimeMillis());
    private final Material color;
    private final String texture;

    PresentUtil(String textureURL, Material dyeColor) {
        texture = textureURL;
        color = dyeColor;
    }

    public ItemStack get() {
        return NBTEditor.getHead(texture);
    }

    public void craft(JavaPlugin plugin) {
        final ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "recipe"), get());
        recipe.shape(" P ", "PCP", " P ");
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('C', color);
    }

    public static int length() {
        return values().length;
    }

    public static PresentUtil random() {
        return values()[random.nextInt(PresentUtil.length())];
    }

}
