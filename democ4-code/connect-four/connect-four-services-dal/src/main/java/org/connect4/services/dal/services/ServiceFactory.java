package org.connect4.services.dal.services;


/**
 * A factory for creating Service objects.
 */
public final class ServiceFactory {

    /** The Constant USERSERVICE_CLASS. */
    public static final Class<?> USERSERVICE_CLASS = org.connect4.services.dal.services.impl.UserServiceImpl.class;

    /**
     * Gets the user service.
     * @return the user service
     */
    public static UserService getUserService() {
        try {
            return (UserService) USERSERVICE_CLASS.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Couldn't create UserService: ");
        }
    }

    private ServiceFactory() {
        super();
    }
}
