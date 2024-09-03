public class LinkText extends FormatText {

    public LinkText(HTMLText element, String link) {
        super(element, link);
    }

    @Override
    public String source() {
        // !! uso format de FormatText para guardar link (se lo mando al constructor)
        return "<a href:%s>%s</a>".formatted(format, element.source());
    }
}
