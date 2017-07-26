/**
 * 
 */
package org.jboss.infinispan.tests.embeddedserver.test2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class Grade.
 *
 * @author Andrea Battaglia (Red Hat)
 */
@ProtoMessage
@ProtoDoc("@Indexed")
public class Grade {

    /** The id. */
    private Long id;

    /** The ref. */
    private String ref;

    /** The name. */
    private String name;

    /** The forwards grades. */
    private Set<ForwardsGrade> forwardsGrades;

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
    @ProtoDoc("@IndexedField(index = true, store = true)")
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
    @ProtoDoc("@IndexedField(index = true, store = true)")
    @ProtoField(number = 3, required = true)
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

    /**
     * Gets the forwards grades.
     *
     * @return the forwards grades
     */
    @ProtoField(number = 4, required = false, collectionImplementation=HashSet.class)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public Set<ForwardsGrade> getForwardsGrades() {
        return forwardsGrades;
    }

    /**
     * Sets the forwards grades.
     *
     * @param forwardsGrades
     *            the new forwards grades
     */
    public void setForwardsGrades(Set<ForwardsGrade> forwardsGrades) {
        this.forwardsGrades = forwardsGrades;
    }

    /** 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Grade other = (Grade) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Grade [");
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
            builder.append(", ");
        }
        if (forwardsGrades != null) {
            builder.append("forwardsGrades=");
            builder.append(forwardsGrades);
        }
        builder.append("]");
        return builder.toString();
    }

}
