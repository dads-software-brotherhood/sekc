package mx.infotec.dads.sekc.util;

import java.util.function.Consumer;

import mx.infotec.dads.sekc.exception.SekcException;

/**
 * EntityBuilder is and abstract builder used for create Entitties in lambda
 * notation.s
 * 
 * @author Daniel Cortes Pichardo
 *
 */
public class EntityBuilder {

    public static <T> T build(Consumer<T> block, Class<T> type) {
        T s = null;
        try {
            s = type.newInstance();
            block.accept(s);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new SekcException("EntityBuilder Exception for Entity: " + type.getName(), e);
        }
        return s;
    }

}
