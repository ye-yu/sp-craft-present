package sp.yeyu.presents;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public enum Presents implements Supplier<ItemStack> {
    RED_BOX("https://textures.minecraft.net/texture/6cef9aa14e884773eac134a4ee8972063f466de678363cf7b1a21a85b7", PresentIngredients.RED.get());

    private static final Random random = new Random(System.currentTimeMillis());
    private static final HashMap<Integer, Presents> map = new HashMap<>();
    private final Material dyeColor;
    private final String texture;
    private final int itemHash;
    private final String namespaceName;

    Presents(String textureURL, Material dyeColor) {
        texture = textureURL;
        this.dyeColor = dyeColor;
        itemHash = hashItemStack(new ItemStack[]{null, ItemUtil.INSTANCE.of(Material.PAPER), null,
                ItemUtil.INSTANCE.of(Material.PAPER), ItemUtil.INSTANCE.of(this.dyeColor), ItemUtil.INSTANCE.of(Material.PAPER),
                null, ItemUtil.INSTANCE.of(Material.PAPER), null
        });
        namespaceName = String.join("", name().toLowerCase(Locale.ENGLISH).split("_"));
        System.out.println(name() + " => " + itemHash);
    }

    static {
        for (Presents value : values()) {
            map.put(value.itemHash, value);
        }
    }

    public ItemStack get() {
        return NBTEditor.getHead(texture);
    }

    public static int length() {
        return values().length;
    }

    public static Presents random() {
        return values()[random.nextInt(Presents.length())];
    }

    public static int hashItemStack(ItemStack [] items) {
        return IntStream.range(0, items.length).map(n -> {
            final ItemStack item = items[n];
            final int dyeIndex = PresentIngredients.indexOf(item);
            if (item == null || dyeIndex == -1) return 0;
            else return (dyeIndex + 1) + n * (PresentIngredients.values().length + 1);
        }).sum();
    }

    public static Presents getOrNull(int itemHash) {
        return map.getOrDefault(itemHash, null);
    }

    public void craft(JavaPlugin plugin) {
        final NamespacedKey key = new NamespacedKey(plugin, namespaceName);
        final ShapedRecipe recipe = new ShapedRecipe(key, get());
        recipe.shape(" P ", "PDP", " P ");
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('D', dyeColor);
        Bukkit.addRecipe(recipe);
        Objects.requireNonNull(Bukkit.getRecipe(key));
    }

    public static void craftPresent(JavaPlugin plugin) {
        for (Presents value : values()) {
            value.craft(plugin);
        }
    }
}
