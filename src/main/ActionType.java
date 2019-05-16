package main;

enum ActionType {
    INIT,
    FORWARD,
    BACKWARD,
    UPDATE;

    @Override
    public String toString() {
        switch (this) {
            case INIT:
                return "INIT";
            case FORWARD:
                return "REDO";
            case BACKWARD:
                return "UNDO";
            case UPDATE:
                return "UPDATE";
            default:
                return "";
        }
    }
}