package sp.yeyu.presents;

public enum Log {
    INSTANCE;

    private static final int LOOK_BACK = 2;

    public void info(String message) {
        final StackTraceElement[] currentStackTrace = Thread.currentThread().getStackTrace();
        final int lookBack = Math.min(Log.LOOK_BACK, currentStackTrace.length);
        CraftPresents.getInstance().getLogger().info(trimClassName(currentStackTrace[lookBack].getClassName()) + ": " + message);
    }

    public String trimClassName(final String className) {
        final String[] split = className.split("\\.");
        return split[Math.max(split.length - 1, 0)];
    }
}
