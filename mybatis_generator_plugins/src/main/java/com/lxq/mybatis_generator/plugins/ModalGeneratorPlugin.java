package com.lxq.mybatis_generator.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ModalGeneratorPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
	// TODO Auto-generated method stub
	return true;
    }
    
    /**
     * import com.fasterxml.jackson.annotation.JsonInclude;
       modal上  添加  注解   @JsonInclude(JsonInclude.Include.NON_NULL)
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
	// TODO Auto-generated method stub
	
	 topLevelClass.addImportedType("com.fasterxml.jackson.annotation.JsonInclude");
	 topLevelClass.addAnnotation("@JsonInclude(JsonInclude.Include.NON_NULL)");
	
	
	
	return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    
    
    
}
