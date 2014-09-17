package org.connect4.services.dal.exeptions;

/**
 * The Class StoredServicesExeption.
 */
public class StoredServicesExeption extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The source object. */
    private final Object sourceObject;

    /**
     * Gets the source object.
     * @return the source object
     */
    public Object getSourceObject() {
        return this.sourceObject;
    }

    /**
     * Gets the type.
     * @return the type
     */
    public StoredServicesExeptionType getType() {
        return this.type;
    }

    /** The type. */
    private final StoredServicesExeptionType type;

    /**
     * Instantiates a new stored services exeption.
     * @param source
     *            the source
     * @param type
     *            the type
     */
    public StoredServicesExeption(final Object source, final StoredServicesExeptionType type) {
        super();
        this.sourceObject = source;
        this.type = type;
    }

    /**
     * The Enum StoredServicesExeptionType.
     */
    public enum StoredServicesExeptionType {

        /** The user already in use. */
        USER_ALREADY_IN_USE,

        /** The unhandled error. */
        UNHANDLED_ERROR
    }
}
