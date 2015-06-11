package com.lxq.mybatis_generator.plugins;
 
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class Utils {
    
	public static  final String   Example_suffix = "Example";
	public static  final String   resultMap = "resultMap";
	public static  final String   Base_Column_List = "Base_Column_List";
	public static  final String   BaseResultMap = "BaseResultMap";
	public static  final String   parameterType = "parameterType";
	
	
	 
	/**
	 *  若  fieldClassShortType 没有引入 , 需  addimprot ; 
	 * @param topLevelClass
	 * @param fieldName
	 * @param fieldClassShortType
	 */
	public static  void AddFieldWhidSetGet(TopLevelClass topLevelClass,
			String fieldName,  String  fieldClassShortType  , String   defalueVale) {
		
		FullyQualifiedJavaType type = new FullyQualifiedJavaType( fieldClassShortType );
		
		 char c = fieldName.charAt(0);  
	     String camel = Character.toUpperCase(c) + fieldName.substring(1);  
		
		
		String setter = "set" + camel; 
		String getter = "get" + camel; 
		
		// 添加 selectFields 字段 ;
		Field selectFields = new Field();
		selectFields.setVisibility(JavaVisibility.PRIVATE);
		selectFields.setType(type);
		selectFields.setName(fieldName);
		if(null!= defalueVale){
			selectFields.setInitializationString( defalueVale );
		}
		
		topLevelClass.addField(selectFields);

		// 添加 set selectFields 方法;
		Method setMethod = new Method();
		setMethod.setVisibility(JavaVisibility.PUBLIC);
		setMethod.setName(setter);
		setMethod.addParameter(new Parameter(type, fieldName));
		setMethod.addBodyLine("this." + fieldName + " =   "+fieldName+" ;");
		topLevelClass.addMethod(setMethod);

		// 添加 get SelctedFields 方法;
		Method getMethod = new Method();
		getMethod.setVisibility(JavaVisibility.PUBLIC);
		getMethod.setName(getter);
		getMethod.setReturnType(type);
		getMethod.addBodyLine(" return " + fieldName + ";");
		topLevelClass.addMethod(getMethod);
		
	}

	
	public  static XmlElement createXmlElement ( String tagname  ){
		  XmlElement  xml = new XmlElement(tagname);
		  return  xml ; 
	}
	
	public static  TextElement  createTextElement (String text){
		
		return new TextElement(text);
	}
	
	public static XmlElement createXmlElementWithAttr ( String tagname  , String attr ,  String value  ){
		XmlElement  xml = new XmlElement(tagname);
		xml.addAttribute(new Attribute(attr, value));
		return xml ; 
		
	}
	
	 /**
	  * 
	  * @param visbity
	  * @param returnType
	  * @param method_name
	  * @param methodBody
	  * @param type_name
	  * @return
	  */
     public  static Method createMethod( JavaVisibility  visbity,
		                         String returnType, 
		                         String method_name ,
	                                 String methodBody,
	                                 HashMap<String, String>  paramstype_name 
	                                 ) {
		Method method = new Method(); 
		if(null != visbity){
		    method.setVisibility( visbity );
		}
		method.setReturnType(new FullyQualifiedJavaType(returnType ));
		method.setName(method_name);
		
		if(null != methodBody){
		    method.addBodyLine(methodBody);
		}
		
		
		if(null != paramstype_name){
			 Set<String> keySet = paramstype_name.keySet();
		    for( String key : keySet ){
		    	  String value = paramstype_name.get(key); 
		    	
			    method.addParameter(new Parameter(new FullyQualifiedJavaType(key),  value));
		     }
		}
		return method;
	    }
	
	// 添加  字段,  set , get ; 
	
	
}

