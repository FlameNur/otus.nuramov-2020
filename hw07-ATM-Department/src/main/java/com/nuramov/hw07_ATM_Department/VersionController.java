package com.nuramov.hw07_ATM_Department;

public class VersionController {
    private Save save;

    public Save getSave() {
        return save;
    }

    public void setSave(Save save) {
        this.save = save;
    }

    /*
    private final Deque<Save> history = new LinkedList<>();

    public void storeAndExecute(Save save) {
        this.history.add(save); // optional
        cmd.execute();
    }

    public void undo() {
        if (history.size() > 1) {
            history.removeLast();
            history.getLast().execute();
        }
    }
     */
}
