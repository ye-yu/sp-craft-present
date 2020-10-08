package sp.yeyu.presents;

public enum Log {
    INSTANCE;

    private static final int LOOK_BACK = 4;

    public void info(String message) {
        final StackTraceElement[] currentStackTrace = Thread.currentThread().getStackTrace();
        final int lookBack = Math.max(currentStackTrace.length - Log.LOOK_BACK, 0);
        CraftPresents.getInstance().getLogger().info("[CraftPresents:" + currentStackTrace[lookBack].getClassName() + "] " + message);
    }
}
