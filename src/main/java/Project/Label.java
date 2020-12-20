package Project;

public class Label {

    //Class attributes definitions
    private long id;
    private String text;

    //Overloaded Constructor
    public Label(long id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    //Get method for ID
    public long getId() {
        return id;
    }

    //Set method for ID
    public void setId(long id) {
        this.id = id;
    }

    //Get method for Text
    public String getText() {
        return text;
    }

    //Set method for Text
    public void setText(String text) {
        this.text = text;
    }

}