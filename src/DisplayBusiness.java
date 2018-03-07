import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DisplayBusiness implements Initializable {
    @FXML
    JFXTextField searchField;

    @FXML
    static JFXButton searchButton;

    @FXML
    Label businessNameLabel;

    @FXML
    TextField address;

    @FXML
    AnchorPane star1;

    @FXML
    AnchorPane star2;

    @FXML
    AnchorPane star3;

    @FXML
    AnchorPane star4;

    @FXML
    AnchorPane star5;

    @FXML
    Label related1;

    @FXML
    Label related2;

    @FXML
    Label related3;

    @FXML
    Label related4;

    @FXML
    Label related5;

    @FXML
    TextArea categories;

    @FXML
    ImageView businessPhoto;

    @FXML
    ImageView relatedPic1;

    @FXML
    ImageView relatedPic2;

    @FXML
    ImageView relatedPic3;

    @FXML
    ImageView relatedPic4;

    @FXML
    ImageView relatedPic5;

    private ArrayList<Photo> businessPhotos = new ArrayList<>();

    private int currentPhoto;

    private HashTable ht;
    private String searchText;
    private static String searchFieldText = "Enter a business name here...";
    private static ArrayList<Photo> correctPhotos = new ArrayList<>();
    private static boolean fromDisplayBusiness = false;
    private static String businessName;
    private static ArrayList<Business> related;
    private static String businessAddress, businessPhotoPath, relatedPhotoPath1, relatedPhotoPath2, relatedPhotoPath3, relatedPhotoPath4, relatedPhotoPath5;
    private static int rating;
    private static String[] businessCategories;
    private Image businessPhotoFile, relatedPhotoFile1, relatedPhotoFile2, relatedPhotoFile3, relatedPhotoFile4, relatedPhotoFile5;


    public static void setBusinessPhotoPath(String businessPhotoPath) {
        DisplayBusiness.businessPhotoPath = businessPhotoPath;
    }

    public static void setBusinessName(String b) {
        businessName = b;
    }

    public static void setBusinessAddress(String businessAddress) {
        DisplayBusiness.businessAddress = businessAddress;
    }

    public static void setRating(int rating) {
        DisplayBusiness.rating = rating;
    }

    public static void setBusinessCategories(String[] businessCategories) {
        DisplayBusiness.businessCategories = businessCategories;
    }

    public static void setRelated(ArrayList<Business> related) {
        DisplayBusiness.related = related;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ht = Main.getHt();
        currentPhoto = 0;
        businessNameLabel.setText(businessName);
        address.setText(businessAddress);
        if (rating == 1) {
            star1.setVisible(true);
        } else if (rating == 2) {
            star1.setVisible(true);
            star2.setVisible(true);
        } else if (rating == 3) {
            star1.setVisible(true);
            star2.setVisible(true);
            star3.setVisible(true);
        } else if (rating == 4) {
            star1.setVisible(true);
            star2.setVisible(true);
            star3.setVisible(true);
            star4.setVisible(true);
        } else if (rating == 5) {
            star1.setVisible(true);
            star2.setVisible(true);
            star3.setVisible(true);
            star4.setVisible(true);
            star5.setVisible(true);
        }
        for (int i = 0; i < businessCategories.length; i++) {
            if (i == 0) {
                categories.appendText(businessCategories[i]);
                if (i == businessCategories.length - 2) {
                    categories.appendText(" and ");
                } else if (i != businessCategories.length - 1) {
                    categories.appendText(", ");
                }
            } else {
                categories.appendText(businessCategories[i]);
                if (i == businessCategories.length - 2) {
                    categories.appendText(" and ");
                } else if (i != businessCategories.length - 1) {
                    categories.appendText(", ");
                }
            }
        }

        businessPhotoFile = new Image(getClass().getResource(businessPhotoPath).toExternalForm());
        businessPhoto.setImage(businessPhotoFile);
        centerImage();
        related1.setText(related.get(0).getName());
        related2.setText(related.get(1).getName());
        related3.setText(related.get(2).getName());
        related4.setText(related.get(3).getName());
        related5.setText(related.get(4).getName());

        int hashRelated1 = ht.hashBusiness(related.get(0)), hashRelated2 = ht.hashBusiness(related.get(1)), hashRelated3 = ht.hashBusiness(related.get(2)), hashRelated4 = ht.hashBusiness(related.get(3)), hashRelated5 = ht.hashBusiness(related.get(4));
        Photo related1Photo = new Photo(), related2Photo = new Photo(), related3Photo = new Photo(), related4Photo = new Photo(), related5Photo = new Photo();

        boolean firstPhoto = false;
        if (ht.getPhotos()[hashRelated1].photo.getName().equals(related.get(0).getName())) {
            related1Photo = ht.getPhotos()[hashRelated1].photo;
            firstPhoto = true;
        }
        HashTable.PhotoNode temp2 = ht.getPhotos()[hashRelated1];
        if (!firstPhoto) {
            while (temp2.next != null) {
                temp2 = temp2.next;
                if (temp2.photo.getName().equals(related.get(0).getName())) {
                    related1Photo = ht.getPhotos()[hashRelated1].photo;
                    break;
                }
            }
        }

        firstPhoto = false;
        if (ht.getPhotos()[hashRelated2].photo.getName().equals(related.get(1).getName())) {
            related2Photo = ht.getPhotos()[hashRelated2].photo;
            firstPhoto = true;
        }
        temp2 = ht.getPhotos()[hashRelated2];
        if (!firstPhoto) {
            while (temp2.next != null) {
                temp2 = temp2.next;
                if (temp2.photo.getName().equals(related.get(1).getName())) {
                    related2Photo = ht.getPhotos()[hashRelated2].photo;
                    break;
                }
            }
        }

        firstPhoto = false;
        if (ht.getPhotos()[hashRelated3].photo.getName().equals(related.get(2).getName())) {
            related3Photo = ht.getPhotos()[hashRelated3].photo;
            firstPhoto = true;
        }
        temp2 = ht.getPhotos()[hashRelated3];
        if (!firstPhoto) {
            while (temp2.next != null) {
                temp2 = temp2.next;
                if (temp2.photo.getName().equals(related.get(2).getName())) {
                    related3Photo = ht.getPhotos()[hashRelated3].photo;
                    break;
                }
            }
        }

        firstPhoto = false;
        if (ht.getPhotos()[hashRelated4].photo.getName().equals(related.get(3).getName())) {
            related4Photo = ht.getPhotos()[hashRelated4].photo;
            firstPhoto = true;
        }
        temp2 = ht.getPhotos()[hashRelated4];
        if (!firstPhoto) {
            while (temp2.next != null) {
                temp2 = temp2.next;
                if (temp2.photo.getName().equals(related.get(3).getName())) {
                    related4Photo = ht.getPhotos()[hashRelated4].photo;
                    break;
                }
            }
        }

        firstPhoto = false;
        if (ht.getPhotos()[hashRelated5].photo.getName().equals(related.get(4).getName())) {
            related5Photo = ht.getPhotos()[hashRelated5].photo;
            firstPhoto = true;
        }
        temp2 = ht.getPhotos()[hashRelated5];
        if (!firstPhoto) {
            while (temp2.next != null) {
                temp2 = temp2.next;
                if (temp2.photo.getName().equals(related.get(4).getName())) {
                    related5Photo = ht.getPhotos()[hashRelated5].photo;
                    break;
                }
            }
        }


        if (related1Photo.getPhoto_id() != null) {
            relatedPic1.setImage(new Image(getClass().getResource(related1Photo.getPhoto_id() + ".jpg").toExternalForm()));
        } else{
            relatedPic1.setImage(new Image("No_Image_Available.png"));
        }

        if (related2Photo.getPhoto_id() != null){
            relatedPic2.setImage(new Image(getClass().getResource(related2Photo.getPhoto_id() + ".jpg").toExternalForm()));
        } else{
            relatedPic2.setImage(new Image("No_Image_Available.png"));
        }

        if (related3Photo.getPhoto_id() != null){
            relatedPic3.setImage(new Image(getClass().getResource(related3Photo.getPhoto_id() + ".jpg").toExternalForm()));
        } else{
            relatedPic3.setImage(new Image("No_Image_Available.png"));
        }

        if (related4Photo.getPhoto_id() != null){
            relatedPic4.setImage(new Image(getClass().getResource(related4Photo.getPhoto_id() + ".jpg").toExternalForm()));
        } else{
            relatedPic4.setImage(new Image("No_Image_Available.png"));
        }

        if (related5Photo.getPhoto_id() != null){
            relatedPic5.setImage(new Image(getClass().getResource(related5Photo.getPhoto_id() + ".jpg").toExternalForm()));
        } else{
            relatedPic5.setImage(new Image("No_Image_Available.png"));
        }
    }

    public void centerImage() {
        Image img = businessPhoto.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = businessPhoto.getFitWidth() / img.getWidth();
            double ratioY = businessPhoto.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            businessPhoto.setX((businessPhoto.getFitWidth() - w) / 2);
            businessPhoto.setY((businessPhoto.getFitHeight() - h) / 2);
        }
    }

    public void nextImage(){
            businessPhotos = new ArrayList<>();
            if (!fromDisplayBusiness) {
                businessPhotos = SearchPage.getCorrectPhotos();
                if (businessPhotos.size() > 1) {
                    if (currentPhoto != businessPhotos.size() - 1) {
                        ++currentPhoto;
                        businessPhoto.setImage(new Image(getClass().getResource(businessPhotos.get(currentPhoto).getPhoto_id() + ".jpg").toExternalForm()));
                        centerImage();
                    }
                }
            } else {
                businessPhotos = correctPhotos;
                if (businessPhotos.size() > 1) {
                    if (currentPhoto != businessPhotos.size() - 1) {
                        ++currentPhoto;
                        businessPhoto.setImage(new Image(getClass().getResource(businessPhotos.get(currentPhoto).getPhoto_id() + ".jpg").toExternalForm()));
                        centerImage();
                    }
                }
            }
    }

    public void prevImage(){
            businessPhotos = new ArrayList<>();
            if (!fromDisplayBusiness) {
                businessPhotos = SearchPage.getCorrectPhotos();
                if (businessPhotos.size() > 1) {
                    if (currentPhoto != 0) {
                        --currentPhoto;
                        businessPhoto.setImage(new Image(getClass().getResource(businessPhotos.get(currentPhoto).getPhoto_id() + ".jpg").toExternalForm()));
                        centerImage();
                    }
                }
            } else {
                businessPhotos = correctPhotos;
                if (businessPhotos.size() > 1) {
                    if (currentPhoto != 0) {
                        --currentPhoto;
                        businessPhoto.setImage(new Image(getClass().getResource(businessPhotos.get(currentPhoto).getPhoto_id() + ".jpg").toExternalForm()));
                        centerImage();
                    }
                }
            }
    }

    @FXML
    private void search(ActionEvent event) throws IOException {
        ht = Main.getHt();
        //Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();


        searchText = searchField.getText();
        DisplayBusiness.correctPhotos = new ArrayList<>();

        Business search = new Business();
        search.setName(searchText);
        System.out.println(searchText);
        int hash = ht.hashBusiness(search);
        Business correctBusiness = null;
        if(ht.getBusinesses()[hash].business.getName().equals(searchText)){
            if (ht.getPhotos()[hash].photo.getName().equals(searchText)){
                DisplayBusiness.correctPhotos.add(ht.getPhotos()[hash].photo);
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
                        DisplayBusiness.correctPhotos.add(temp.photo);
                    }
                }
            }
            try {
                //System.out.println(correctPhotos.get(0).getPhoto_id());
                if (!correctPhotos.isEmpty()) {
                    DisplayBusiness.setBusinessPhotoPath(DisplayBusiness.correctPhotos.get(0).getPhoto_id() + ".jpg");
                } else {
                    DisplayBusiness.setBusinessPhotoPath("No_Image_Available.png");
                }
                DisplayBusiness.fromDisplayBusiness = true;
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
                DisplayBusiness.correctPhotos.add(ht.getPhotos()[hash].photo);
            }
            HashTable.PhotoNode temp2 = ht.getPhotos()[hash];
            while (temp2.next != null){
                temp2 = temp2.next;
                System.out.println(temp2.photo.getName());
                if (temp2.photo.getName().equals(searchText)){
                    DisplayBusiness.correctPhotos.add(temp2.photo);
                }
            }
            if(match) {
                try {
                    if (!correctPhotos.isEmpty()) {
                        DisplayBusiness.setBusinessPhotoPath(DisplayBusiness.correctPhotos.get(0).getPhoto_id() + ".jpg");
                    } else {
                        DisplayBusiness.setBusinessPhotoPath("No_Image_Available.png");
                    }
                    DisplayBusiness.fromDisplayBusiness = true;
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
                    DisplayBusiness.searchFieldText = "Business not Found, please try again";
                    Pane businessPane = FXMLLoader.load(getClass().getResource("display_business.fxml"));
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

    @FXML
    private void openRelated1(MouseEvent event){
        ht = Main.getHt();
        int relatedHash = ht.hashBusiness(related.get(0));
        Business correctBusiness = related.get(0);
        ArrayList<Photo> newPhotos = new ArrayList<>();

        if (ht.getPhotos()[relatedHash].photo.getName().equals(related.get(0).getName())){
            newPhotos.add(ht.getPhotos()[relatedHash].photo);
        }
        HashTable.PhotoNode temp2 = ht.getPhotos()[relatedHash];
        while (temp2.next != null){
            temp2 = temp2.next;
            System.out.println(temp2.photo.getName());
            if (temp2.photo.getName().equals(related.get(0).getName())){
                newPhotos.add(temp2.photo);
            }
        }
        if (!newPhotos.isEmpty()) {
            DisplayBusiness.setBusinessPhotoPath(newPhotos.get(0).getPhoto_id() + ".jpg");
        } else {
            DisplayBusiness.setBusinessPhotoPath("No_Image_Available.png");
        }

        correctPhotos = newPhotos;
        newBusinessScene(correctBusiness, event);
    }

    @FXML
    private void openRelated2(MouseEvent event){
        ht = Main.getHt();
        int relatedHash = ht.hashBusiness(related.get(1));
        Business correctBusiness = related.get(1);
        ArrayList<Photo> newPhotos = new ArrayList<>();

        if (ht.getPhotos()[relatedHash].photo.getName().equals(related.get(1).getName())){
            newPhotos.add(ht.getPhotos()[relatedHash].photo);
        }
        HashTable.PhotoNode temp2 = ht.getPhotos()[relatedHash];
        while (temp2.next != null){
            temp2 = temp2.next;
            System.out.println(temp2.photo.getName());
            if (temp2.photo.getName().equals(related.get(1).getName())){
                newPhotos.add(temp2.photo);
            }
        }
        if (!newPhotos.isEmpty()) {
            DisplayBusiness.setBusinessPhotoPath(newPhotos.get(0).getPhoto_id() + ".jpg");
        } else {
            DisplayBusiness.setBusinessPhotoPath("No_Image_Available.png");
        }

        correctPhotos = newPhotos;
        newBusinessScene(correctBusiness, event);
    }

    @FXML
    private void openRelated3(MouseEvent event){
        ht = Main.getHt();
        int relatedHash = ht.hashBusiness(related.get(2));
        Business correctBusiness = related.get(2);
        ArrayList<Photo> newPhotos = new ArrayList<>();

        if (ht.getPhotos()[relatedHash].photo.getName().equals(related.get(2).getName())){
            newPhotos.add(ht.getPhotos()[relatedHash].photo);
        }
        HashTable.PhotoNode temp2 = ht.getPhotos()[relatedHash];
        while (temp2.next != null){
            temp2 = temp2.next;
            System.out.println(temp2.photo.getName());
            if (temp2.photo.getName().equals(related.get(2).getName())){
                newPhotos.add(temp2.photo);
            }
        }
        if (!correctPhotos.isEmpty()) {
            DisplayBusiness.setBusinessPhotoPath(newPhotos.get(0).getPhoto_id() + ".jpg");
        } else {
            DisplayBusiness.setBusinessPhotoPath("No_Image_Available.png");
        }

        newPhotos = newPhotos;
        newBusinessScene(correctBusiness, event);
    }

    @FXML
    private void openRelated4(MouseEvent event){
        ht = Main.getHt();
        int relatedHash = ht.hashBusiness(related.get(3));
        Business correctBusiness = related.get(3);
        ArrayList<Photo> newPhotos = new ArrayList<>();

        if (ht.getPhotos()[relatedHash].photo.getName().equals(related.get(3).getName())){
            newPhotos.add(ht.getPhotos()[relatedHash].photo);
        }
        HashTable.PhotoNode temp2 = ht.getPhotos()[relatedHash];
        while (temp2.next != null){
            temp2 = temp2.next;
            System.out.println(temp2.photo.getName());
            if (temp2.photo.getName().equals(related.get(3).getName())){
                newPhotos.add(temp2.photo);
            }
        }
        if (!newPhotos.isEmpty()) {
            DisplayBusiness.setBusinessPhotoPath(newPhotos.get(0).getPhoto_id() + ".jpg");
        } else {
            DisplayBusiness.setBusinessPhotoPath("No_Image_Available.png");
        }

        correctPhotos = newPhotos;
        newBusinessScene(correctBusiness, event);
    }

    @FXML
    private void openRelated5(MouseEvent event){
        ht = Main.getHt();
        int relatedHash = ht.hashBusiness(related.get(4));
        Business correctBusiness = related.get(4);
        ArrayList<Photo> newPhotos = new ArrayList<>();

        if (ht.getPhotos()[relatedHash].photo.getName().equals(related.get(4).getName())){
            newPhotos.add(ht.getPhotos()[relatedHash].photo);
        }
        HashTable.PhotoNode temp2 = ht.getPhotos()[relatedHash];
        while (temp2.next != null){
            temp2 = temp2.next;
            System.out.println(temp2.photo.getName());
            if (temp2.photo.getName().equals(related.get(4).getName())){
                newPhotos.add(temp2.photo);
            }
        }
        if (!newPhotos.isEmpty()) {
            DisplayBusiness.setBusinessPhotoPath(newPhotos.get(0).getPhoto_id() + ".jpg");
        } else {
            DisplayBusiness.setBusinessPhotoPath("No_Image_Available.png");
        }
        correctPhotos = newPhotos;
        newBusinessScene(correctBusiness, event);
    }

    private void newBusinessScene(Business correctBusiness, MouseEvent event){
        try {
            DisplayBusiness.fromDisplayBusiness = true;
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
}
