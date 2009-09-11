<script type="text/javascript">
var process_request = "${lang.processRequest}";
</script>
<div class="block clearfix">
 <div class="f_l"><a href="home.action" name="top"><img src="../images/logo.gif" /></a></div>
 <div class="f_r log">
   <ul>
   <li class="userInfo">
   <script type="text/javascript" src="js/transport.js"></script>
<script type="text/javascript" src="js/utils.js"></script>

   <font id="ECS_MEMBERZONE"><#include "member_info.ftl"> </font>
   </li>
	 <#if  navigatorList.top??  >
   <li id="topNav" class="clearfix">
    <#list navigatorList.top as nav>
            <a href="${nav.url}" <#if  nav.opennew  ==  1  > target="_blank" </#if>>${nav.name}</a>
            <#if  nav_has_next??  >
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
  <a href="home.action"<#if  navigatorList.config.index  ==  1  > class="cur"</#if>>${lang.home}<span></span></a>
  <#list navigatorList.middle as nav>
  <a href="${nav.url}" <#if  nav.opennew  ==  1  >target="_blank" </#if> <#if  nav.active  ==  1  > class="cur"</#if>>${nav.name}<span></span></a>
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
    <#if  searchkeywords??  >
   ${lang.hotSearch} ：
   <#list searchkeywords as val>
   <a href="search.action?keywords=${val}">${val}</a>
   </#list>
   </#if>
  </div>
  <form id="searchForm" name="searchForm" method="get" action="search.action" onSubmit="return checkSearchForm()" class="f_r"  style="_position:relative; top:5px;">
   <select name="category" id="category" class="B_input">
      <option value="0">${lang.allCategory}</option>
      ${categoryList}
    </select>
   <input name="keywords" type="text" id="keyword" value="${searchKeywords}" class="B_input" style="width:110px;"/>
   <input name="imageField" type="submit" value="" class="go" style="cursor:pointer;" />
   <a href="search.action?act=advanced_search">${lang.advancedSearch}</a>
   </form>
</div>
<!--search end-->



