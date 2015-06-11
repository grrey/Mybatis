package com.lxq.mybatis_generator.plugins;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class MysqlPaginationPlugin extends PluginAdapter {  
  
	 @Override  
	    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,  
	            IntrospectedTable introspectedTable) {  
	        // add field, getter, setter for limit clause   
	        
	        Utils.AddFieldWhidSetGet(topLevelClass, "limitStart", "int", "-1");
	        Utils.AddFieldWhidSetGet(topLevelClass, "limitEnd", "int", "-1");
	        
//	        return super.modelExampleClassGenerated(topLevelClass,  
//	                introspectedTable); 
	       //取消插件 , model 集成 genericModelExample , 其中包含了 start , end 字段; 
	        return false ; 
	        
	        
	    }  
	    @Override  
	    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(  
	            XmlElement element, IntrospectedTable introspectedTable) { 
	    	
	    	element.addElement(Utils.createTextElement(
	    	    	" <if test= \" limitStart != -1 \"  >"
     		       + "     LIMIT  ${limitStart} ,${limitEnd}"
     		       + "  </if>  "  ));
	    	
	    
	        
	        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element,  
	                introspectedTable);  
	    }  
	     
	    /** 
	     * This plugin is always valid - no properties are required 
	     */  
	    public boolean validate(List<String> warnings) {  
	        return true;  
	    }  
	    
	 
	}  
