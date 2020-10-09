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
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

public class CraftPresents extends JavaPlugin {

    private static JavaPlugin instance = null;
    public CraftPresents() {
        super();
        if(instance == null) instance = this;
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Objects.requireNonNull(getCommand("devpresent")).setExecutor(new DevPresentCommand());
        NBTEditor.getEmptyNBTCompound();
        Log.INSTANCE.info("NBTEditor is loaded.");
        Presents.craftPresent(this);
        getServer().getPluginManager().registerEvents(PresentEvent.INSTANCE, this);
        try {
            Data.INSTANCE.setDirectory(new File(getDataFolder(), "CraftPresentsData"));
        } catch (FileAlreadyExistsException e) {
            Log.INSTANCE.error("Cannot load plugin, reasons: ", e);
            setEnabled(false);
            return;
        }
        Log.INSTANCE.info("Plugin is activated.");
    }

    public void disable() {
        setEnabled(false);
    }
}
