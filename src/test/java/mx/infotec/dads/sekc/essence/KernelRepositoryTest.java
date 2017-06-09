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
// */
//package mx.infotec.dads.sekc.essence;
//
//import java.util.List;
//
//import org.joda.time.DateTime;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import mx.infotec.dads.essence.model.foundation.SEKernel;
//import mx.infotec.dads.essence.repository.SEKernelRepository;
//import mx.infotec.dads.sekc.SekcApp;
//
///**
// * Test for GeneratorService
// * 
// * @author Daniel Cortes Pichardo
// *
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SekcApp.class)
//public class KernelRepositoryTest {
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private SEKernelRepository kernelRepository;
//
//    private static String id;
//
//    /**
//     * 
//     * @throws Exception
//     */
//    @Test
//    public void insertKernel() throws Exception {
//        LOGGER.info("insert practice");
//        List<SEKernel> kernelList = kernelRepository.findAll();
//        SEKernel kernel = new SEKernel();
//        if (kernelList.isEmpty()) {
//            kernel.setBriefDescription("Essence default kernel");
//            kernel.setConsistencyRules("Consistencies rules");
//            kernel.setDescription("Essence default kernel");
//            kernel.setExtension(null);
//            kernel.setFeatureSelection(null);
//            kernel.setIcon(null);
//            kernel.setMergeResolution(null);
//            kernel.setName("essence-core");
//            kernel.setOwnedElements(null);// por defecto no hay elementos
//            kernel.setOwner(null);
//            kernel.setPatternAssociation(null);
//            kernel.setProperties(null);
//            kernel.setReferredElements(null);
//            kernel.setReferrer(null);
//            kernel.setReferringMethod(null);
//            kernel.setResource(null);
//            kernel.setSuppressable(false);
//            kernel.setTag(null);
//            kernel.setViewSelection(null);
//            kernel.setCreatedDate(new DateTime());
//            kernel.setLastModifiedDate(new DateTime());
//
//        } else {
//            kernel = kernelList.get(0);
//            kernel.setName("otro nombre");
//            kernel.setCreatedDate(new DateTime());
//            kernel.setLastModifiedDate(new DateTime());
//        }
//
//        kernelRepository.save(kernel);
//        id = kernel.getId();
//        LOGGER.info("id = {}", id);
//    }
//
//    @Test
//    public void getKernel() {
//        LOGGER.info("get kernel id = {}", id);
//        SEKernel kernel = kernelRepository.findOne(id);
//        LOGGER.info("id: {}", kernel.getId());
//    }
//}
//