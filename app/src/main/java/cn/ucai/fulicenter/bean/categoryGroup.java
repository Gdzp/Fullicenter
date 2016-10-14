package cn.ucai.fulicenter.bean;

/**
 * Created by Think on 2016/10/14.
 */
public class categoryGroup {


    /**
     * id : 344
     * name : 最IN
     * imageUrl : muying/2.jpg
     */

    private int id;
    private String name;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public categoryGroup(int id, String imageUrl, String name) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public categoryGroup(int id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "categoryGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
