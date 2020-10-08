package sp.yeyu.presents;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public enum HeadUtil implements Supplier<ItemStack> {
    RED_BOX("d94ea9a8-3336-46c6-900a-65c9faaf7949");

    private static final Random random = new Random(System.currentTimeMillis());
    private final OfflinePlayer player;
    HeadUtil(String uuid) {
        player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
    }

    public ItemStack get() {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = Objects.requireNonNull((SkullMeta) item.getItemMeta());
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);
        return item;
    }

    public static int length() {
        return values().length;
    }

    public static HeadUtil random() {
        return values()[random.nextInt(HeadUtil.length())];
    }
}
