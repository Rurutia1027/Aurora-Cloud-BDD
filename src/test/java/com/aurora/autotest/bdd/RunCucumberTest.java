package com.aurora.autotest.bdd;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Runner class for Serenity + Cucumber BDD tests.
 *
 * This class uses JUnit 4 to launch Cucumber features via Serenity.
 */
@RunWith(CucumberWithSerenity.class)  // REQUIRED for Serenity + Cucumber
@CucumberOptions(
        features = "src/test/resources/features",   // Path to your .feature files
        glue = "com.aurora.autotest.bdd.steps",     // Package containing step definitions
        plugin = {
                "pretty",                            // Console-friendly output
                "json:target/cucumber.json"          // JSON report for Serenity
        }
)
public class RunCucumberTest {
    // No additional code required; CucumberWithSerenity handles execution
}
