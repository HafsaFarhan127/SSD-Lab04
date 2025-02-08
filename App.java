import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        //this.primaryStage = primaryStage;
        UserSelection login = new UserSelection(primaryStage);
        login.initializeComponents();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
