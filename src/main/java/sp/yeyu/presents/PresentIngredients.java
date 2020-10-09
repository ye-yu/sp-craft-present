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

import java.util.function.Supplier;

public enum PresentIngredients implements Supplier<Material> {

    RED(Material.RED_DYE),
    GRAY(Material.GRAY_DYE),
    PINK(Material.PINK_DYE),
    PURPLE(Material.PURPLE_DYE),

    ORANGE(Material.ORANGE_DYE),
    YELLOW(Material.YELLOW_DYE),
    GREEN(Material.GREEN_DYE),
    BLACK(Material.BLACK_DYE),

    CYAN(Material.CYAN_DYE),
    LIGHT_GRAY(Material.LIGHT_GRAY_DYE),
    LIME(Material.LIME_DYE),
    BROWN(Material.BROWN_DYE),

    MAGENTA(Material.MAGENTA_DYE),
    LIGHT_BLUE(Material.LIGHT_BLUE_DYE),
    BLUE(Material.BLUE_DYE),
    WHITE(Material.WHITE_DYE),

    PAPER(Material.PAPER);

    private final Material dye;

    PresentIngredients(Material dye) {
        this.dye = dye;
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
