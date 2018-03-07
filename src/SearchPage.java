import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class SearchPage implements Initializable {


    private HashTable ht;
    private String searchText;
    private static String searchFieldText = "Enter a business name here...";
    private static ArrayList<Photo> correctPhotos = new ArrayList<>();


    @FXML
    JFXTextField searchField;

    @FXML
    static JFXButton searchButton;


    public static JFXButton getSearchButton() {
        return searchButton;
    }

    private static Scene businessScene;


    public static Scene getBusinessScene() {
        return businessScene;
    }

    public static ArrayList<Photo> getCorrectPhotos() {
        return correctPhotos;
    }


    @FXML
    public void search(ActionEvent event) {
        ht = Main.getHt();

        searchText = searchField.getText();

        Business search = new Business();
        search.setName(searchText);
        System.out.println(searchText);
        int hash = ht.hashBusiness(search);
        Business correctBusiness = null;
        if(ht.getBusinesses()[hash].business.getName().equals(searchText)){
                if (ht.getPhotos()[hash].photo.getName().equals(searchText)){
                    correctPhotos.add(ht.getPhotos()[hash].photo);
                }
                else if(!ht.getBusinesses()[hash].business.getName().equals(searchText)){
                    int i =0;
                    HashTable.PhotoNode temp = ht.getPhotos()[hash];
                    while (temp.next != null){
                        i++;
                        System.out.println(i);
                        temp = temp.next;
                        System.out.println(temp.photo.getName());
                        if (temp.photo.getName().equals(searchText)){
                            correctPhotos.add(temp.photo);
                        }
                    }
                }
            try {

                if (!correctPhotos.isEmpty()) {
                    DisplayBusiness.setBusinessPhotoPath(correctPhotos.get(0).getPhoto_id() + ".jpg");
                } else {
                    DisplayBusiness.setBusinessPhotoPath("No_Image_Available.png");
                }
                DisplayBusiness.setRelated(ht.getManySimilar(ht.getBusinesses()[hash].business.getName()));
                DisplayBusiness.setBusinessName(ht.getBusinesses()[hash].business.getName());
                DisplayBusiness.setBusinessAddress(ht.getBusinesses()[hash].business.getAddress() + ", " + ht.getBusinesses()[hash].business.getCity() + " " + ht.getBusinesses()[hash].business.getState() + " " +  ht.getBusinesses()[hash].business.getPostal_code());
                DisplayBusiness.setRating((int) Math.round(ht.getBusinesses()[hash].business.getStars()));
                DisplayBusiness.setBusinessCategories(ht.getBusinesses()[hash].business.getCategories());
                Pane businessPane = FXMLLoader.load(getClass().getResource("display_business.fxml"));
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(businessPane,900,600);
                stage.setScene(scene);
                businessPane.requestFocus();
            }catch (IOException io){
                io.printStackTrace();
            }
        }
        else if(!ht.getBusinesses()[hash].business.getName().equals(searchText)){
            int i =0;
            boolean match = false;
            HashTable.BusinessNode temp = ht.getBusinesses()[hash];
            while (temp.next != null){
                i++;
                System.out.println(i);
                temp = temp.next;
                System.out.println(temp.business.getName());
                if (temp.business.getName().equals(searchText)){
                    match = true;
                    correctBusiness = temp.business;
                    System.out.println(correctBusiness.getName() + " Found!");
                    break;
                }
            }
            if (ht.getPhotos()[hash].photo.getName().equals(searchText)){
                correctPhotos.add(ht.getPhotos()[hash].photo);
            }
            HashTable.PhotoNode temp2 = ht.getPhotos()[hash];
            while (temp2.next != null){
                temp2 = temp2.next;
                System.out.println(temp2.photo.getName());
                if (temp2.photo.getName().equals(searchText)){
                    correctPhotos.add(temp2.photo);
                }
            }
            if(match) {
                try {
                    if (!correctPhotos.isEmpty()) {
                        DisplayBusiness.setBusinessPhotoPath(correctPhotos.get(0).getPhoto_id() + ".jpg");
                    } else {
                        DisplayBusiness.setBusinessPhotoPath("No_Image_Available.png");
                    }
                    DisplayBusiness.setRelated(ht.getManySimilar(correctBusiness.getName()));
                    DisplayBusiness.setBusinessName(correctBusiness.getName());
                    DisplayBusiness.setBusinessAddress(correctBusiness.getAddress() + ", " + correctBusiness.getCity() + " " + correctBusiness.getState() + " " + correctBusiness.getPostal_code());
                    DisplayBusiness.setRating((int) Math.round(correctBusiness.getStars()));
                    DisplayBusiness.setBusinessCategories(correctBusiness.getCategories());
                    Pane businessPane = FXMLLoader.load(getClass().getResource("display_business.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(businessPane, 1000, 600);
                    stage.setScene(scene);
                    businessPane.requestFocus();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
            else {
                try {
                    SearchPage.searchFieldText = "Business not Found, please try again";
                    Pane businessPane = FXMLLoader.load(getClass().getResource("search_page.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(businessPane, 1000, 600);
                    stage.setScene(scene);
                    businessPane.requestFocus();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }

    }

    /*public Photo findPhotoMatch(){

    }*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchField.setPromptText(searchFieldText);
    }
}
