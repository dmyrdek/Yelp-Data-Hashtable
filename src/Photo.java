import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("caption")
    private String caption;

    @SerializedName("photo_id")
    private String photo_id;

    @SerializedName("business_id")
    private String business_id;

    @SerializedName("label")
    private String label;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public String getCaption() {
        return caption;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public String getLabel() {
        return label;
    }
}

