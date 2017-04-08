package mx.infotec.dads.sekc.cucumber.stepdefs;

import mx.infotec.dads.sekc.SekcApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = SekcApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
