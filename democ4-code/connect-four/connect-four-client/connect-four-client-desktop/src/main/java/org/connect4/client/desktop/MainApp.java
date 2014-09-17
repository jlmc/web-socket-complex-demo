package org.connect4.client.desktop;

import java.net.URI;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.log4j.Logger;
import org.connect4.client.desktop.screens.ScreensController;
import org.connect4.client.desktop.session.DesktopSessionManager;
import org.connect4.client.endpoint.Router;
import org.connect4.client.endpoint.clientendpoint.ConnectEndpoint;
import org.connect4.client.endpoint.clientendpoint.Connector;
import org.connect4.utils.properties.PropertiesUtil;
import org.controlsfx.dialog.Dialogs;

/**
 * The Class MainApp.
 */
@SuppressWarnings("unused")
public class MainApp extends Application {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MainApp.class);
    // LoggerFactory.getLogger(MainApp.class);

    /** The Constant SERVER_ENDPOINT_URL. */
    // private static final String SERVER_ENDPOINT_URL = "wss://localhost:8080/connect-four-server-webApp/endpoint";
    // private static final String SERVER_ENDPOINT_URL = "wss://192.168.1.3:8080/connect-four-server-webApp/endpoint";

    private static final String SERVER_ENDPOINT_URL_KEY = "SERVER_ENDPOINT_URL";

    /** The settings properties. */
    private final Properties settingsProperties = new Properties();

    /** The session manager. */
    private DesktopSessionManager sessionManager;

    /** The Constant STAKE_FILE. */
    private static final String STAKE_FILE = "/fxml/StackPaneScreen.fxml";

    /** The Constant LOGIN. */
    public static final String LOGIN = "login";

    /** The Constant LOGIN_FILE. */
    private static final String LOGIN_FILE = "/fxml/LoginScreen.fxml";

    /** The Constant REGISTER. */
    public static final String REGISTER = "register";

    /** The Constant REGISTER_FILE. */
    private static final String REGISTER_FILE = "/fxml/RegisterScreen.fxml";

    /** The Constant MAIN. */
    public static final String MAIN = "main";

    /** The Constant MAIN_FILE. */
    private static final String MAIN_FILE = "/fxml/ChatGeneralScreen2.fxml";

    /** The Constant PRIVATE. */
    public static final String PRIVATE = "private";

    /** The Constant PRIVATE_FILE. */
    private static final String PRIVATE_FILE = "/fxml/PrivateScreen.fxml";

    /** The Constant RANKING. */
    public static final String RANKING = "ranking";

    /** The Constant RANKING_FILE. */
    private static final String RANKING_FILE = "/fxml/RankingScreen.fxml";

    /*
     * (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(final Stage primaryStage) {

        try {
            initApp(primaryStage);
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return;
        }

        final ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(LOGIN, LOGIN_FILE);
        mainContainer.loadScreen(REGISTER, REGISTER_FILE);
        mainContainer.loadScreen(MAIN, MAIN_FILE);
        mainContainer.loadScreen(RANKING, RANKING_FILE);

        mainContainer.setScreen(LOGIN);

        /*
         * AnchorPane.setTopAnchor(n, 0.0);
         * AnchorPane.setRightAnchor(n, 0.0);
         * AnchorPane.setLeftAnchor(n, 0.0);
         * AnchorPane.setBottomAnchor(n, 0.0);
         */

        // final Group root = new Group();

        final VBox vg = new VBox(mainContainer);
        vg.setMaxHeight(Double.MAX_VALUE);

        // root.getChildren().addAll(mainContainer);
        final BorderPane root = new BorderPane();
        root.setCenter(mainContainer);

        // final Scene scene = new Scene(vg);
        final Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    /**
     * Inits the rooter.
     * @param primaryStage
     *            the primary stage
     * @throws Exception
     *             the exception
     */
    private void initApp(final Stage primaryStage) throws Exception {

        String urlPath = null;
        final Properties properties = PropertiesUtil.load("config.properties");
        if (properties.containsKey(SERVER_ENDPOINT_URL_KEY) && properties.get(SERVER_ENDPOINT_URL_KEY) != null) {
            urlPath = properties.getProperty(SERVER_ENDPOINT_URL_KEY);

        } else {

            Dialogs.create().owner(primaryStage).title("Error Dialog").masthead("Look, an Error Dialog")
            .message("Ooops, Can't Load Configuration file.").showError();
            throw new Exception("Can't Load Configuration file.");
        }

        final Router rotter = Router.getInstance();
        final Connector connector = new ConnectEndpoint();
        rotter.init(connector);

        try {
            rotter.connect(new URI(properties.getProperty(SERVER_ENDPOINT_URL_KEY)));
            // rotter.connect(new URI(SERVER_ENDPOINT_URL));
        } catch (final Exception e) {

            Dialogs.create().owner(primaryStage).title("Error Dialog").masthead("Look, an Error Dialog")
            .message("Ooops, can't connect with the server.").showError();

            throw e;
        }

        this.sessionManager = DesktopSessionManager.getInstance();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     * @param args
     *            the command line arguments
     */
    public static void main(final String[] args) {

        launch(args);

    }
}
