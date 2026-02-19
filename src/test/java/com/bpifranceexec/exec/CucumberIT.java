package com.bpifranceexec.exec;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters({
        @ConfigurationParameter(
                key = PLUGIN_PROPERTY_NAME,
                value = "pretty, html:target/cucumber-reports/cucumber-report.html, json:target/cucumber-reports/cucumber-report.json"
        ),
        // ADD THIS PARAMETER TO TELL CUCUMBER WHERE YOUR STEPS ARE
        @ConfigurationParameter(
                key = GLUE_PROPERTY_NAME,
                value = "com.bpifranceexec.exec.integrationTest.steps"
        )
})
public class CucumberIT {
// This class remains empty. It's used only for configuration.
}
