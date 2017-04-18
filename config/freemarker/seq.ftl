package com.ai.apaas.common.cisf.domain.base.tools;

public class SeqManager {
<#list seqs as seq>
	public static String get${seq.seqName}Seq(){
		return String.valueOf(Math.round(Math.random()*1000000));
	}
	
</#list>

}