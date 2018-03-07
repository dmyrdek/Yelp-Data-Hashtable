import java.io.IOException;
import java.util.List;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.fxml.FXMLLoader;


public class Main extends Application  {
    static private Stage stage;
    private DatabaseParser dp = new DatabaseParser();
    private List<Business> businesses;
    private List<Photo> photos;
    private static HashTable ht = new HashTable(4096);
    private ResourceLoadingTask task = new ResourceLoadingTask();

    public static HashTable getHt() {
        return ht;
    }



    @Override
    public void start(Stage stage) throws IOException{
        //Group root = new Group();
        //Group root = FXMLLoader.load(getClass().getResource("loading_screen.fxml"));

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 50, 50, 50));
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.setResizable(false);
        Pane root = FXMLLoader.load(getClass().getResource("loading_screen.fxml"));
        stage.setTitle("Yelp Data Search");
        FontAwesomeIconView spinner = new FontAwesomeIconView(FontAwesomeIcon.SPINNER);
        spinner.setSize("50");
        spinner.setFill(Paint.valueOf("#d63031"));

        RotateTransition spin = new RotateTransition(Duration.seconds(1), spinner);
        spin.setByAngle(360);
        spin.setInterpolator(Interpolator.LINEAR);
        spin.setCycleCount(Animation.INDEFINITE);
        spin.play();

        root.getChildren().add(spinner);


        stage.setScene(new Scene(root, 1000, 600));

        stage.show();

        Thread t = new Thread(task);
        Pane searchPane = FXMLLoader.load(getClass().getResource("search_page.fxml"));

        task.setOnSucceeded(e -> {
            Scene search=  new Scene(searchPane,1000,600);
            stage.setScene(search);
            searchPane.requestFocus();
        });
        t.start();

        stage.show();
        root.requestFocus();
    }


    public class ResourceLoadingTask extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            businesses = dp.businessesParser();
            photos = dp.photosParser();
            ht = ht.loadBoth(businesses,photos);
            System.out.println("Data Loaded");
            return null;
        }
    }

    public static void main(String[] args) throws IOException, Exception {
        launch(args);
    }
}
