package tuanle.testing;


import org.junit.Assert;
import org.junit.Test;
import tuanle.DefaultValidator;
import tuanle.model.Staff;

/**
 * Validator tester
 */
public class UpdatedTest {
    /**
     * When object for test is null
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenObject_isNull() {
        Staff staff = null;
        DefaultValidator validator = new DefaultValidator();
        validator.validate(staff);
    }

    /**
     * When first name of staff is null and last name is size violation
     */
    @Test
    public void when_firstName_isNull_andLastname_isSizeViolation() {
        Staff staff = new TestUtils().createStaff(null, "SDFD");
        DefaultValidator validator = new DefaultValidator();
        Object actualList[] = validator.validate(staff).toArray();
        Object expectedList[] = TestUtils.createCollectionOfViolation(staff).toArray();
        Assert.assertEquals(actualList.length, expectedList.length);
        for(int count = 0; count < expectedList.length; count ++) {
            Assert.assertEquals(actualList[count].toString(), expectedList[count].toString());
        }
    }

    /**
     * When both first name and last name is null
     */
    @Test
    public void when_firstName_isNull_andLastname_isNull() {
        Staff staff = new TestUtils().createStaff(null, null);
        DefaultValidator validator = new DefaultValidator();
        Object actualList[] = validator.validate(staff).toArray();
        Object expectedList[] = TestUtils.createCollectionOfViolation(staff).toArray();
        Assert.assertEquals(actualList.length, expectedList.length);
        for(int count = 0; count < expectedList.length; count ++) {
            Assert.assertEquals(actualList[count].toString(), expectedList[count].toString());
        }
    }

    /**
     * When both first name and last name is size violation
     */
    @Test
    public void when_firstName_isSizeViolation_andLastname_isSizeViolation() {
        Staff staff = new TestUtils().createStaff("ABCD", "1234");
        DefaultValidator validator = new DefaultValidator();
        Object actualList[] = validator.validate(staff).toArray();
        Object expectedList[] = TestUtils.createCollectionOfViolation(staff).toArray();
        Assert.assertEquals(actualList.length, expectedList.length);
        for(int count = 0; count < expectedList.length; count ++) {
            Assert.assertEquals(actualList[count].toString(), expectedList[count].toString());
        }
    }

    /**
     * Test when both first name and last name is empty
     */
    @Test
    public void when_firstName_isEmpty_andLastname_isEmpty() {
        Staff staff = new TestUtils().createStaff("", "");
        DefaultValidator validator = new DefaultValidator();
        Object actualList[] = validator.validate(staff).toArray();
        Object expectedList[] = TestUtils.createCollectionOfViolation(staff).toArray();
        Assert.assertEquals(actualList.length, expectedList.length);
        for(int count = 0; count < expectedList.length; count ++) {
            Assert.assertEquals(actualList[count].toString(), expectedList[count].toString());
        }
    }

    /**
     * Test when both first name and last name is violation of regrex
     *
     * */
    @Test
    public void when_Both_firstName_AndLastName_isRegrexViolation() {
        Staff staff = new TestUtils().createStaff("12Fd", "32Fg3s");
        DefaultValidator validator = new DefaultValidator();
        Object actualList[] = validator.validate(staff).toArray();
        Object expectedList[] = TestUtils.createCollectionOfViolation(staff).toArray();
        Assert.assertEquals(actualList.length, expectedList.length);
        for(int count = 0; count < expectedList.length; count ++) {
            Assert.assertEquals(actualList[count].toString(), expectedList[count].toString());
        }
    }
}
