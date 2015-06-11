package com.lxq.mybatis_generator.plugins;

import java.util.List;
import java.util.logging.Logger;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;

public class DaoGeneratorPlugin extends PluginAdapter {
    
    Logger log = Logger.getAnonymousLogger();
    
    @Override
    public boolean validate(List<String> arg0) {
	// TODO Auto-generated method stub11111111
	return true;
    }
    
    
    @Override
    public boolean clientGenerated(Interface interfaze,  TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
	// TODO Auto-generated method stub
	 
         // FullyQualifiedJavaType type = topLevelClass.getType();
	 log.info( interfaze.toString() + topLevelClass +  introspectedTable  );
	 TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
	 
	 interfaze.addImportedType(new FullyQualifiedJavaType("com.sunwayland.core.generic.GenericMybatisDao"));
	 
	 String modalname = tableConfiguration.getDomainObjectName();
	 
	 String exampleType = introspectedTable.getExampleType();
	 
	// IntrospectedColumn column = introspectedTable.getPrimaryKeyColumns().get(0);
	 
	 String primaryKeyType = "Long" ; //column.getJavaProperty();
	 
	 
	 
//	 String primaryKeyType = introspectedTable.getPrimaryKeyType();
        
       //  String generatedKeytype = generatedKey.getType();
       
	 
        interfaze.addSuperInterface(new FullyQualifiedJavaType("GenericMybatisDao<"+modalname+ ", "+primaryKeyType +", "+exampleType+" >"));
	
	
	return super.clientGenerated(interfaze, topLevelClass,  introspectedTable);
    }

  

}
