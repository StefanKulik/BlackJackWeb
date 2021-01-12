package gameLogic;

public class Cards {
    private String picture;
    private int value;
    private int number;

    public Cards() {}

    public Cards(String picture, int value, int number) {
        this.picture = picture;
        this.value = value;
        this.number = number;
    }

    @Override
    public Cards clone(){
        try{
            return (Cards) super.clone();
        } catch (CloneNotSupportedException e){
            return new Cards(this.picture, this.value, this.number);
        }
    }

    public void setValue(int index) {
        this.value = index;
    }
    public void reduceNumber() {
        this.number -= 1;
    }
    public String getPicture() {
        return picture;
    }
    public int getValue() {
        return value;
    }
    public int getNumber() {
        return number;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
