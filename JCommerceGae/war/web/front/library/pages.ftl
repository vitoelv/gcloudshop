<!--翻页 start-->
<form name="selectPageForm" action="${smarty.server.PHP_SELF}" method="get">
<#if  pager.styleid  ==  0  >
<div id="pager">
  ${lang.pager_1}${pager.recordCount}${lang.pager_2}${lang.pager_3}${pager.pageCount}${lang.pager_4} <span> <a href="${pager.pageFirst}">${lang.pageFirst}</a> <a href="${pager.pagePrev}">${lang.pagePrev}</a> <a href="${pager.pageNext}">${lang.pageNext}</a> <a href="${pager.pageLast}">${lang.pageLast}</a> </span>
    <#list pager.search?keys as key> <#assign item = pager.search.get(key)>
    <input type="hidden" name="${key}" value="${item}" />
    </#list>
    <select name="page" id="page" onchange="selectPage(this)">
    <#list pager.array?keys as key>
<#assign val = pager.array.get(key)>
<option value="${key}" <#if pager.page == key>selected</#if> >${val}</option>
</#list>

    </select>
</div>
<#else>

<!--翻页 start-->
 <div id="pager" class="pagebar">
  <span class="f_l f6" style="margin-right:10px;">${lang.pager_1}<b>${pager.recordCount}</b> ${lang.pager_2}</span>
  <#if  pager.pageFirst??  ><a href="${pager.pageFirst}">${lang.pageFirst} ...</a></#if>
  <#if  pager.pagePrev??  ><a class="prev" href="${pager.pagePrev}">${lang.pagePrev}</a></#if>
  <#if  pager.pageCount  !=  1  >
    <#list pager.pageNumber?keys as key> <#assign item = pager.pageNumber.get(key)>
      <#if  pager.page  ==  key  >
      <span class="page_now">${key}</span>
      <#else>
      <a href="${item}">[${key}]</a>
      </#if>
    </#list>
  </#if>

  <#if  pager.pageNext??  ><a class="next" href="${pager.pageNext}">${lang.pageNext}</a></#if>
  <#if  pager.pageLast??  ><a class="last" href="${pager.pageLast}">...${lang.pageLast}</a></#if>
  <#if  pager.pageKbd??  >
    <#list pager.search?keys as key> <#assign item = pager.search.get(key)>
    <input type="hidden" name="${key}" value="${item}" />
    </#list>
    <kbd style="float:left; margin-left:8px; position:relative; bottom:3px;"><input type="text" name="page" onkeydown="if(event.keyCode==13)selectPage(this)" size="3" class="B_blue" /></kbd>
    </#if>
</div>
<!--翻页 END-->

</#if>
</form>
<script type="Text/Javascript" language="JavaScript">
<!--

function selectPage(sel)
{
  sel.form.submit();
}

//-->
</script>
