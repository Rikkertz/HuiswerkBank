import java.util.HashSet;
import java.util.Set;

public class Person {
    String name;
    private static final Set<String> names = new HashSet<String>();

    public Person(String name) throws RuntimeException {
        if (!names.add(name)) {
            throw new RuntimeException("Name must be unique.");
        }
        names.add(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }

    public static Person guest = new Person("Guest");
}
