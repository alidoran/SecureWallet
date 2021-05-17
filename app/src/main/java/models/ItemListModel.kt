package models;

import java.io.Serializable;

public class ItemListModel implements Serializable{
    private Long Code;
    private String Name;
    private String Id = "";
    private String ImgAddress;

    public Long getCode() {
        return Code;
    }

    public void setCode(Long code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getId() {
        return Id;
    }

    public String getImgAddress() {
        return ImgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.ImgAddress = imgAddress;
    }
}
