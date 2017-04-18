package com.ai.apaas.common.service.impl.bidao;

import java.util.HashMap;
import java.util.Map;
import com.ai.apaas.busiframe.util.ExecuteSqlUtil;
import com.ai.appframe2.bo.DataContainer;

public class InteractDAO{
	
<#list properties as pro>
	<#if pro.isBiMain == "true">
	public static DataContainer[] query${pro.simpleTableName}ByIntactId(String center, String intactId) throws Exception{
		String connName = "JOUR"+center;
		StringBuilder sql = new StringBuilder(1000);
		sql.append("SELECT * FROM ${pro.tableName}");
		sql.append(" WHERE INTACT_ID = :INTACT_ID ");
		Map map = new HashMap();
		map.put("INTACT_ID", intactId);
		return (DataContainer[])ExecuteSqlUtil.getDcs(connName, sql.toString(), map);
	}
	
	public static DataContainer[] queryHis${pro.simpleTableName}ByIntactId(String center, String intactId) throws Exception{
		String connName = "JOUR"+center;
		StringBuilder sql = new StringBuilder(1000);
		String yearMonth = "20"+intactId.substring(2,6);
		sql.append("SELECT * FROM ${pro.tableName}_"+yearMonth);
		sql.append(" WHERE INTACT_ID = :INTACT_ID ");
		Map map = new HashMap();
		map.put("INTACT_ID", intactId);
		return (DataContainer[])ExecuteSqlUtil.getDcs(connName, sql.toString(), map);
	}
	<#elseif pro.isOmMain = "true">
	public static DataContainer[] query${pro.simpleTableName}ByOrderId(String center, String orderId) throws Exception{
		String connName = "JOUR"+center;
		StringBuilder sql = new StringBuilder(1000);
		sql.append("SELECT * FROM ${pro.tableName}");
		sql.append(" WHERE ORDER_ID = :ORDER_ID ");
		Map map = new HashMap();
		map.put("ORDER_ID", orderId);
		return (DataContainer[])ExecuteSqlUtil.getDcs(connName, sql.toString(), map);
	}
	
	public static DataContainer[] queryHis${pro.simpleTableName}ByOrderId(String center, String orderId) throws Exception{
		String connName = "JOUR"+center;
		StringBuilder sql = new StringBuilder(1000);
		String yearMonth = "20"+orderId.substring(2,6);
		sql.append("SELECT * FROM ${pro.tableName}_"+yearMonth);
		sql.append(" WHERE ORDER_ID = :ORDER_ID ");
		Map map = new HashMap();
		map.put("ORDER_ID", orderId);
		return (DataContainer[])ExecuteSqlUtil.getDcs(connName, sql.toString(), map);
	}
	<#elseif pro.isOm = "true">
	public static DataContainer[] query${pro.simpleTableName}ByLineId(String center, String lineId) throws Exception{
		String connName = "JOUR"+center;
		StringBuilder sql = new StringBuilder(1000);
		sql.append("SELECT * FROM ${pro.tableName}");
		sql.append(" WHERE ORDER_LINE_ID = :ORDER_LINE_ID ");
		Map map = new HashMap();
		map.put("ORDER_LINE_ID", lineId);
		return (DataContainer[])ExecuteSqlUtil.getDcs(connName, sql.toString(), map);
	}
	
	public static DataContainer[] queryHis${pro.simpleTableName}ByLineId(String center, String lineId) throws Exception{
		String connName = "JOUR"+center;
		StringBuilder sql = new StringBuilder(1000);
		String yearMonth = "20"+lineId.substring(2,6);
		sql.append("SELECT * FROM ${pro.tableName}_"+yearMonth);
		sql.append(" WHERE ORDER_LINE_ID = :ORDER_LINE_ID ");
		Map map = new HashMap();
		map.put("ORDER_LINE_ID", lineId);
		return (DataContainer[])ExecuteSqlUtil.getDcs(connName, sql.toString(), map);
	}
	<#else>
	public static DataContainer[] query${pro.simpleTableName}ByIntactItemId(String center, String intactItemId) throws Exception{
		String connName = "JOUR"+center;
		StringBuilder sql = new StringBuilder(1000);
		sql.append("SELECT * FROM ${pro.tableName}");
		sql.append(" WHERE INTACT_ITEM_ID = :INTACT_ITEM_ID ");
		Map map = new HashMap();
		map.put("INTACT_ITEM_ID", intactItemId);
		return (DataContainer[])ExecuteSqlUtil.getDcs(connName, sql.toString(), map);
	}
	
	public static DataContainer[] queryHis${pro.simpleTableName}ByIntactItemId(String center, String intactItemId) throws Exception{
		String connName = "JOUR"+center;
		StringBuilder sql = new StringBuilder(1000);
		String yearMonth = "20"+intactItemId.substring(2,6);
		sql.append("SELECT * FROM ${pro.tableName}_"+yearMonth);
		sql.append(" WHERE INTACT_ITEM_ID = :INTACT_ITEM_ID ");
		Map map = new HashMap();
		map.put("INTACT_ITEM_ID", intactItemId);
		return (DataContainer[])ExecuteSqlUtil.getDcs(connName, sql.toString(), map);
	}
	</#if>
	
</#list>
}