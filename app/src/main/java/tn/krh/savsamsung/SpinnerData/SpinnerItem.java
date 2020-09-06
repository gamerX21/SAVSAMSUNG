package tn.krh.savsamsung.SpinnerData;

public class SpinnerItem {
    String name;
    int imgId;

    public SpinnerItem(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }
    public SpinnerItem(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
