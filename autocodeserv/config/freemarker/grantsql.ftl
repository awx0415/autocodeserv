<#list properties as pro>
	create or replace synonym ${table_name} for ucr_${domain}.${table_name};
</#list>