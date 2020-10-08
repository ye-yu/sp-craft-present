package sp.yeyu.presents;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.function.Supplier;

public enum HeadUtil implements Supplier<ItemStack> {
    RED_BOX("https://textures.minecraft.net/texture/5fde3bfce2d8cb724de8556e5ec21b7f15f584684ab785214add164be7624b");

    private static final Random random = new Random(System.currentTimeMillis());
    private final String texture;

    HeadUtil(String textureURL) {
        texture = textureURL;
    }

    public ItemStack get() {
        return NBTEditor.getHead(texture);
    }

    public static int length() {
        return values().length;
    }

    public static HeadUtil random() {
        return values()[random.nextInt(HeadUtil.length())];
    }

}
