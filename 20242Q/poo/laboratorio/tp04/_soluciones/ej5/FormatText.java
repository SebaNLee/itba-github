package ar.edu.itba.poo.soltp4.ej5;

public class FormatText extends BasicText {

    protected HTMLText element;
    protected String format;

    public FormatText(HTMLText element, String format) {
        this.element = element;
        this.format = format;
    }

    @Override
    public String source() {
        return "<%s>%s</%s>".formatted(format, element.source(), format);
    }

}