package org.semdevko.simplejsonorm;

import org.semdevko.simplejsonorm.core.Entity;

public class BaseEntity implements Entity {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
