package ${package};

import com.ailk.bof.data.tradedata.BaseTradeData;
import com.ailk.common.data.IData;
import com.ailk.common.data.impl.DataMap;

public class ${className} extends BaseEntity{

<#list properties as pro>
	private ${pro.proType} ${pro.proName};
</#list>
	
<#list properties as pro>
	public void set<@upperFC>${pro.proName}</@upperFC>(${pro.proType} ${pro.proName}){
		this.${pro.proName}=${pro.proName};
	}
	
	public ${pro.proType} get<@upperFC>${pro.proName}</@upperFC>(){
		return this.${pro.proName};
	}
</#list>

	public ${className}()
	{
	
	}

	public ${className}(IData data)
	{
		<#list properties2 as pro>
		this.${pro.proName} = data.getString("${pro.colName}");
		</#list>
	}
	
	public ${className} clone()
	{
		${className} <@lowerFC>${className}</@lowerFC> = new ${className}();
		<#list properties2 as pro>
		<@lowerFC>${className}</@lowerFC>.set<@upperFC>${pro.proName}</@upperFC>(this.get<@upperFC>${pro.proName}</@upperFC>());
		</#list>
		
		return <@lowerFC>${className}</@lowerFC>;
	}
	
	public IData toData()
	{
		IData data = new DataMap();
		<#list properties2 as pro>
		data.put("${pro.colName}", this.${pro.proName});
		</#list>
		
		return data;
	}
	
	public String getTableName()
	{
		return "${tableName}";
	}
	
	public String toString()
	{
		IData data = new DataMap();
		data.put(getTableName(), this.toData());
		return data.toString();
	}
}