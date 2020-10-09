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

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.util.*;
import java.util.stream.Collectors;

public enum Data {
    INSTANCE;

    protected static volatile File directory;

    protected void setDirectory(File directory) throws FileAlreadyExistsException {
        Data.directory = directory;
        if (!directory.exists() && directory.mkdirs()) Log.INSTANCE.info("Created directory folder: " + directory);
        else if (directory.isFile()) throw new FileAlreadyExistsException(directory + " is a directory");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void recordPresent(Block block, ItemStack itemInHand) throws IOException {
        final String filename = toFileName(block.getLocation());
        final File file = new File(directory, filename);
        if (file.exists() && !file.delete()) throw new FileSystemException("File exists and cannot be deleted: " + file);
        file.createNewFile();
        try(final FileWriter writer = new FileWriter(file)) {
            writer.write(Objects.requireNonNull(Objects.requireNonNull(itemInHand.getItemMeta()).getLore()).get(1));
        }
    }

    public String toFileName(Location location) {
        return String.format("%d,%d,%d.txt", location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public boolean isPresentBlock(Location location) {
        return new File(directory, toFileName(location)).exists();
    }

    public ArrayList<ItemStack> getItemsFromBlock(Location location) throws IOException {
        final String filename = toFileName(location);
        final File file = new File(directory, filename);
        if (!file.exists()) throw new FileSystemException("Cannot find data for present at: " + filename);
        try(final Scanner reader = new Scanner(file)) {
            return getItemsFromName(reader.nextLine());
        }
    }

    public ArrayList<ItemStack> getItemsFromName(String filename) throws FileNotFoundException {
        final ArrayList<ItemStack> list = new ArrayList<>();
        if (filename.equals("Empty")) return list;
        try(final Scanner scanner = new Scanner(new File(directory, filename))) {
            while(scanner.hasNextLine()) {
                list.add(NBTEditor.getItemFromTag(NBTEditor.NBTCompound.fromJson(scanner.nextLine())));
            }
        }
        return list;
    }

    public void updatePresentsData(final ItemStack presentItem, final ItemStack[] contents) throws IOException {
        final ItemMeta itemMeta = Objects.requireNonNull(presentItem.getItemMeta());
        final List<String> lore = Objects.requireNonNull(itemMeta.getLore());
        final String filename = lore.get(1).equals("Empty") ? UUID.randomUUID().toString() : lore.get(1);
        final File file = new File(directory, filename);
        if (file.exists() && !file.delete()) {
            Log.INSTANCE.errorWithDisable("Cannot update present of: \n", new Throwable());
            return;
        }

        try(final FileWriter writer = new FileWriter(file)) {
            for (ItemStack content : contents) {
                if (content == null) continue;
                final NBTEditor.NBTCompound nbtCompound = NBTEditor.getNBTCompound(content);
                writer.write(nbtCompound.toJson());
                writer.write("\n");
            }
        }
        lore.set(1, filename);
        itemMeta.setLore(lore);
        presentItem.setItemMeta(itemMeta);
    }

    private <T> String arrayToString(T[] arr) {
        return Arrays.stream(arr).map(Objects::toString).collect(Collectors.joining("\n"));
    }

    public void removeLoot(Location location) throws FileSystemException {
        final String filename = toFileName(location);
        final File file = new File(directory, filename);
        if (file.exists() && !file.delete()) throw new FileSystemException("Cannot remove loot data for present at: " + filename);
    }
}
