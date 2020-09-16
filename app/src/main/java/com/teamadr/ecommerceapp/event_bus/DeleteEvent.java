package com.teamadr.ecommerceapp.event_bus;

public class DeleteEvent {
    private boolean isDelete;

    public DeleteEvent(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
