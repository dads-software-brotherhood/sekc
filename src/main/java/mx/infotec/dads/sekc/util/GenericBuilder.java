package mx.infotec.dads.sekc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This GenericBuilder is used to buil any Domain entity in differents ways, The
 * original idea como from stackoverflow:
 * 
 * @see <a href=
 *      "https://stackoverflow.com/questions/31754786/how-to-implement-the-builder-pattern-in-java-8">http://stackoverflow.com</a>
 *      <blockquote>
 * 
 *      <pre>
 *      {@code
 * Person value = GenericBuilder.of(Person::new)
 *         .with(Person::setName, "Otto").with(Person::setAge, 5).build();
 * </pre></blockquote>
 * 
 * @author Daniel Cortes Pichardo
 * 
 *
 * @param <T>
 */
public class GenericBuilder<T> {

    private final Supplier<T> instantiator;

    private List<Consumer<T>> instanceModifiers = new ArrayList<>();

    private GenericBuilder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    public static <T> GenericBuilder<T> of(Supplier<T> instantiator) {
        return new GenericBuilder<T>(instantiator);
    }

    public <U> GenericBuilder<T> with(BiConsumer<T, U> consumer, U value) {
        Consumer<T> c = instance -> consumer.accept(instance, value);
        instanceModifiers.add(c);
        return this;
    }

    public T build() {
        T value = instantiator.get();
        instanceModifiers.forEach(modifier -> modifier.accept(value));
        instanceModifiers.clear();
        return value;
    }
}