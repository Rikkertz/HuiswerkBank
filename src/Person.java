import java.util.HashSet;
import java.util.Set;

public class Person {
    String name;
    public static Person guest;

    private static final Set<String> names = new HashSet<String>();

    public Person(String name) throws RuntimeException {
        if (!names.add(name)) {
            throw new RuntimeException("Name must be unique.");
        }
        names.add(name);
        this.name = name;
    }

    static {
        guest = new Person("Guest");
    }
}
