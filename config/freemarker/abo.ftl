package ${package};

import java.util.List;
import com.ai.appframe2.bo.DataContainer;
import abo.ABOEnum;
import abo.AbstractABO;
import abo.Attribute;

public class ${className} extends AbstractABO{

<#list properties as pro>
	public static final ${pro.proType} <@upperFC>${pro.colName}</@upperFC> = "${pro.colName}";
</#list>
	
<#list properties as pro>
	public void set<@upperFC>${pro.proName}</@upperFC>(${pro.proType} ${pro.proName}){
		setAttrValue("${pro.proName}", ${pro.proName});
	}
	
	public ${pro.proType} get<@upperFC>${pro.proName}</@upperFC>(){
		return getAttrValue("${pro.proName}");
	}
</#list>

	public ${className}()
	{
		super();
		<#list properties as pro>
		addAttr("${pro.proName}", new Attribute(${pro.colName}));
		</#list>
	}

	@Override
	public ABOEnum getABOName() {
		return ABOEnum.${tableName};
	}
}