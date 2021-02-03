package ${basePackage};
import ${realPackage}.${className};
import com.lzj.autotestpc.servce.AppiumOperationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.lzj.autotestpc.servce.impl.AppiumOperationServiceImp;

public class ${className}Test {
    private AppiumOperationServiceImp service;

    private String AppPackage=${AppPackage};
    private String AppActivity=${AppActivity};
    @Before
    public void testAfter() {
        service = new AppiumOperationService();
    }
    <#list scriptList as scriptStepBean>
    @Test
    public void test${scriptName}${scriptId}() {
        service.${scriptStepBean}(${idOrText},${outTime});
    }
    </#list>

    public String getAppPackage() {
        return AppPackage;
    }
    public String getAppActivity() {
        return AppActivity;
    }


}