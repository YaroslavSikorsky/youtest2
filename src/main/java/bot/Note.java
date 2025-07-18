package bot;

public class Note {

    public String text;

    public Note(String text) {
        this.text = text;

    }

    @Override
    public String toString() {
        return text + " + text";
    }

    public String getText() {
        return text;
    }
}
