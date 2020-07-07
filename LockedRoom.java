public class LockedRoom extends Room {

    private boolean locked = true;
    private String key;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     *
     * @param description The room's description.
     */
    public LockedRoom(String description, String key) {
        super(description);
        this.key = key;
    }

    public boolean openRoom(String key) {
        if (this.key.equals(key)) {
            this.locked = false;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isLocked() {
        return this.locked;
    }

}
