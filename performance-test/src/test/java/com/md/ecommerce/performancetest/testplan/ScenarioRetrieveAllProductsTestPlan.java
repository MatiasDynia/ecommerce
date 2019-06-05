package com.md.ecommerce.performancetest.testplan;

import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.config.RandomVariableConfig;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.modifiers.CounterConfig;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.timers.ConstantThroughputTimer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.*;
import static java.util.Objects.isNull;

public class ScenarioRetrieveAllProductsTestPlan {

    private static final Properties properties = new Properties();

    @Before
    public void setup() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("performanceTest.properties");
        properties.load(inputStream);
    }

    @Test
    public void runScenarioRetrieveAllProductsTestPlan() throws IOException {

        String slash = "/";
        String jmeterHome = System.getProperty("user.dir") + slash + properties.getProperty("jmeter.home.path");

        if (!isNull(jmeterHome)) {
            File jmeterProperties = new File(jmeterHome + slash + "bin" + slash + properties.getProperty("jmeter.properties.file"));
            if (jmeterProperties.exists()) {

                //JMeter Engine
                StandardJMeterEngine jmeter = new StandardJMeterEngine();

                //JMeter initialization (properties, log levels, locale, etc)
                JMeterUtils.setJMeterHome(jmeterHome);
                JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
                JMeterUtils.initLogging();
                JMeterUtils.initLocale();

                //JMeter Test Plan
                HashTree testPlanTreeScenarioRetrieveAllProducts = new HashTree();

                //HeaderManager
                HeaderManager headerManagerScenarioRetrieveAllProducts = new HeaderManager();
                headerManagerScenarioRetrieveAllProducts.add(new Header("Content-type", "application/json"));
                headerManagerScenarioRetrieveAllProducts.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
                headerManagerScenarioRetrieveAllProducts.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());

                //HTTP sampler
                HTTPSamplerProxy samplerScenarioRetrieveAllProducts = new HTTPSamplerProxy();
                samplerScenarioRetrieveAllProducts.setName("RetrieveAllProducts test");

                samplerScenarioRetrieveAllProducts.setHeaderManager(headerManagerScenarioRetrieveAllProducts);
                samplerScenarioRetrieveAllProducts.setDomain(properties.getProperty("api.hostname"));
                samplerScenarioRetrieveAllProducts.setPort(Integer.parseInt(properties.getProperty("api.port")));
                samplerScenarioRetrieveAllProducts.setProtocol(properties.getProperty("api.protocol"));
                samplerScenarioRetrieveAllProducts.setPath(properties.getProperty("scenarioRetrieveAllProducts.path"));
                samplerScenarioRetrieveAllProducts.setMethod(properties.getProperty("scenarioRetrieveAllProducts.method"));
                samplerScenarioRetrieveAllProducts.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                samplerScenarioRetrieveAllProducts.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

                //In case to need to create requestIds, accountNumbers, etc (just as example, not required in this scenario)
                RandomVariableConfig randomVariable = new RandomVariableConfig();
                randomVariable.setProperty("minimumValue","10000");
                randomVariable.setProperty("maximumValue","89999");
                randomVariable.setProperty("perThread",false);
                randomVariable.setProperty("variableName", "productNumber");

                //Loop controller
                LoopController loopControllerScenarioRetrieveAllProducts = new LoopController();
                loopControllerScenarioRetrieveAllProducts.setContinueForever(false);
                loopControllerScenarioRetrieveAllProducts.setLoops(
                        Integer.parseInt(properties.getProperty("scenarioRetrieveAllProducts.loops")));

                //ConstantThroughputTimer
                ConstantThroughputTimer constantThroughputTimerScenarioRetrieveAllProducts = new ConstantThroughputTimer();
                //AllScenarios.TPS * 60 * loadFactor * ScenarioGetReports.weight
                constantThroughputTimerScenarioRetrieveAllProducts.setEnabled(true);
                constantThroughputTimerScenarioRetrieveAllProducts.setProperty("calcMode",
                        properties.getProperty("scenarioRetrieveAllProducts.calcMode"));
                constantThroughputTimerScenarioRetrieveAllProducts.setProperty("throughput", Double.toString(
                        (Integer.parseInt(properties.getProperty("AllScenarios.TPS"))
                                * 60 * Integer.parseInt(properties.getProperty("loadFactor"))
                                * Double.parseDouble(properties.getProperty("scenarioRetrieveAllProducts.weight")))));

                //ThreadGroup
                ThreadGroup threadGroupScenarioRetrieveAllProducts = new ThreadGroup();
                threadGroupScenarioRetrieveAllProducts.setNumThreads(
                        Integer.parseInt(properties.getProperty("scenarioRetrieveAllProducts.threads.count")));
                threadGroupScenarioRetrieveAllProducts.setRampUp(
                        Integer.parseInt(properties.getProperty("scenarioRetrieveAllProducts.rampUpTime.seconds")));
                //If needed configure here a scheduler
                threadGroupScenarioRetrieveAllProducts.setDuration(
                        Integer.parseInt(properties.getProperty("scenarioRetrieveAllProducts.duration")));
                threadGroupScenarioRetrieveAllProducts.setDelay(
                        Integer.parseInt(properties.getProperty("scenarioRetrieveAllProducts.delay")));
                threadGroupScenarioRetrieveAllProducts.setSamplerController(loopControllerScenarioRetrieveAllProducts);

                //Counter
                CounterConfig counterConfigScenarioRetrieveAllProducts = new CounterConfig();
                counterConfigScenarioRetrieveAllProducts.setStart(
                        properties.getProperty("scenarioRetrieveAllProducts.counter.start"));
                counterConfigScenarioRetrieveAllProducts.setEnd("");
                counterConfigScenarioRetrieveAllProducts.setIncrement(
                        properties.getProperty("scenarioRetrieveAllProducts.counter.increment"));
                counterConfigScenarioRetrieveAllProducts.setFormat("");
                counterConfigScenarioRetrieveAllProducts.setIsPerUser(false);
                counterConfigScenarioRetrieveAllProducts.setVarName("RetrieveAllProductsCounter");

                //Test plan
                TestPlan testPlanScenarioRetrieveAllProducts = new TestPlan("RetrieveAllProductsJavaTestPlan");
                testPlanScenarioRetrieveAllProducts.setComment("");
                testPlanScenarioRetrieveAllProducts.setFunctionalMode(false);
                testPlanScenarioRetrieveAllProducts.setSerialized(false);
                testPlanScenarioRetrieveAllProducts.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
                testPlanScenarioRetrieveAllProducts.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());

                //Construct Test Plan
                testPlanTreeScenarioRetrieveAllProducts.add(testPlanScenarioRetrieveAllProducts);
                HashTree threadGroupHashTreeScenarioRetrieveAllProducts = testPlanTreeScenarioRetrieveAllProducts
                        .add(testPlanScenarioRetrieveAllProducts, threadGroupScenarioRetrieveAllProducts);
                testPlanTreeScenarioRetrieveAllProducts.add(constantThroughputTimerScenarioRetrieveAllProducts);
                threadGroupHashTreeScenarioRetrieveAllProducts
                        .add(samplerScenarioRetrieveAllProducts, headerManagerScenarioRetrieveAllProducts);
                threadGroupHashTreeScenarioRetrieveAllProducts.add(counterConfigScenarioRetrieveAllProducts);
                threadGroupHashTreeScenarioRetrieveAllProducts.add(testPlanScenarioRetrieveAllProducts, randomVariable);
                threadGroupHashTreeScenarioRetrieveAllProducts.add(constantThroughputTimerScenarioRetrieveAllProducts);

                //Add summariser output
                Summariser summariserScenarioRetrieveAllProducts = new Summariser("ScenarioRetrieveAllProductsInfoSummariser");

                //Sample configuration
                SampleSaveConfiguration sampleConfScenarioRetrieveAllProducts = new SampleSaveConfiguration();
                sampleConfScenarioRetrieveAllProducts.setTime(true);
                sampleConfScenarioRetrieveAllProducts.setLatency(true);
                sampleConfScenarioRetrieveAllProducts.setTimestamp(true);
                sampleConfScenarioRetrieveAllProducts.setLabel(true);
                sampleConfScenarioRetrieveAllProducts.setCode(true);
                sampleConfScenarioRetrieveAllProducts.setMessage(true);
                sampleConfScenarioRetrieveAllProducts.setThreadName(true);
                sampleConfScenarioRetrieveAllProducts.setDataType(true);
                sampleConfScenarioRetrieveAllProducts.setEncoding(false);
                sampleConfScenarioRetrieveAllProducts.setAssertions(true);
                sampleConfScenarioRetrieveAllProducts.setSubresults(true);
                sampleConfScenarioRetrieveAllProducts.setResponseData(false);
                sampleConfScenarioRetrieveAllProducts.setSamplerData(false);
                sampleConfScenarioRetrieveAllProducts.setAsXml(false);
                sampleConfScenarioRetrieveAllProducts.setFieldNames(true);
                sampleConfScenarioRetrieveAllProducts.setResponseHeaders(false);
                sampleConfScenarioRetrieveAllProducts.setRequestHeaders(false);
                sampleConfScenarioRetrieveAllProducts.setResponseData(false);
                sampleConfScenarioRetrieveAllProducts.setAssertionResultsFailureMessage(true);
                sampleConfScenarioRetrieveAllProducts.assertionsResultsToSave();
                sampleConfScenarioRetrieveAllProducts.setBytes(true);
                sampleConfScenarioRetrieveAllProducts.setSentBytes(true);
                sampleConfScenarioRetrieveAllProducts.setThreadCounts(true);
                sampleConfScenarioRetrieveAllProducts.setIdleTime(true);
                sampleConfScenarioRetrieveAllProducts.setConnectTime(true);

                //Store excecution results on file
                String logFileScenarioRetrieveAllProducts = System.getProperty("user.dir") +
                        properties.getProperty("jmeter.results.path") + "retrieveAllProductsTestPlanResults.jtl";

                //ResultCollector
                ResultCollector resultCollectorScenarioRetrieveAllProducts = new ResultCollector(summariserScenarioRetrieveAllProducts);
                resultCollectorScenarioRetrieveAllProducts.setProperty(TestElement.GUI_CLASS, "org.apache.jmeter.visualizers.ViewResultsFullVisualizer");
                resultCollectorScenarioRetrieveAllProducts.setProperty(TestElement.TEST_CLASS, "org.apache.jmeter.reporters.ResultCollector");
                resultCollectorScenarioRetrieveAllProducts.setProperty(TestElement.NAME, "View Results Tree");
                resultCollectorScenarioRetrieveAllProducts.setProperty(TestElement.ENABLED, true);
                resultCollectorScenarioRetrieveAllProducts.setSaveConfig(sampleConfScenarioRetrieveAllProducts);
                resultCollectorScenarioRetrieveAllProducts.setFilename(logFileScenarioRetrieveAllProducts);

                //Assertion
                ResponseAssertion assertionScenarioRetrieveAllProducts = new ResponseAssertion();
                assertionScenarioRetrieveAllProducts.setTestFieldResponseCode();
                assertionScenarioRetrieveAllProducts.addTestString("200");
                assertionScenarioRetrieveAllProducts.setAssumeSuccess(false);

                //Three plan configuration
                testPlanTreeScenarioRetrieveAllProducts.add(testPlanScenarioRetrieveAllProducts);
                testPlanTreeScenarioRetrieveAllProducts.add(assertionScenarioRetrieveAllProducts);
                testPlanTreeScenarioRetrieveAllProducts.add(testPlanTreeScenarioRetrieveAllProducts.getArray()[0],
                        resultCollectorScenarioRetrieveAllProducts);

                //Run Test Plan
                jmeter.configure(testPlanTreeScenarioRetrieveAllProducts);

                //save generated test plan to JMeter's .jmx file
                SaveService.saveTree(testPlanTreeScenarioRetrieveAllProducts, new FileOutputStream(
                        System.getProperty("user.dir") + properties.getProperty("jmeter.jmxFiles.path")  +
                                "RetrieveAllProductsJavaTestPlan.jmx"));

                jmeter.run();

                out.println("\nTest completed. See " + System.getProperty("user.dir") +
                        properties.getProperty("jmeter.results.path") + "retrieveAllProductsTestPlanResults.jtl file for results");
                out.println("JMeter .jmx script is available at " + System.getProperty("user.dir") +
                        properties.getProperty("jmeter.jmxFiles.path") + "retrieveAllProductsTestPlan.jmx\n");

                exit(0);
            }
        }

        err.println("jmeter.home property is not set or pointing to incorrect location");
        exit(1);
    }
}