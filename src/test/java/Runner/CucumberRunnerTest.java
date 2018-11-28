package Runner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = "src/test/resources/ValidateEditPaymentAPI.feature",glue={"stepDefinitions"},tags = {"@RepaymentOptionTest"}
)
public class CucumberRunnerTest {

}