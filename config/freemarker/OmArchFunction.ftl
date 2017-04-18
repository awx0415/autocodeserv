<#list properties as pro>
	ins<@upperFC>${pro.proName}</@upperFC>();
</#list>

<#list properties as pro>
	private void ins<@upperFC>${pro.proName}</@upperFC>() throws Exception
	{
		String table = "${pro.omTableName}";
		
		if (isIns(table) == false)
        {
            return;
        }
		
		DataContainer[] datas = OmDao.qryOmInfoById(connNameJour, table, orderLineId);

        if (ArrayUtil.isEmpty(datas))
        {
            return;
        }
        
        String sql = "";
        String action = "";
        int size = datas.length;
        
        for(int i = 0; i < size; i++)
        {
        	Map<String, String> data = BoTool.transformBo(datas[i]);
        	
        	// 得到操作类型
        	action = getAction(data);
        	String subscriberInsId = data.get("SUBSCRIBER_INS_ID");
        	String partitionId = StrUtil.getPartition4ById(subscriberInsId);
        	data.put("PARTITION_ID", partitionId);
        	
        	if(action.equals(CisfConst.ACTION_ADD))
        	{
        		sql = OmLineInsSql.get<@upperFC>${pro.proName}</@upperFC>();
        		
        		ExecuteSqlUtil.execute(connNameFile, sql, data);
        	}
        	else if(action.equals(CisfConst.ACTION_UPD))
        	{
        		// 备份
                setBakData(data);

                sql = OmLineBakSql.get<@upperFC>${pro.proName}</@upperFC>(yyyymm);

                ExecuteSqlUtil.execute(connNameFile, sql, data);

                // 更新
                sql = OmLineUpdSql.get<@upperFC>${pro.proName}</@upperFC>();

                ExecuteSqlUtil.execute(connNameFile, sql, data);
        	}
        	else if(action.equals(CisfConst.ACTION_DEL))
        	{
        		// 备份
                setBakData(data);

                sql = OmLineBakSql.get<@upperFC>${pro.proName}</@upperFC>(yyyymm);

                ExecuteSqlUtil.execute(connNameFile, sql, data);

                // 删除
                sql = OmLineDelSql.get<@upperFC>${pro.proName}</@upperFC>();

                ExecuteSqlUtil.execute(connNameFile, sql, data);
        	}
        	else if(action.equals(CisfConst.ACTION_INHERIT))
        	{
        		continue;
        	}
        	else
        	{
//        		BizException.bizerr(OrderException.CRM_ORDER_617, table, action);
        	}
        }
	}
</#list>