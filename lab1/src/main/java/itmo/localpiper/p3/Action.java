package itmo.localpiper.p3;

public class Action {
    private final String type;
    private final Object argument;

    public Action(String type, Object argument) {
        this.type = type;
        this.argument = argument;
    }

    public String getType() {
        return type;
    }

    public Object getArgument() {
        return argument;
    }
}
