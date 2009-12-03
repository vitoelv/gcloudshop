<div class="flowBox">
<h6><span>${lang.consigneeInfo}</span></h6>
<script type="text/javascript" src="js/utils.js"></script>
<script type="text/javascript" src="js/transport.js"></script>

<table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
  <#if  (realGoodsCount  >  0)  >
  <!-- 购物车中存在实体商品显示国家和地区 -->
  <tr>
    <td bgcolor="#ffffff">${lang.countryProvince}:</td>
    <td colspan="3" bgcolor="#ffffff">
    <select name="country" id="selCountries_${sn}" onchange="region.changed(this, 1, 'selProvinces_${sn}')" style="border:1px solid #ccc;">
        <option value="0">${lang.pleaseSelect}${nameOfRegion[0]}</option>
        <#list countryList as country>
        <option value="${country.regionId}" <#if  consignee.country  ==  country.regionId  >selected</#if>>${country.regionName}</option>
        </#list>
      </select>
      <select name="province" id="selProvinces_${sn}" onchange="region.changed(this, 2, 'selCities_${sn}')" style="border:1px solid #ccc;">
        <option value="0">${lang.pleaseSelect}${nameOfRegion[1]}</option>
        <#list provinceList[sn] as province>
        <option value="${province.regionId}" <#if  consignee.province  ==  province.regionId  >selected</#if>>${province.regionName}</option>
        </#list>
      </select>
      <select name="city" id="selCities_${sn}" onchange="region.changed(this, 3, 'selDistricts_${sn}')" style="border:1px solid #ccc;">
        <option value="0">${lang.pleaseSelect}${nameOfRegion[2]}</option>
        <#list cityList[sn] as city>
        <option value="${city.regionId}" <#if  consignee.city  ==  city.regionId  >selected</#if>>${city.regionName}</option>
        </#list>
      </select>
      <select name="district" id="selDistricts_${sn}" <#if  !districtList[sn]??  >style="display:none"</#if> style="border:1px solid #ccc;">
        <option value="0">${lang.pleaseSelect}${nameOfRegion[3]}</option>
        <#list districtList[sn] as district>
        <option value="${district.regionId}" <#if  consignee.district  ==  district.regionId  >selected</#if>>${district.regionName}</option>
        </#list>
      </select>
    ${lang.requireField} </td>
  </tr>
  </#if>
  <tr>
    <td bgcolor="#ffffff">${lang.consigneeName}:</td>
    <td bgcolor="#ffffff"><input name="consignee" type="text" class="inputBg" id="consignee_${sn}" value="${consignee.consignee}" />
    ${lang.requireField} </td>
    <td bgcolor="#ffffff">${lang.emailAddress}:</td>
    <td bgcolor="#ffffff"><input name="email" type="text" class="inputBg"  id="email_${sn}" value="${consignee.email}" />
    ${lang.requireField}</td>
  </tr>
  <#if  (realGoodsCount  >  0)  >
  <!-- 购物车中存在实体商品显示详细地址以及邮政编码 -->
  <tr>
    <td bgcolor="#ffffff">${lang.detailedAddress}:</td>
    <td bgcolor="#ffffff"><input name="address" type="text" class="inputBg"  id="address_${sn}" value="${consignee.address}" />
    ${lang.requireField}</td>
    <td bgcolor="#ffffff">${lang.postalcode}:</td>
    <td bgcolor="#ffffff"><input name="zipcode" type="text" class="inputBg"  id="zipcode_${sn}" value="${consignee.zipcode}" /></td>
  </tr>
  </#if>
  <tr>
    <td bgcolor="#ffffff">${lang.phone}:</td>
    <td bgcolor="#ffffff"><input name="tel" type="text" class="inputBg"  id="tel_${sn}" value="${consignee.tel}" />
    ${lang.requireField}</td>
    <td bgcolor="#ffffff">${lang.backupPhone}:</td>
    <td bgcolor="#ffffff"><input name="mobile" type="text" class="inputBg"  id="mobile_${sn}" value="${consignee.mobile}" /></td>
  </tr>
  <#if  (realGoodsCount  >  0)  >
  <!-- 购物车中存在实体商品显示最佳送货时间及标志行建筑 -->
  <tr>
    <td bgcolor="#ffffff">${lang.signBuilding}:</td>
    <td bgcolor="#ffffff"><input name="sign_building" type="text" class="inputBg"  id="sign_building_${sn}" value="${consignee.signBuilding}" /></td>
    <td bgcolor="#ffffff">${lang.deliverGoodsTime}:</td>
    <td bgcolor="#ffffff"><input name="best_time" type="text"  class="inputBg" id="best_time_${sn}" value="${consignee.bestTime}" /></td>
  </tr>
  </#if>
  <tr>
    <td colspan="4" align="center" bgcolor="#ffffff">
    <input type="submit" name="Submit" class="bnt_blue_2" value="${lang.shippingAddress}" />
      <!--#if  smarty.session.userId  >  0  &&  consignee.addressId??  >  0  -->
      <#if consignee.addressId?? >
      <!-- 如果登录了，显示删除按钮 -->
      <input name="button" type="button" onclick="if (confirm('${lang.dropConsigneeConfirm}')) location.href='flow.action?step=drop_consignee&amp;id=${consignee.addressId}'"  class="bnt_blue" value="${lang.drop}" />
      </#if>
      <input type="hidden" name="step" value="consignee" />
      <input type="hidden" name="act" value="checkout" />
      <input name="address_id" type="hidden" value="${consignee.addressId}" />
      </td>
  </tr>
</table>
</div>