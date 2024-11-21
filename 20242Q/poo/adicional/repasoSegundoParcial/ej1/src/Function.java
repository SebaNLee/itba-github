@FunctionalInterface
public interface Function<IN, OUT> {

    OUT apply(IN elem);
}
