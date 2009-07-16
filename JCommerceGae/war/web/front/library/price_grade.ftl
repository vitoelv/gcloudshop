<#if price_grade??>
<div class="box">
 <div class="box_1">
  <h3><span>{$lang.price_grade}</span></h3>
  <div class="boxCenterList RelaArticle">
    <#list price_grade as grade>
    <#if grade.selected>
    <img src="${template_root}/images/alone.gif" style=" margin-right:8px;"><font class="f1 f5">${grade.start} - ${grade.end} <#if 0<grade.goodsNum>(${grade.goodsNum})</#if></font><br />
    <#else>
    <a href="grade.action?id=${grade.id}">${grade.start} - ${grade.end}</a> <#if 0<grade.goodsNum>(${grade.goodsNum})</#if><br />
    </#if>
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>
