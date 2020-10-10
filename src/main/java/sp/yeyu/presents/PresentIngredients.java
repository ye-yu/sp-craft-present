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

import org.bukkit.Material;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

public enum PresentIngredients implements Supplier<Material> {

    BLACK("BLACK_DYE", "INK_SAC"),
    BLUE("BLUE_DYE", "LAPIS_LAZULI"),
    BROWN("BROWN_DYE", "COCOA_BEANS"),
    CYAN("CYAN_DYE"),

    GRAY("GRAY_DYE"),
    GREEN("GREEN_DYE", "CACTUS_GREEN"),
    LIGHT_BLUE("LIGHT_BLUE_DYE"),
    LIGHT_GRAY("LIGHT_GRAY_DYE"),

    LIME("LIME_DYE"),
    MAGENTA("MAGENTA_DYE"),
    ORANGE("ORANGE_DYE"),
    PINK("PINK_DYE"),

    PURPLE("PURPLE_DYE"),
    RED("RED_DYE", "ROSE_RED"),
    WHITE("WHITE_DYE", "BONE_MEAL"),
    YELLOW("YELLOW_DYE", "DANDELION_YELLOW");

    private final Material dye;

    PresentIngredients(String... names) {
        Material candidateDye;
        try {
            candidateDye = Arrays.stream(names).map(PresentIngredients::getMaterial).filter(Objects::nonNull).findAny().orElseThrow(() -> new NoSuchFieldException("Cannot find material for " + name()));
        } catch (NoSuchFieldException e) {
            Log.INSTANCE.errorWithDisable("Error: ", e);
            candidateDye = null;
        }
        dye = candidateDye;
    }

    public static Material getMaterial(String enumName) {
        try {
            return Material.valueOf(enumName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Gets a dye or a paper material.
     *
     * @return a present material
     */
    @Override
    public Material get() {
        return dye;
    }
}
