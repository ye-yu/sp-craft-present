package sp.yeyu.presents;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public enum Presents implements Supplier<ItemStack> {
    RED_BOX("https://textures.minecraft.net/texture/6cef9aa14e884773eac134a4ee8972063f466de678363cf7b1a21a85b7", PresentIngredients.RED.get());

    private static final Random random = new Random(System.currentTimeMillis());
    private final Material dyeColor;
    private final String texture;
    private final String namespaceName;
    private Recipe recipe = null;

    Presents(String textureURL, Material dyeColor) {
        texture = textureURL;
        this.dyeColor = dyeColor;
        namespaceName = String.join("", name().toLowerCase(Locale.ENGLISH).split("_"));
    }

    public ItemStack get() {
        final ItemStack head = NBTEditor.getHead(texture);
        head.setItemMeta(renamePresent(Objects.requireNonNull(head.getItemMeta())));
        return head;
    }

    private ItemMeta renamePresent(final ItemMeta itemMeta) {
        itemMeta.setDisplayName("Present");
        return itemMeta;
    }

    public static int length() {
        return values().length;
    }

    public static Presents random() {
        return values()[random.nextInt(Presents.length())];
    }

    protected void craft(JavaPlugin plugin) {
        final NamespacedKey key = new NamespacedKey(plugin, namespaceName);
        final ShapedRecipe recipe = new ShapedRecipe(key, get());
        recipe.shape(" P ", "PDP", " P ");
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('D', dyeColor);
        Bukkit.addRecipe(recipe);
        this.recipe = Objects.requireNonNull(Bukkit.getRecipe(key));
    }

    public static void craftPresent(JavaPlugin plugin) {
        for (Presents value : values()) {
            value.craft(plugin);
        }
    }
}
