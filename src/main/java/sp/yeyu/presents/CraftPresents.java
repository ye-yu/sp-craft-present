package sp.yeyu.presents;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.plugin.java.JavaPlugin;

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
        Log.INSTANCE.info("Plugin is activated.");
        NBTEditor.getEmptyNBTCompound();
        Log.INSTANCE.info("NBTEditor is loaded.");
        Presents.craftPresent(this);
    }
}
