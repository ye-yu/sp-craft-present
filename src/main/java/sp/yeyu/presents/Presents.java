package sp.yeyu.presents;

import com.google.common.collect.Lists;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public enum Presents implements Supplier<ItemStack> {
    RED_BOX("https://textures.minecraft.net/texture/6cef9aa14e884773eac134a4ee8972063f466de678363cf7b1a21a85b7", PresentIngredients.RED.get()),
    GRAY_BOX("http://textures.minecraft.net/texture/ac3821d4f61b17f82f0d7a8e5312608ff50ede29b1b4dc89847be9427d36", PresentIngredients.GRAY.get()),
    PINK_BOX("http://textures.minecraft.net/texture/10c75a05b344ea043863974c180ba817aea68678cbea5e4ba395f74d4803d1d", PresentIngredients.PINK.get()),
    PURPLE_BOX("http://textures.minecraft.net/texture/75419fce506a495343a1d368a71d22413f08c6d67cb951d656cd03f80b4d3d3", PresentIngredients.PURPLE.get()),

    ORANGE_BOX("http://textures.minecraft.net/texture/ee3a8fd0852977444d9fd7797cac07b8d3948addc43f0bb5ce25ae72d95dc", PresentIngredients.ORANGE.get()),
    YELLOW_BOX("http://textures.minecraft.net/texture/a3e58ea7f3113caecd2b3a6f27af53b9cc9cfed7b043ba334b5168f1391d9", PresentIngredients.YELLOW.get()),
    GREEN_BOX("http://textures.minecraft.net/texture/d08ce7deba56b726a832b61115ca163361359c30434f7d5e3c3faa6fe4052", PresentIngredients.GREEN.get()),
    BLACK_BOX("http://textures.minecraft.net/texture/5c712b1971c5f42eeff80551179220c08b8213eacbe6bc19d238c13f86e2c0", PresentIngredients.BLACK.get()),

    CYAN_BOX("http://textures.minecraft.net/texture/59f0743576bba4a2622480548970b721543d2c457955e8dd5c4f9ddb6a56b95c", PresentIngredients.CYAN.get()),
    LIGHT_GRAY_BOX("http://textures.minecraft.net/texture/93ed8799d07b8e2da2557c3f0598fdaed944376826f1d2ceb670fd651b2cd166", PresentIngredients.LIGHT_GRAY.get()),
    LIME_BOX("http://textures.minecraft.net/texture/301b2cee6b3688f75e02ef4a740ee67ea42ac537d8ca401c200608ae02608b5", PresentIngredients.LIME.get()),
    BROWN_BOX("http://textures.minecraft.net/texture/278f1a858d66b9e7951f70acea2c19ab6c0af88ca5db516f1a1ff51f06b2c", PresentIngredients.BROWN.get()),

    LIGHT_BLUE_BOX("http://textures.minecraft.net/texture/b5651a18f54714b0b8f7f011c018373b33fd1541ca6f1cfe7a6c97b65241f5", PresentIngredients.LIGHT_BLUE.get()),
    BLUE_BOX("http://textures.minecraft.net/texture/84e1c42f11383b9dc8e67f2846fa311b16320f2c2ec7e175538dbff1dd94bb7", PresentIngredients.BLUE.get()),
    MAGENTA_BOX("http://textures.minecraft.net/texture/14caafd233d3afd4b6f2132c63a694d012bad6d923316b3aa5c3768fee3339", PresentIngredients.MAGENTA.get()),
    WHITE_BOX("http://textures.minecraft.net/texture/10f5398510b1a05afc5b201ead8bfc583e57d7202f5193b0b761fcbd0ae2", PresentIngredients.WHITE.get());


    private static final Random random = new Random(System.currentTimeMillis());
    private final Material dyeColor;
    private final String texture;
    private final String namespaceName;
    private final String displayName;

    Presents(String textureURL, Material dyeColor) {
        texture = textureURL;
        this.dyeColor = dyeColor;
        namespaceName = String.join("", name().toLowerCase(Locale.ENGLISH).split("_"));
        displayName = WordUtils.capitalize(name().toLowerCase(Locale.ENGLISH).replace('_', ' '));
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean isInvalidPresent(ItemStack item) {
        return item.getItemMeta() == null || !item.getItemMeta().hasLore() || !item.getItemMeta().getLore().contains("Present Tag");
    }

    public ItemStack get() {
        final ItemStack head = NBTEditor.getHead(texture);
        head.setItemMeta(renamePresent(Objects.requireNonNull(head.getItemMeta())));
        return head;
    }

    private ItemMeta renamePresent(final ItemMeta itemMeta) {
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Lists.newArrayList("Present Tag", "Empty"));
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
        recipe.shape("PPP", "PDP", "PPP");
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
