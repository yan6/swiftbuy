package service;

import com.ywj.swiftbuy.service.testcode.TestCodeService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-service-test.xml")
public class TransactionServiceTest {

    @Autowired
    private TestCodeService testCodeService;

    @Test
    @After
    public void test1() {
        testCodeService.update();
    }
}
