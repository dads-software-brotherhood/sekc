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

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.essence.model.foundation.Checkpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.essence.model.activityspaceandactivity.SEActivitySpace;
import mx.infotec.dads.essence.model.alphaandworkproduct.SEAlpha;
import mx.infotec.dads.essence.model.competency.SECompetency;
import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SELanguageElement;
import mx.infotec.dads.essence.model.foundation.extention.SEAreaOfConcern;
import mx.infotec.dads.essence.repository.SEKernelRepository;
import mx.infotec.dads.sekc.SekcApp;
import mx.infotec.dads.sekc.util.EssenceFilter;

/**
 * Test for GeneratorService
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SekcApp.class)
public class KernelRepositoryTest {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SEKernelRepository kernelRepository;

    @Test
    public void getKernel() {
        kernelRepository.findAll().forEach(seKernel -> visitKernel(seKernel));
    }

    /**
     * Process each element of the kernel
     * 
     * @param seKernel
     */
    private void visitKernel(SEKernel seKernel) {
        LOGGER.info("[KERNEL]          ->" + seKernel.getName());
        EssenceFilter.filterLanguageElement(seKernel.getOwnedElements(), SEAreaOfConcern.class)
                .forEach(area -> visitAreaOfConcern(area));
    }

    private void visitAreaOfConcern(SEAreaOfConcern seAreaOfConcern) {
        LOGGER.info("[AREA OF CONCERN] -->" + seAreaOfConcern.getName());
        seAreaOfConcern.getOwnedElements().forEach(element -> processAreaOfConcernElements(element));
    }

    private void processAreaOfConcernElements(SELanguageElement element) {
        if (element instanceof SEAlpha) {
            SEAlpha seAlpha = (SEAlpha) element;
            visitAlpha(seAlpha);
        } else if (element instanceof SEActivitySpace) {
            SEActivitySpace seActivitySpace = (SEActivitySpace) element;
            visitActivitySpace(seActivitySpace);
        } else if (element instanceof SECompetency) {
            SECompetency seCompetency = (SECompetency) element;
            visitCompetency(seCompetency);
        }
    }

    private void visitAlpha(SEAlpha seAlpha) {
        LOGGER.info("[ALPHA]          --->" + seAlpha.getName());
        seAlpha.getStates().forEach(state -> {
            LOGGER.info("[STATE]          ---->" + state.getName());
            visitCheckpoints(state.getCheckListItem());
        });
    }

    private void visitActivitySpace(SEActivitySpace seActivitySpace) {
        LOGGER.info("[ACTIVITY SPACE] --->" + seActivitySpace.getName());
    }

    private void visitCompetency(SECompetency seCompetency) {
        LOGGER.info("[COMPETENCY]      --->" + seCompetency.getName());
        seCompetency.getPossibleLevel().forEach(level -> {
            LOGGER.info("[COMPETENCY LEVEL]---->" + level.getName());
            visitCheckpoints(level.getChecklistItem());
        });
    }

    private void visitCheckpoints(Collection<? extends Checkpoint> checkpoints) {
        checkpoints.forEach(checkpoint -> LOGGER.info("[CHECKPOINT]     ----->" + checkpoint.getName()));
    }

}
