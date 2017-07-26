/*
 *  
 * The MIT License (MIT)
 * Copyright (c) 2016 Daniel Cortes Pichardo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package mx.infotec.dads.sekc.essence;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEState;
import mx.infotec.dads.essence.model.foundation.SECheckpoint;
import mx.infotec.dads.essence.repository.SEAlphaRepository;
import mx.infotec.dads.sekc.SekcApp;
import mx.infotec.dads.sekc.util.EntityBuilder;

/**
 * Test for GeneratorService
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SekcApp.class)
public class AlphaRepositoryTest {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SEAlphaRepository alphaRepository;

    private static String id;

//    @Test
    public void getAlpha() {
        LOGGER.info("get practice id = {}", id);
        SEAlpha alpha = alphaRepository.findAll().get(0);
        for (SEState seState : alpha.getStates()) {
            LOGGER.info("->" + seState.getName());
            for (SECheckpoint seCheckpoint : seState.getCheckListItem()) {
                LOGGER.info("---->" + seCheckpoint.getName());
            }
        }
    }

    @Test
    public void saveAlpha() {
        System.out.println("**********************************");
        System.out.println("**********************************");
        System.out.println("**************ANTES***************");
        System.out.println("**********************************");
        System.out.println("**********************************");
        LOGGER.info("get practice id = {}", id);
        SEAlpha alpha = alphaRepository.findAll().get(0);
        SEAlpha find = alphaRepository.findOne(alpha.getId());
        for (SEState seState : find.getStates()) {
            LOGGER.info("->" + seState.getName());
            for (SECheckpoint seCheckpoint : seState.getCheckListItem()) {
                LOGGER.info("---->" + seCheckpoint.getName());
            }
        }
        List<String> idsStates = new ArrayList<>();
        for (SEState seState : find.getStates()) {
            idsStates.add(seState.getId());
        }
        find.setStates(new ArrayList<>());
        for (String id : idsStates) {
            SEState state = EntityBuilder.build(ent -> {
                ent.setId(id);
            }, SEState.class);
            find.getStates().add(state);
        }
        alphaRepository.save(alpha);
        System.out.println("**********************************");
        System.out.println("**********************************");
        System.out.println("**************DESPUES***************");
        System.out.println("**********************************");
        System.out.println("**********************************");
        SEAlpha otro = alphaRepository.findOne(alpha.getId());
        for (SEState seState : otro.getStates()) {
            LOGGER.info("->" + seState.getName());
            for (SECheckpoint seCheckpoint : seState.getCheckListItem()) {
                LOGGER.info("---->" + seCheckpoint.getName());
            }
        }
    }
}
