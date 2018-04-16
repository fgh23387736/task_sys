package com.sys.typeConverter;

import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
import com.sys.entity.Task;

public class TaskTypeConverter extends DefaultTypeConverter{
	@Override  
    public Object convertValue(Map arg0, Object value,Class toType) {  
        if (toType == Task.class) {// 当字符串向Date类型转换时  
		    String[] params = (String[]) value;// Request.getParameterValues() 
		    Task theTask = new Task();
		    theTask.setTaskId(Integer.valueOf(params[0]));
		    return theTask;
		} else if (toType == Integer.class) {// 当Date转换成字符串时 
		    Task task = (Task) value;  
		    return task.getTaskId();  
		}else if (toType == String.class) {// 当Date转换成字符串时 
		    Task task = (Task) value;  
		    return task.getTaskId()+"";  
		}  
        return null;  
    } 
}
