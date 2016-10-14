package cn.ucai.fulicenter.bean;

/**
 * Created by Think on 2016/10/14.
 */
public class boutique {



    private int id;
    private String title;
    private String description;
    private String name;
    private String imageurl;

    public boutique(int id, String imageurl, String description, String title, String name) {
        this.id = id;
        this.imageurl = imageurl;
        this.description = description;
        this.title = title;
        this.name = name;
    }

    public boutique(int id) {

        this.id = id;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public String toString() {
        return "boutique{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }
}
