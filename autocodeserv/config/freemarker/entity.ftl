package ${package};

import java.util.Map;

import com.asiainfo.veris.framework.cisf.domain.base.entity.BaseEntity;
import com.asiainfo.veris.framework.cisf.consts.EntityEnum;
import com.asiainfo.veris.framework.cisf.util.StringFormatter;

public class ${className} extends BaseEntity{

<#list properties as pro>
	public static final ${pro.proType} <@upperFC>${pro.colName}</@upperFC> = "${pro.colName}";
</#list>
	
<#list properties as pro>
	public void set<@upperFC>${pro.proName}</@upperFC>(${pro.proType} ${pro.proName}){
		this.memory.put(<@upperFC>${pro.colName}</@upperFC>, ${pro.proName});
	}
	
	public ${pro.proType} get<@upperFC>${pro.proName}</@upperFC>(){
		return this.memory.get(<@upperFC>${pro.colName}</@upperFC>);
	}
	
</#list>

	public ${className}()
	{
		super();
	}

	public ${className}(Map<String, String> data)
	{
		this.original.putAll(data);
		this.memory.putAll(data);
	}
	
	public ${className} clone()
	{
		${className} <@lowerFC>${className}</@lowerFC> = new ${className}(this.memory);
		return <@lowerFC>${className}</@lowerFC>;
	}
	
	public String toString()
	{
		return StringFormatter.toStringV(this.memory);
	}
	
	public String toStringH()
	{
		return StringFormatter.toStringH(this.memory);
	}
	
	public EntityEnum getEntityName() {
		return EntityEnum.${entityEnum};
	}
}