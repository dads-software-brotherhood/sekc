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

import static org.assertj.core.api.Assertions.assertThatCode;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.infotec.dads.sekc.SekcApp;
import mx.infotec.dads.sekc.admin.practice.dto.PracticeDto;
import mx.infotec.dads.sekc.admin.practice.service.PracticeHelperService;

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
    private PracticeHelperService practiceHelperService;

    @Before
    public void setup() {
    }

    /**
     * Test insert Practice from remote dto
     * 
     * @throws Exception
     */
    @Test
    public void insertPracticeFromRemoteDto() throws Exception {
        LOGGER.info("insert practice");
        String kernelUrl = "https://gist.githubusercontent.com/danimaniarqsoft/6813e7c27d5b42bd3eda2844e87b107e/raw/b348b061f77a11de323a2852e95e44396b793ded/kernel.js";
        ObjectMapper mapper = new ObjectMapper();
        PracticeDto practiceDto = mapper.readValue(new URL(kernelUrl), PracticeDto.class);
        assertThatCode(() -> practiceHelperService.save(practiceDto)).doesNotThrowAnyException();
    }
}
