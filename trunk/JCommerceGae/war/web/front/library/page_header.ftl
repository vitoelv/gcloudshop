<script type="text/javascript">
var process_request = "<@s.text name="process_request"/>";
</script>
<div class="block clearfix">
 <div class="f_l"><a href="index.action" name="top"><img src="${template_root}/images/logo.gif" /></a></div>
 <div class="f_r log">
   <ul>
   <li class="userInfo">
<script type="text/javascript" src="js/transport.js"></script>
<script type="text/javascript" src="js/utils.js"></script>
   <font id="ECS_MEMBERZONE">
<#include "member_info.ftl">
   </font>
   </li>
   <#if navigator_list.top??>
   <li id="topNav" class="clearfix">
    <#list navigator_list.top as nav>
            <a href="${nav.url}" <#if nav.opennew> target="_blank" </#if>>${nav.name}</a>
            <#if nav_has_next>
             |
            </#if>
    </#list>
    <div class="topNavR"></div>
   </li>
   </#if>
   </ul>
 </div>
</div>
<div  class="blank"></div>
<div id="mainNav" class="clearfix">
  <#list navigator_list.middle as nav>
  <a href="${nav.url}" <#if nav.opennew>target="_blank" </#if> <#if nav.active> class="cur"</#if>>${nav.name}<span></span></a>
  </#list>
</div>
<!--search start-->
<div id="search"  class="clearfix">
  <div class="keys f_l">
   <script type="text/javascript">
    <!--
    function checkSearchForm()
    {
        if(document.getElementById('keyword').value)
        {
            return true;
        }
        else
        {
            alert("请输入搜索关键词！");
            return false;
        }
    }
    -->
    </script>
   <#if searchkeywords??>
   <@s.text name="hot_search"/> ：
   <#list searchkeywords as val>
   <a href="search.action?keywords=${val}">${val}</a>
   </#list>
   </#if>
  </div>
  <form id="searchForm" name="searchForm" method="get" action="search.action" onSubmit="return checkSearchForm()" class="f_r"  style="_position:relative; top:5px;">
   <select name="category" id="category" class="B_input">
      <option value="0"><@s.text name="all_category"/></option>
      <#list category_list as category>
      <option value="${category.id}">${category.name}</option>
      </#list>
    </select>
   <input name="keywords" type="text" id="keyword" value="${search_keywords}" class="B_input" style="width:110px;"/>
   <input name="imageField" type="submit" value="" class="go" style="cursor:pointer;" />
   <a href="search.action?act=advanced_search"><@s.text name="advanced_search"/></a>
   </form>
</div>
<!--search end-->



