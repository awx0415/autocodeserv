<#macro buildNode part count>
	<#assign count = count + 1>
	<div class="list list2" style="display:none;">
		<#list part.extends as extend>
	        <#if extend.isExtend == "true">
	        	<#if extend.index % 2 == 0>
    				<ul class="tr J_tableTrigger even clearfix" id="${extend.id}" onclick="slideTo('${extend.id}')">
    			<#else>
    				<ul class="tr J_tableTrigger clearfix" id="${extend.id}" onclick="slideTo('${extend.id}')">
    			</#if>
    		<#else>
    			<#if extend.index % 2 == 0>
            		<ul class="tr even clearfix">
            	<#else>
            		<ul class="tr clearfix">
            	</#if>
            </#if>
	            <li class="td-1">
	            	<#list 1..count as t>
	            		&nbsp;&nbsp;&nbsp;&nbsp;
	            	</#list>
	            	<#if extend.isExtend == "true">
                		<span class="icon-wrap J_tableIcon"></span>
                	</#if>
                	${extend.parameterName}
	            </li>
	            <li class="td-2">${extend.parameterType}</li>
	            <li class="td-3">${extend.isMust}</li>
	            <li class="td-4">${extend.desc}</li>
	        </ul>
	        <#if extend.isExtend == "true">
            	<@buildNode part=extend count=count/> 
            </#if>
	    </#list>
    </div>
</#macro>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="../css/style.css"/>
    <script src="../js/jquery-1.11.1.min.js"></script>
    <script src="../js/util.js"></script>
</head>
<body>
<div class="wrap">
    <div class="J_FloatContainer">
        <div class="top-bar">
            <div class="wrap-inner">
                <div class="crumbs">
                    <a href="#">接口文档中心</a> &gt; <a href="#">订单中心</a> &gt; 服务
                </div>
            </div>
        </div>
    </div>

    <div class="wrap-inner block-docs-wrap J_FloatContainer">

        <div class="docs-right">
            <div class="mtl">
                <h2 class="mtl-main" style="font-size:22px;margin-bottom:10px;">服务名： <span style="color:gray;font-size:20px;">${serviceName}</span></h2>
                <p class="mtl-desc">类名：${className}<br>
                	描述：${serviceDesc}
                </p>
            </div>
            <div class="doc-detail-bd J_apiDetailBd" id="bd">

                <h1 id="s0" onclick="slideTo('s0')"><i class="triangle"></i>公共参数</h1>
                <div class="J_sCon" style="display:none;">
                    <dd>请求地址：</dd>
                    <table border="1" cellpadding="0" cellspacing="0">
                        <tbody><tr>
                            <th width="20%">环境</th>
                            <th width="40%">HTTP请求地址</th>
                            <th width="40%">HTTPS请求地址</th>
                        </tr>
                        
                        </tbody></table>
                    <dd>公共请求参数：</dd>
                    <table border="1" cellpadding="0" cellspacing="0">
                        <thead>
                        <tr>
                            <th width="18%">名称</th>
                            <th width="10%">类型</th>
                            <th width="10%">是否必须</th>
                            <th>描述</th>
                        </tr>
                        </thead>
                        <tbody>
          
                        </tbody>
                    </table>
                </div>

                <h1 id="s1" onclick="slideTo('s1')"><i class="triangle open"></i>请求参数</h1>
                <div class="J_sCon">
                    <table border="1" cellpadding="0" cellspacing="0" class="table">
                        <thead>
                        <tr>
                            <th width="25%">名称</th>
                            <th width="20%">类型</th>
                            <th width="15%">是否必须</th>
                            <th width="40%">描述</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<#list inParts as inPart>
	                        	<#if inPart.isExtend == "true">
	                        		<#if inPart.index % 2 == 0>
	                        			<tr id="${inPart.id}" class="J_tableTr J_tableTrigger" onclick="slideTo('${inPart.id}')">
	                        		<#else>
	                        			<tr id="${inPart.id}" class="J_tableTr even J_tableTrigger" onclick="slideTo('${inPart.id}')">
	                        		</#if>
	                        	<#else>
	                        		<#if inPart.index % 2 == 0>
		                            	<tr>
		                            <#else>
		                            	<tr class="even">
		                            </#if>
		                        </#if>
	                                <td>
	                                	<#if inPart.isExtend == "true">
	                                		<span class="icon-wrap J_tableIcon"></span>
	                                	</#if>
	                                	${inPart.parameterName}
	                                </td>
	                                <td>${inPart.parameterType}</td>
	                                <td>${inPart.isMust}</td>
	                                <td>${inPart.desc}</td>
	                            </tr>
	                            <#if inPart.isExtend == "true">
		                            <tr class="open-wrap" style="display:none;">
			                            <td class="J_tableOpentd" colspan="5">
			                                <div class="list list1">
			                                	<#list inPart.extends as extend>
			                                		<#if extend.isExtend == "true">
			                                			<#if inPart.index % 2 == 0>
			                                				<ul class="tr J_tableTrigger even clearfix" id="${extend.id}" onclick="slideTo('${extend.id}')">
			                                			<#else>
			                                				<ul class="tr J_tableTrigger clearfix" id="${extend.id}" onclick="slideTo('${extend.id}')">
			                                			</#if>
			                                		<#else>
			                                			<#if extend.index % 2 ==0>
					                                		<ul class="tr clearfix">
					                                	<#else>
					                                		<ul class="tr even clearfix">
					                                	</#if>
					                                </#if>
				                                        <li class="td-1">
				                                        	<#if extend.isExtend == "true">
						                                		<span class="icon-wrap J_tableIcon"></span>
						                                	</#if>
						                                	${extend.parameterName}
				                                        </li>
				                                        <li class="td-2">${extend.parameterType}</li>
				                                        <li class="td-3">${extend.isMust}</li>
				                                        <li class="td-4">${extend.desc}</li>
				                                    </ul>
				                                    <#if extend.isExtend == "true">
				                                    	<@buildNode part=extend count=1/> 
				                                    </#if>
			                                    </#list>
			                                </div>
			                            </td>
			                        </tr>
	                            </#if>
                           	</#list>
                        </tbody>
                    </table>
                </div>

                <h1 id="s2" onclick="slideTo('s2')"><i class="triangle open"></i>响应参数</h1>
                <div class="J_sCon">
                    <table border="1" cellpadding="0" cellspacing="0" class="table">
                        <thead>
                        <tr>
                            <th width="25%">名称</th>
	                        <th width="20%">类型</th>
	                        <th width="15%">是否必须</th>
	                        <th width="40%">描述</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<#list outParts as outPart>
                        		<#if outPart.isExtend == "true">
	                        		<#if outPart.index % 2 == 0>
	                        			<tr id="${outPart.id}" class="J_tableTr J_tableTrigger" onclick="slideTo('${outPart.id}')">
	                        		<#else>
	                        			<tr id="${outPart.id}" class="J_tableTr even J_tableTrigger" onclick="slideTo('${outPart.id}')">
	                        		</#if>
	                        	<#else>
	                        		<#if outPart.index % 2 == 0>
		                            	<tr>
		                            <#else>
		                            	<tr class="even">
		                            </#if>
		                        </#if>
		                            <td>
		                            	<#if outPart.isExtend == "true">
	                                		<span class="icon-wrap J_tableIcon"></span>
	                                	</#if>
		                            	${outPart.parameterName}
		                            </td>
		                            <td>${outPart.parameterType}</td>
		                            <td>${outPart.isMust}</td>
		                            <td>${outPart.desc}</td>
		                        </tr>
		                        <#if outPart.isExtend == "true">
		                            <tr class="open-wrap" style="display:none;">
			                            <td class="J_tableOpentd" colspan="5">
			                                <div class="list list1">
			                                	<#list outPart.extends as extend>
					                                <#if extend.isExtend == "true">
			                                			<#if outPart.index % 2 == 0>
			                                				<ul class="tr J_tableTrigger even clearfix" id="${extend.id}" onclick="slideTo('${extend.id}')">
			                                			<#else>
			                                				<ul class="tr J_tableTrigger clearfix" id="${extend.id}" onclick="slideTo('${extend.id}')">
			                                			</#if>
			                                		<#else>
			                                			<#if extend.index % 2 ==0>
					                                		<ul class="tr clearfix">
					                                	<#else>
					                                		<ul class="tr even clearfix">
					                                	</#if>
					                                </#if>
				                                        <li class="td-1">
				                                        	<#if extend.isExtend == "true">
						                                		<span class="icon-wrap J_tableIcon"></span>
						                                	</#if>
						                                	${extend.parameterName}
				                                        </li>
				                                        <li class="td-2">${extend.parameterType}</li>
				                                        <li class="td-3">${extend.isMust}</li>
				                                        <li class="td-4">${extend.desc}</li>
				                                    </ul>
				                                    <#if extend.isExtend == "true">
				                                    	<@buildNode part=extend count=1/> 
				                                    </#if>
			                                    </#list>
			                                </div>
			                            </td>
			                        </tr>
	                            </#if>
	                        </#list>
                        </tbody>
                    </table>
                </div>


                </div>

            </div>
        </div>
        <!-- 右侧主内容 -->
    </div>
</div>
</body>
</html>