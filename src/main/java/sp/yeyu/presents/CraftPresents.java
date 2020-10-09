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
