package com.balazs.hajdu.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * @author Balazs Hajdu
 */
public class AbstractDocument {

    @Id
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractDocument that = (AbstractDocument) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }

}
