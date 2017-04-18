private void ${methodName}(IData mainTrade, String syncId, String syncDay) throws Exception
{
	StringBuilder sql = new StringBuilder(300);
	
	String tradeId = mainTrade.getString("TRADE_ID");
	String acceptMonth = mainTrade.getString("ACCEPT_MONTH");
	String cancelTag = mainTrade.getString("CANCEL_TAG");
	
	IData param = new DataMap();
	param.put("TRADE_ID", tradeId);
	param.put("ACCEPT_MONTH", acceptMonth);
	IDataset subTrades =  Dao.qryByCode("${BOF_SYNC_TAB_NAME}", "SEL_TRADE", param, Route.getJourDbDefault());
	
	if(IDataUtil.isNotEmpty(subTrades))
	{
		if("0".equals(cancelTag))
    	{
			//填充其他值
    		for(int i = 0, size = subTrades.size(); i < size; i++)
    		{
    			IData subTrade = subTrades.getData(i);
    			subTrade.put("SYNC_DAY", syncDay);
    			subTrade.put("SYNC_SEQUENCE", syncId);
    			
    			String modifyTag = subTrade.getString("MODIFY_TAG");
    			modifyTag = ("0".equals(modifyTag) || "1".equals(modifyTag)) ? modifyTag : "2"; //除了0/1外，其他都转成2
    			subTrade.put("MODIFY_TAG", syncDay);
    			
    			subTrade.put("PARTITION_ID", StrUtil.getPartition4ById(subTrade.getString("${PARTITION_COL}")));
    		}
    		Dao.insert("${SYNC_TABLE_NAME}", subTrades, Route.getJourDbDefault());
    	}
    	else
    	{
    		IDataset tmpList = DataHelper.distinct(subTrades, "USER_ID", null);
    		for(int i = 0, size = tmpList.size(); i < size; i++)
    		{
    			IData tmp = tmpList.getData(i);
    			String userId = tmp.getString("USER_ID");
    		}
    	}
	}
}