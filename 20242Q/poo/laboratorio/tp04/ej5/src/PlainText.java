public class PlainText extends BasicText {
    private String text;

    public PlainText(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String source() {
        return text;
    }
}
