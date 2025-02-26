package view;

import java.io.IOException;

import controller.ProfileOverviewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The main app is the entry point of this Application.
 * It executes the GUI and initializes the NLP operation
 * based on either user audio or pre-set file. 
 * @author Pulkit
 *
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    /**
     * Main method that initializes GUI and NLP.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image("view/logo.png"));
        this.primaryStage.setTitle("Scaminator");
        initRootLayout();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
               Platform.exit();
               System.exit(0);
            }
         });
        showProfileOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showProfileOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("ProfileOverview.fxml"));
            AnchorPane profileOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(profileOverview);
            
            // Give the controller access to the main app.
            ProfileOverviewController controller = loader.getController();
            controller.setMainApp(this);
            //this.dto = controller.getProfileDTO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
