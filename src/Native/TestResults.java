package Native;

import org.testng.annotations.Test;

public class TestResults {
	
	@Test (enabled = true, testName ="testResults", groups = {"temp"})
	public void runSuccessFaillure (){
		org.testng.Assert.fail("WebElement" + " still visible");
		//org.testng.Assert.assertTrue(true, "success");
		
		
	}

}
