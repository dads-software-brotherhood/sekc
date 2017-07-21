package mx.infotec.dads.sekc.util;

import java.util.function.Consumer;

import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEPractice;

public class EntityBuilder {

    public static SEKernel build(Consumer<SEKernel> block) {
        SEKernel target = new SEKernel();
        block.accept(target);
        return target;
    }
}
