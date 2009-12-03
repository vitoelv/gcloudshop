
=== ${lang.cmtLang.get("cmt_invalid_comments")} ===
<#-- this will output correctly

<#list lang.cmtLang.keySet() as key> 
	<#assign item = lang.cmtLang.get(key)>
	var ${key} = "${item}";
</#list>
-->

<#-- this will output extra keys -->
<#list lang.cmtLang?keys as key> 
	<#assign item = lang.cmtLang.get(key)>
	var ${key} = "${item}";
</#list>
