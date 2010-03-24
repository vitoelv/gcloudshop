<#if ( priceGrade??  ) >
<div class="box">
 <div class="box_1">
  <h3><span>${lang.priceGrade}</span></h3>
  <div class="boxCenterList RelaArticle">
    <#list priceGrade as grade>
    <#if ( grade.selected??  ) >
    <img src="images/alone.gif" style=" margin-right:8px;"><font class="f1 f5">${grade.start} - ${grade.end} <#if ( grade.goodsNum??  ) >(${grade.goodsNum})</#if></font><br />
    <#else>
    <a href="${grade.url}">${grade.start} - ${grade.end}</a> <#if ( grade.goodsNum??  ) >(${grade.goodsNum})</#if><br />
    </#if>
    </#list>
  </div>
 </div>
</div>
<div class="blank5"></div>
</#if>
