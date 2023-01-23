import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{
    //1
    @Test
    public void testGetLocalNumber () {
        Assert.assertEquals("getLocalNumber method doesn't return 14", 14, this.getLocalNumber());
    }
    //2
    @Test
    public void testGetClassNumber (){
        Assert.assertTrue("GetClassNumber method returns value less or equals than 45", this.getClassNumber() > 45);

    }
    //3
    @Test
    public void testGetClassString() {
        Assert.assertTrue("GetClassString method doesn't returns Hello or hello",
                StringUtils.containsAny(this.getClassString(), "Hello", "hello"));
    }
}
