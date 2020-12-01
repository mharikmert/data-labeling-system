import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
public class Label {
    private static final Logger logger = LogManager.getLogger(Label.class);
    private long id;
    private String text;

    public Label(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
