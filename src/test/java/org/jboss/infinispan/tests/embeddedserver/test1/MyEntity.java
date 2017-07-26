package org.jboss.infinispan.tests.embeddedserver.test1;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoMessage;

/**
 * The Class MyEntity.
 *
 * @author Andrea Battaglia (Red Hat)
 */
@ProtoMessage
@ProtoDoc("@Indexed")
public class MyEntity {

    /** The id. */
    private int id;

    /** The my ref id. */
    private String myRefId;

    /** The my date. */
    private Date myDate;

    /** The my collection. */
    private List<String> myCollection;

    /**
     * Gets the id.
     *
     * @return the id
     */
    @ProtoField(number = 1, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the my ref id.
     *
     * @return the my ref id
     */
    @ProtoField(number = 2, name = "guid", required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public String getMyRefId() {
        return myRefId;
    }

    /**
     * Sets the my ref id.
     *
     * @param myRefId
     *            the new my ref id
     */
    public void setMyRefId(String myRefId) {
        this.myRefId = myRefId;
    }

    /**
     * Gets the my date.
     *
     * @return the my date
     */
    @ProtoField(number = 3, name = "date", required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public Date getMyDate() {
        return myDate;
    }

    /**
     * Sets the my date.
     *
     * @param myDate
     *            the new my date
     */
    public void setMyDate(Date myDate) {
        this.myDate = myDate;
    }

    /**
     * Gets the my collection.
     *
     * @return the my collection
     */
    @ProtoField(number = 4, name = "list", required = false, collectionImplementation=ArrayList.class)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public List<String> getMyCollection() {
        return myCollection;
    }

    /**
     * Sets the my collection.
     *
     * @param myCollection
     *            the new my collection
     */
    public void setMyCollection(List<String> myCollection) {
        this.myCollection = myCollection;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyEntity other = (MyEntity) obj;
        if (id != other.id)
            return false;
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MyEntity [id=");
        builder.append(id);
        builder.append(", ");
        if (myRefId != null) {
            builder.append("myRefId=");
            builder.append(myRefId);
            builder.append(", ");
        }
        if (myDate != null) {
            builder.append("myDate=");
            builder.append(myDate);
            builder.append(", ");
        }
        if (myCollection != null) {
            builder.append("myCollection=");
            builder.append(myCollection);
        }
        builder.append("]");
        return builder.toString();
    }

}
