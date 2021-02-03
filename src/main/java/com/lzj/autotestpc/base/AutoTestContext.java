package com.lzj.autotestpc.base;

import com.lzj.autotestpc.factory.StageFactory;
import com.lzj.autotestpc.tool.StageManagerTool;

import java.util.HashMap;
import java.util.Map;

public class AutoTestContext {

    public static StageFactory stageFactory = new StageFactory();
    public static StageManagerTool stageManagerTool = new StageManagerTool();
    public static Map<String, Object> controllers = new HashMap<String, Object>();
}
