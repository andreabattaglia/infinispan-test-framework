package org.jboss.infinispan.tests.embeddedserver.test2;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class DealType.
 */
@ProtoMessage
@ProtoDoc("@Indexed")
public class DealType {

    /** The id. */
    private Long id;

    /** The ref. */
    private String ref;

    /** The name. */
    private String name;

    /**
     * Gets the id.
     *
     * @return the id
     */
    @ProtoField(number = 1, required = true)
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ref.
     *
     * @return the ref
     */
    @ProtoField(number = 2, required = true)
    public String getRef() {
        return ref;
    }

    /**
     * Sets the ref.
     *
     * @param ref
     *            the new ref
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @ProtoField(number = 3, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
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
        DealType other = (DealType) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DealType [");
        if (id != null) {
            builder.append("id=");
            builder.append(id);
            builder.append(", ");
        }
        if (ref != null) {
            builder.append("ref=");
            builder.append(ref);
            builder.append(", ");
        }
        if (name != null) {
            builder.append("name=");
            builder.append(name);
        }
        builder.append("]");
        return builder.toString();
    }

}
