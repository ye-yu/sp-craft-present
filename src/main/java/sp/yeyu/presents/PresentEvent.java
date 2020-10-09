/*
 * CraftPresents: Craft a Present for Your Friend!
 * Copyright (C) 2020 Raflie Zainuddin rafolwen98@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * */
package sp.yeyu.presents;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public enum PresentEvent implements Listener {
    INSTANCE;

    private static final HashMap<HumanEntity, Inventory> OPEN_INVENTORIES = new HashMap<>();

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        final Block block = event.getBlock();
        if (block.getType() != Material.PLAYER_HEAD) return;
        final ItemStack itemInHand = event.getItemInHand();
        if (Presents.isInvalidPresent(itemInHand)) return;
        try {
            Data.INSTANCE.recordPresent(block, itemInHand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        final Block block = event.getBlock();
        if (!Data.INSTANCE.isPresentBlock(block.getLocation())) return;
        try {
            for (ItemStack itemStack : Data.INSTANCE.getItemsFromBlock(block.getLocation())) {
                event.getPlayer().getWorld().dropItem(block.getLocation(), itemStack);
            }
            Data.INSTANCE.removeLoot(block.getLocation());
            event.getPlayer().getWorld().dropItem(block.getLocation(), getRandomPaper());
            event.setDropItems(false);
        } catch (IOException e) {
            Log.INSTANCE.errorWithDisable("Cannot get presents at " + block.getLocation() + " with reasons: " + e.getMessage(), e);
        }
    }

    @EventHandler
    public void onPresentRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR) return;
        final ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
        if (Presents.isInvalidPresent(itemInHand)) return;
        final Inventory present = Bukkit.createInventory(event.getPlayer(), 9, "Present");
        event.getPlayer().openInventory(present);
        // load present to inventory
        int i = 0;
        try {
            for (ItemStack itemStack : Data.INSTANCE.getItemsFromName(Objects.requireNonNull(Objects.requireNonNull(itemInHand.getItemMeta()).getLore()).get(1))) {
                present.setItem(i, itemStack);
                i++;
            }
        } catch (FileNotFoundException e) {
            Log.INSTANCE.errorWithDisable("Cannot get presents at main hand item with reasons: ", e);
        }
        OPEN_INVENTORIES.put(event.getPlayer(), present);
    }

    @EventHandler
    public void onPresentExit(InventoryCloseEvent event) {
        final Inventory inventory = event.getInventory();
        if (inventory != OPEN_INVENTORIES.getOrDefault(event.getPlayer(), null)) return;
        OPEN_INVENTORIES.remove(event.getPlayer());
        final ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
        if (Presents.isInvalidPresent(itemInHand)) {
            returnItemsToPlayer(event, inventory);
            return;
        }
        try {
            Data.INSTANCE.updatePresentsData(itemInHand, inventory.getContents());
        } catch (IOException e) {
            returnItemsToPlayer(event, inventory);
            Log.INSTANCE.errorWithDisable(e.getMessage(), e);
        }
    }

    private void returnItemsToPlayer(InventoryCloseEvent event, Inventory inventory) {
        if (Arrays.stream(inventory.getContents()).allMatch(Objects::isNull)) return;
        final HashMap<Integer, ItemStack> integerItemStackHashMap = event.getPlayer().getInventory().addItem(inventory.getContents());
        if (integerItemStackHashMap.isEmpty()) return;
        integerItemStackHashMap.forEach((index, item) -> event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), item));
    }

    private ItemStack getRandomPaper() {
        return new ItemStack(Material.PAPER, 1 + RANDOM.nextInt(3));
    }
}
