package Utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class retryAnalyzer implements IRetryAnalyzer {
    int retryCount=1;
    int maxRetryCount =3;
    @Override
    public boolean retry(ITestResult iTestResult) {

    if (!iTestResult.isSuccess())
    {
        if (retryCount <= maxRetryCount)
        {
                 System.out.println( "Retrying the "+iTestResult.getMethod().getMethodName() +" method for"+retryCount+" time(s) due to failure");
                 retryCount++;
                 iTestResult.setStatus(iTestResult.FAILURE);
                 return true;
        }
        else
        {
            iTestResult.setStatus(ITestResult.FAILURE);
        }
    }
    else
    {
        iTestResult.setStatus(iTestResult.SUCCESS);
    }
          return false;
    }
}
