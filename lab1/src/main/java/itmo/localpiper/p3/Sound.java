package itmo.localpiper.p3;

public class Sound {
    private final String type;
    private final Object source;

    public Sound(String type, Object source) {
        this.type = type;
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public Object getSource() {
        return source;
    }

}
