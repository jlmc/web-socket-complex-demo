package org.connect4.services.dal.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.connect4.entities.User;
import org.connect4.services.dal.exeptions.StoredServicesExeption;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The Class UserServicesTest.
 */
public class UserServicesTest {

    /** The Constant USER_RESPOSITORY. */
    private static final List<User> USER_RESPOSITORY = new ArrayList<>(Arrays.asList(new User[] {
            new User(0, "catia", "Catia Pires", "demo"), new User(0, "carlos", "Carlos Nogueira", "demo"),
            new User(0, "cedric", "Cedric Mickael", "demo"), new User(0, "diana", "Diana Pinheiro", "demo"),
            new User(0, "filipe", "Filipe Queiroes", "demo"), new User(0, "ricardo", "Ricardo Cabetel", "demo"),
            new User(0, "Joao", "Joao Costa", "demo") }));

    /** The Constant userServices. */
    private static final UserService userServices = ServiceFactory.getUserService();

    /**
     * Test create user.
     */
    @SuppressWarnings("static-method")
    @Test
    @Ignore
    public void testCreateUser() {

        List<User> results = null;
        try {

            final User[] users = USER_RESPOSITORY.toArray(new User[USER_RESPOSITORY.size()]);

            results = userServices.saveUser(users);
        } catch (final StoredServicesExeption e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertNotNull(results);
    }

    /**
     * Test load by existing username.
     * @throws StoredServicesExeption
     *             the stored services exeption
     */
    @SuppressWarnings("static-method")
    @Test
    @Ignore
    public void testLoadByExistingUsername() throws StoredServicesExeption {

        final User user = userServices.getByUsername("joao");

        Assert.assertNotNull(user);

    }

    /**
     * Test update existing user.
     * @throws StoredServicesExeption
     *             the stored services exeption
     */
    @SuppressWarnings("static-method")
    @Test
    @Ignore
    public void testUpdateExistingUser() throws StoredServicesExeption {
        final User user = new User(7L, "Joao", "Joao Costa", "demo");

        user.setTotalGames(100);
        user.setTotalGamesWon(99);
        user.setDisplayName("Joao Luis Costa");

        final List<User> users = userServices.updateUser(user);

        Assert.assertNotNull(users);

    }
}
