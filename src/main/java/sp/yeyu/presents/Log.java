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

public enum Log {
    INSTANCE;

    private static final int LOOK_BACK = 2;

    public void info(String s) {
        final StackTraceElement[] currentStackTrace = Thread.currentThread().getStackTrace();
        final int lookBack = Math.min(Log.LOOK_BACK, currentStackTrace.length);
        CraftPresents.getInstance().getLogger().info(trimClassName(currentStackTrace[lookBack].getClassName()) + ": " + s);
    }

    public String trimClassName(final String className) {
        final String[] split = className.split("\\.");
        return split[Math.max(split.length - 1, 0)];
    }

    public void error(String s, Throwable e) {
        CraftPresents.getInstance().getLogger().severe(s);
        CraftPresents.getInstance().getLogger().severe(e.getMessage());
        e.printStackTrace();
    }

    public void errorWithDisable(String s, Throwable e) {
        error(s, e);
        ((CraftPresents)CraftPresents.getInstance()).disable();
    }

    public void warn(String s) {
        final StackTraceElement[] currentStackTrace = Thread.currentThread().getStackTrace();
        final int lookBack = Math.min(Log.LOOK_BACK, currentStackTrace.length);
        CraftPresents.getInstance().getLogger().warning(trimClassName(currentStackTrace[lookBack].getClassName()) + ": " + s);
    }

    public void warn(String s, Throwable e) {
        CraftPresents.getInstance().getLogger().warning(s);
        CraftPresents.getInstance().getLogger().warning(e.getMessage());
        e.printStackTrace();
    }

}
