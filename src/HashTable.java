import java.util.ArrayList;
import java.util.List;

public class HashTable {

    //Creates the nodes for the business linked list
    static final class BusinessNode{
        Business business;
        BusinessNode next;

        BusinessNode(Business b){
            business = b;
        }

    }

    //Creates the nodes for the photo linked list
    static final class PhotoNode{
        Photo photo;
        PhotoNode next;

        PhotoNode(Photo p){
            photo = p;
        }
    }

    //Creates arrays of linkedlist for seperate hash tables
    private BusinessNode businesses[];
    private PhotoNode photos[];

    public BusinessNode[] getBusinesses() {
        return businesses;
    }

    public PhotoNode[] getPhotos() {
        return photos;
    }

    //size of the hashtable
    private int size;

    public HashTable(int size) {
        this.size = size;
        businesses = new BusinessNode[size];
        photos = new PhotoNode[size];
    }


    //hashes the business name in a business object
    public int hashBusiness(Business b){
        int hash = 7;
        char[] chars = b.getName().toCharArray();
        for (int i =0; i< b.getName().length(); i++) {
            hash = hash*137 + chars[(i)];
        }

        return Math.abs(hash) & (size-1);
    }

    //put's a business object into the hash inbex of the hashtable
    public void putBusiness(Business b){
        int hash = this.hashBusiness(b);
        BusinessNode newNode = new BusinessNode(b);
        if (businesses[hash] == null){
            businesses[hash] = newNode;
            businesses[hash].next = null;
        } else{
            newNode.next = businesses[hash];
            businesses[hash] = newNode;
        }
    }

    //loads the list of business objects created by gson
    public void loadBusiness(List<Business> businesses){
        int i = 0;
        int j = 0;
        for (Business b : businesses){
            i++;
            if ((float)(i+1)/(float)this.businesses.length <= 0.66) {
                this.putBusiness(b);
                //System.out.println(i + "   " + b.toString() + "loaded");
            } else {
                j++;
                this.resizeBusiness();
                this.putBusiness(b);
                //System.out.println(i + "   " + "resized and " + b.toString() + "loaded");
            }
        }
        //System.out.println(j);
    }

    //checks if a certain business exists
    public boolean containsBusiness(Business b){
        int hash = this.hashBusiness(b);
        String businessName = b.getName();

        if (this.businesses[hash] == null){
            return false;
        }
        for (int i = 0; i < this.businesses.length; i++){
            if (this.businesses[i] != null) {
                if (this.businesses[i].business.getName().equals(businessName)) {
                    return true;
                }
                BusinessNode temp = this.businesses[i];
                while (temp.next != null) {
                    temp = temp.next;
                    if (temp.business.getName().equals(businessName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //resizes the business hashtable by doubling
    public void resizeBusiness(){
        int size = this.businesses.length;
        BusinessNode[] resized = new BusinessNode[size *2];

        for (int i = 0; i < this.businesses.length; i++){
            BusinessNode list = businesses[i];
            while (list != null) {
                BusinessNode next = list.next;
                int h = this.hashBusiness(list.business);
                list.next = resized[i];
                resized[i] = list;
                list = next;
            }
        }
        businesses = resized;
    }


//----------------------------------------------------------------------------------------------------------------------

    //hashes the business name in a photo object
    public int hashPhoto(Photo p){
        int hash = 7;
        char[] chars = p.getName().toCharArray();
        for (int i =0; i< p.getName().length(); i++) {
            hash = hash*137 + chars[(i)];
        }
        return Math.abs(hash) & (size-1);
    }

    //put's a photo object into the hash inbex of the hashtable
    public void putPhoto(Photo p){
        int hash = this.hashPhoto(p);
        PhotoNode newNode = new PhotoNode(p);
        if (photos[hash] == null){
            photos[hash] = newNode;
            photos[hash].next = null;
        } else{
            newNode.next = photos[hash];
            photos[hash] = newNode;
        }
    }

    //loads the list of photos objects created by gson
    public void loadPhotos(List<Photo> photos){
        int i = 0;
        int j = 0;
        for (Photo p : photos){
            i++;
            if ((float)(i+1)/(float)this.photos.length <= 0.5) {
                this.putPhoto(p);
                System.out.println(i + "   " + p.toString() + "loaded");
            } else {
                j++;
                this.resizePhotos();
                this.putPhoto(p);
                System.out.println(i + "   " + "resized and " + p.toString() + "loaded");
            }
        }
        System.out.println(j);
    }

    //resizes the photo hashtable by doubling
    public void resizePhotos(){
        int size = this.photos.length;
        PhotoNode resized[] = new PhotoNode[size * 2];

        for (int i = 0; i < this.photos.length; i++) {
            PhotoNode list = photos[i];
            while (list != null) {
                PhotoNode next = list.next;
                int h = this.hashPhoto(list.photo);
                list.next = resized[i];
                resized[i] = list;
                list = next;
            }
        }
        photos = resized;
    }

    //loads both hashtables from gson
    public HashTable loadBoth(List<Business> b, List<Photo> p){
        loadBusiness(b);
        loadPhotos(p);
        return this;
    }

    //gets a list of most similar businesses by category matches and the manhattan algorithm
    public ArrayList<Business> getManySimilar(String name){
        Business b = new Business();
        b.setName(name);
        if (!this.containsBusiness(b)) {
            System.out.println("This element does not exist");
            return null;
        } else {
            for (int i = 0; i < this.businesses.length; i++){
                if (this.businesses[i]!=null) {
                    if (this.businesses[i].business.getName().equals(name)){
                        b = this.businesses[i].business;
                        System.out.println(b.getName() + " Found!");
                        break;
                    }
                    BusinessNode temp = this.businesses[i];
                    while (temp.next != null) {
                        temp = temp.next;
                        if (temp.business.getName().equals(name)){
                            b = temp.business;
                            System.out.println(b.getName() + " Found!");
                            break;
                        }
                    }
                }
            }
            ArrayList <Business> mostSimilarBusinesses = new ArrayList<>();
            for (int j = 0; j < this.businesses.length; j++){
                if (this.businesses[j]!=null) {
                    if (!(this.businesses[j].business.getName().equals(b.getName()))){
                        double similarity = this.businesses[j].business.getSimilarity(b);
                        if (similarity < 0.005) {
                            mostSimilarBusinesses.add(this.businesses[j].business);
                        }
                    }
                    BusinessNode temp = this.businesses[j];
                    while (temp.next != null) {
                        temp = temp.next;
                        if (!(temp.business.getName().equals(b.getName()))){
                            double similarity = temp.business.getSimilarity(b);
                            if (similarity < 0.005) {
                                mostSimilarBusinesses.add(temp.business);
                            }
                        }
                    }
                }
            }
            return mostSimilarBusinesses;
        }
    }
}

