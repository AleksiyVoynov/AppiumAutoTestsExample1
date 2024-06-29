import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GoogleSearchPage;

public class GoogleSearchTest extends BaseTest {

    @Test(priority = 1, description = "test google search")
    @Description("""
            Test Description:
            1. go to google page
            2. make search
            3. validate result""")
    public void testGoogleSearch() {
        GoogleSearchPage googleSearchPage = new GoogleSearchPage(config);
        var testData = "facebook";

        googleSearchPage.navigateTo("https://www.google.com");
        googleSearchPage.searchFor(testData);
        Assert.assertTrue(googleSearchPage.getSearchingResult(testData), "nothing was found");
    }
}
