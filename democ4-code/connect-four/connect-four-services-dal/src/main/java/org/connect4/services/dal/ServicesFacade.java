package org.connect4.services.dal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.connect4.entities.AuthenticationStatusCode;
import org.connect4.entities.User;
import org.connect4.messages.CreateAcountResult;
import org.connect4.services.dal.daos.DAOFactory;
import org.connect4.services.dal.daos.UserDao;
import org.connect4.services.dal.entites.ModelCredentials;
import org.connect4.services.dal.entites.ModelUser;
import org.connect4.services.dal.exeptions.StoredServicesExeption;
import org.connect4.services.dal.services.AuthenticationException;
import org.connect4.services.dal.services.CreateUserExeption;
import org.connect4.services.dal.services.ServiceFactory;
import org.connect4.services.dal.services.UserService;
import org.connect4.utils.functions.Preconditions;

/**
 * The Class UserServices.
 */
public final class ServicesFacade {

    /** The factory. */
    private final DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);

    /** The user dao. */
    private final UserDao userDao = this.factory.getUserDao();

    /** The Constant USER_RESPOSITORY. */
    private static final List<ModelUser> USER_RESPOSITORY = new ArrayList<>(Arrays.asList(new ModelUser[] {
            new ModelUser(null, new ModelCredentials("catia", "demo"), "Catia Pires"),
            new ModelUser(null, new ModelCredentials("carlos", "demo"), "Carlos Nogueira"),
            new ModelUser(null, new ModelCredentials("cedric", "demo"), "Cedric Mickael"),
            new ModelUser(null, new ModelCredentials("diana", "demo"), "Diana Pinheiro"),
            new ModelUser(null, new ModelCredentials("filipe", "demo"), "Filipe Queiroes"),
            new ModelUser(null, new ModelCredentials("ricardo", "demo"), "Ricardo Cabetel"),
            new ModelUser(null, new ModelCredentials("Joao", "demo"), "Joao Costa") }));

    /**
     * Creates the user.
     * @return the user
     */
    protected ModelUser createUser() {

        ModelUser createdUser = null;
        this.userDao.beginTransaction();
        try {

            for (final ModelUser user : USER_RESPOSITORY) {
                createdUser = this.userDao.makePersistent(user);
            }

            this.userDao.commitTransaction();
        } catch (final Exception e) {
            this.userDao.rollbackTransaction();
        }
        return createdUser;

    }

    /**
     * Login.
     * @param user
     *            the user
     * @return the user
     * @throws AuthenticationException
     *             the authentication exception
     */
    public static User login(final User user) throws AuthenticationException {

        if (user != null) {

            final UserService userService = ServiceFactory.getUserService();

            User u;
            try {
                u = userService.getByUsername(user.getUsername());
            } catch (final StoredServicesExeption e) {
                throw new AuthenticationException(AuthenticationStatusCode.INTERNAL_ERROR, e.getMessage());
            }

            if (u == null) {
                throw new AuthenticationException(AuthenticationStatusCode.NONEXISTENT_USERNAME,
                        "The username does not exist in the system");
            }

            if (!String.valueOf(u.getPassword()).trim().equalsIgnoreCase(String.valueOf(user.getPassword()).trim())) {
                throw new AuthenticationException(AuthenticationStatusCode.WRONG_PASSWORD,
                        "The password is not correct for the given user");
            }
            return u;

        }

        return null;
    }

    /**
     * Creates the user.
     * @param user
     *            the user
     * @return the user
     * @throws CreateUserExeption
     *             the create user exeption
     */
    public static User createUser(final User user) throws CreateUserExeption {
        if (user != null) {

            Preconditions.checkArgument(user.getUsername() != null && !user.getUsername().trim().isEmpty(),
                    new CreateUserExeption(CreateAcountResult.MISSING_USERNAME, ""));

            Preconditions.checkArgument(user.getPassword() != null && !user.getPassword().trim().isEmpty(),
                    new CreateUserExeption(CreateAcountResult.MISSING_PASSWORD, ""));

            try {

                final UserService userService = ServiceFactory.getUserService();

                final List<User> savedusers = userService.saveUser(user);

                if (savedusers != null && !savedusers.isEmpty() && savedusers.get(0) != null) {
                    return savedusers.get(0);
                }

            } catch (final StoredServicesExeption e) {
                switch (e.getType()) {
                case USER_ALREADY_IN_USE:
                    throw new CreateUserExeption(CreateAcountResult.USER_ALREADY_IN_USE, "");
                default:
                    break;
                }

            }
        }

        throw new CreateUserExeption(CreateAcountResult.INTERNAL_ERROR, "");
    }
}
