package ${package};

import java.util.HashMap;
import java.util.Map;
import com.ai.ipaas.busiframe.util.PartTool;
import com.ai.appframe2.common.DataStructInterface;
import com.ai.ipaas.busiframe.service.SheetPart;
import com.ai.apaas.common.cisf.domain.base.entity.BaseEntity;
import com.ai.apaas.common.cisf.domain.base.tools.SeqManager;
import com.ai.apaas.common.cisf.domain.base.data.OrderBaseConst.EntityEnum;
import com.ai.ipaas.bc.common.string.StringFormatter;

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
		${setPk}
	}

	public ${className}(Map<String, String> map)
	{
		this.original.putAll(map);
		this.memory.putAll(map);
		this.isNew = false;
	}
	
	public ${className}(DataStructInterface dc) throws Exception
	{
		Map<String, String> map = PartTool.transformBo(dc);
		this.original.putAll(map);
		this.memory.putAll(map);
		this.isNew = false;
	}
	
	public ${className}(SheetPart sheet)
	{
		if(sheet == null || sheet.size() <= 0){
			return;
		}
		Map<String, String> map = sheet.get(0);
		this.original.putAll(map);
		this.memory.putAll(map);
		this.isNew = false;
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