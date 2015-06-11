package com.lxq.mybatis_generator.plugins;

import java.util.HashMap;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.TableConfiguration;
/**
 *  添加   selectByExampleSelective  处理; 
 * @author Administrator
 *
 */
public class MySqlSelectSelectiveWithPagePlugin extends PluginAdapter {

	final String methodname = "selectByExampleSelective";
	final String selectiveColumns_id = "Selective_Base_Column_List";
	final String  exampleMethod_addSelFields = "addSelectiveFields";
	final String  filterFieldlist  = "filterFieldlist";

	/**
	 * 在mapper 文件中 添加 selectByExanpleSelective <sql>
	 * 
	 * 
	 *     <select id="selectByExample" resultMap="BaseResultMap" parameterType="com.eliteams.quick4j.web.model.UserExample" >
			    select
			    <if test="distinct" >
			      distinct
			    </if>
			    <include refid="Selective_Base_Column_List" />
			    from user
			    <if test="_parameter != null" >
			      <include refid="Example_Where_Clause" />
			    </if>
			    <if test="orderByClause != null" >
			      order by ${orderByClause}
			    </if>
		  </select>
	 * 
	 * 
	 */
	@Override
	public boolean sqlMapDocumentGenerated(Document document,
			IntrospectedTable introspectedTable) {

		String modelExanple_fullName = introspectedTable.getExampleType();
		String tableName = introspectedTable.getTableConfiguration().getTableName();

		XmlElement select = Utils.createXmlElementWithAttr("select", "id", methodname);
		// -------------------------------------
		select.addAttribute(new Attribute(Utils.resultMap, Utils.BaseResultMap));
		select.addAttribute(new Attribute(Utils.parameterType, modelExanple_fullName));
		// -------------------------------------
		 
		 
           select.addElement(new TextElement(
        		      " select    \n " +
			           " <if test=\"distinct\" >  \n "+
			            "   distinct   \n"+
			           " </if>  \n" +
			           " <include refid=\"" + selectiveColumns_id  +"\"  />  \n " +
			           " from  "+ tableName +
        		      "<if test=\"_parameter != null\" >   \n" +
        		       "   <include  refid=\"Example_Where_Clause\" />  \n" +
        		       " </if>  \n" +
        		       "<if test=\"orderByClause != null\" >  \n" +
        		       "   order by ${orderByClause}  \n" +
        		       "</if> \n"  
        		       // with page 
        		       +"   <if test= \" limitStart != -1 \"  >"
        		       + "     LIMIT  ${limitStart} ,${limitEnd}"
        		       + "  </if>  " 
        		       
        		   )); 
          
          // -------------------------------------
         XmlElement rootElement = document.getRootElement(); 
         rootElement.addElement(select);
         
         // 创建   Selective_Base_Column_List 动态sql ; 
          
         
         rootElement.addElement( new TextElement(
        	  " <sql id=\"Selective_Base_Column_List\" >  \n  " +
        	  "   <if  test= \"!filterField\">  " +
        	  "       <include refid=\"Base_Column_List\" />    \n " +
        	  "   </if>  \n" +
        	  "   <if test=\"filterField\" >   \n " +
        	  "       <foreach collection=\" "+filterFieldlist+"  \"  item=\"item\" index=\"index\"" +
        	  "               separator=\",\" >    \n " +
        	  "              ${item} \n   " +
        	  "       </foreach>    \n  " +
        	  "   </if>  \n " +
        	  " </sql>   \n  "
        	 ));
         
         
          
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	/*
	 * true 启动 插件;
	 */
	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 在 interfa 中 创建 selectByExanpleSelective 方法;
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

		TableConfiguration tableConfiguration = introspectedTable
				.getTableConfiguration();
		String modal = tableConfiguration.getDomainObjectName();

		String returnType = "List<" + modal + ">";
		String paramsType = modal + Utils.Example_suffix;

		HashMap<String, String> params = new HashMap<>();
		params.put(paramsType, "example");

		Method selectByExampleSelective = Utils.createMethod(null, returnType,
				methodname, null, params);

		interfaze.addMethod(selectByExampleSelective);

		return super.clientGenerated(interfaze, topLevelClass,
				introspectedTable);
	}
   
	
	/**
	 *  为 modelExample  
	 *               // 添加   modelFields 枚举 ; 
	 *               // 添加 addSelectiveFields 方法; 
	 *                
	 *                添加 filterField  boolean 字段; 
	 *                添加   filterFieldlist  list 集合; 
	 *                为各个字段添加 sel_ziduan  方法; 
	 *                
	 *                
	 *                
	 *    <sql  id="Selective_Base_Column_List" > 
                 <if  test= "filterField">
                      <include refid="Base_Column_List" />
                 <if>
	 *  
	 */
	
	@Override
	public  boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
	     // 添加 filterField  boolean 字段;
	     Utils.AddFieldWhidSetGet(topLevelClass, "filterField", "boolean", "false");
	     
	     topLevelClass.addImportedType("java.util.ArrayList");
	     
	     Utils.AddFieldWhidSetGet(topLevelClass, filterFieldlist, "ArrayList", null);
	    
	    
		// TODO Auto-generated method stub
		 List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
		
  	         String modalName = introspectedTable.getTableConfiguration().getDomainObjectName();
		 
		 
		 for(IntrospectedColumn col :  allColumns){
			 String javaProperty = col.getJavaProperty();
			 String actualColumnName = col.getActualColumnName();
			
			 // 添加字段枚举; 
			 //fieldsEnum.addEnumConstant(javaProperty);
			 // 添加 字段; 
			  
			 Method sel_field = Utils.createMethod(JavaVisibility.PUBLIC, 
				            modalName+Utils.Example_suffix,
				 "sel_"+javaProperty, 
				 "   if( !filterField ) {    \n " +
				 "        filterField  = true ;   " +
			         "        filterFieldlist = new ArrayList();  \n "+		 
				 "   }" +
				 "   filterFieldlist.add( \"" +actualColumnName+"\") ; \n" +
				  "  return  this; ",
				 null);
			 topLevelClass.addMethod(sel_field);
			 
		 } 
		 
                // topLevelClass.addMethod(addSelectiveField);
		 
		//  impprot java.lang.reflect.Method;
		// topLevelClass.addImportedType("java.lang.reflect.Method");
		
		
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}
	
	
	

}
