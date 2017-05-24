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
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import mx.infotec.dads.essence.model.foundation.SEKernel;
import mx.infotec.dads.essence.model.foundation.SEPractice;
import mx.infotec.dads.essence.repository.SEKernelRepository;
import mx.infotec.dads.essence.repository.SEPracticeRepository;
import mx.infotec.dads.essence.util.EssenceMapping;
import mx.infotec.dads.sekc.SekcApp;

/**
 * Test for GeneratorService
 * 
 * @author Daniel Cortes Pichardo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SekcApp.class)
public class PracticeRepositoryTest {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SEPracticeRepository practiceRepository;
    @Autowired
    private SEKernelRepository kernelRepository;

    private static String id;

    /**
     * 
     * @throws Exception
     */
    @Test
    public void insertPractice() throws Exception {
        LOGGER.info("insert practice");
        SEPractice practice = new SEPractice();
        // Practice
        practice.setConsistencyRules("Consistency Rules");
        practice.setEntry(Arrays.asList("Requeriments:State", "Software:Architecture"));
        practice.setMeasures(Arrays.asList("Timing", "five minutes pear meeting"));
        practice.setObjective("The Objetive of the practice");
        practice.setResult(Arrays.asList("Requeriments:Alpha", "Software:Architecture"));
        EssenceMapping.fillSELanguageElements(practice);
        EssenceMapping.fillSEElementGroup(practice);
        EssenceMapping.fillBitacoraData(practice);
        List<String> keywords = new ArrayList<>();
//        keywords.add("arquitectura");
         keywords.add("solo");
//        keywords.add("implementacion");
        practice.setKeyWords(keywords);
        practice.setName("Scrum");
        // ElementGroup
        // practice.setBriefDescription("Practice Brief Descrition");
        // practice.setDescription("Refactoring all");
        // practice.setIcon(null);
        // practice.setMergeResolution(null);
        // practice.setName("Name of the Practice");

        // practice.setOwnedElements(ownedElements);
        // practice.setReferredElements(referredElements);
        //
        // // Inheritance
        // practice.setExtension(extension);
        // practice.setFeatureSelection(featureSelection);
        // practice.setOwner(owner);
        // practice.setPatternAssociation(patternAssociation);
        // practice.setProperties(properties);
        // practice.setReferrer(referrer);
        // practice.setResource(resource);
        // practice.setSuppressable(false);
        // practice.setTag(tag);
        // practice.setViewSelection(viewSelection);
        practiceRepository.save(practice);
        id = practice.getId();
        LOGGER.info("id = {}", id);
        SEKernel seKernel = kernelRepository.findAll().get(0);
        if (seKernel.getOwnedElements() == null) {
            seKernel.setOwnedElements(new ArrayList<>());
        }
        seKernel.getOwnedElements().add(practice);
        System.out.println(seKernel.getId());
        kernelRepository.save(seKernel);
    }

    @Test
    public void getPractice() {
        LOGGER.info("get practice id = {}", id);
        SEPractice practice = practiceRepository.findOne(id);
        LOGGER.info(practice.getName());
        LOGGER.info("id: {}", practice.getId());
    }

    @Test
    public void findPractice() {
        LOGGER.info("Runnig and finding");
        SEPractice practice = new SEPractice();
        List<String> keywords = new ArrayList<>();
        keywords.add("arquitectura");
        keywords.add("solo");
        practice.setKeyWords(keywords);
        Pageable pageRequest = new PageRequest(0, 11);
        Page<SEPractice> practicesList = practiceRepository.findByKeyWordsIn(keywords, pageRequest);
        LOGGER.info("Size: {}", practicesList.getSize());
        for (SEPractice sePractice : practicesList) {
            LOGGER.info("id {}", sePractice.getId());
            if (sePractice.getKeyWords() != null)
                LOGGER.info("name {}", sePractice.getName());
            for (String keyword : sePractice.getKeyWords()) {
                LOGGER.info("keyword {}", keyword);
            }
        }
    }
}
