<script type="text/javascript" src="js/utils.js"></script>
<script type="text/javascript" src="js/transport.js"></script>

<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
  <tr>
    <td bgcolor="#ffffff">
      ${lang.countryProvince}:
      <select name="country" id="selCountries_${sn}" onchange="region.changed(this, 1, 'selProvinces_${sn}')" style="border:1px solid #ccc;">
        <option value="0">${lang.pleaseSelect}</option>
        <#list countryList as country>
        <option value="${country.regionId}" <#if  choose.country  ==  country.regionId??  >selected</#if>>${country.regionName}</option>
        </#list>
      </select>
      <select name="province" id="selProvinces_${sn}" onchange="region.changed(this, 2, 'selCities_${sn}')" style="border:1px solid #ccc;">
        <option value="0">${lang.pleaseSelect}</option>
        <#list provinceList.${sn} as province>
        <option value="${province.regionId}" <#if  choose.province  ==  province.regionId??  >selected</#if>>${province.regionName}</option>
        </#list>
      </select>
      <select name="city" id="selCities_${sn}" onchange="region.changed(this, 3, 'selDistricts_${sn}')" style="border:1px solid #ccc;">
        <option value="0">${lang.pleaseSelect}</option>
        <#list cityList.${sn} as city>
        <option value="${city.regionId}" <#if  choose.city  ==  city.regionId??  >selected</#if>>${city.regionName}</option>
        </#list>
      </select>
      <select name="district" id="selDistricts_${sn}" <#if  !districtList.sn??  >style="display:none"</#if> style="border:1px solid #ccc;">
        <option value="0">${lang.pleaseSelect}</option>
        <#list districtList.${sn} as district>
        <option value="${district.regionId}" <#if  choose.district  ==  district.regionId??  >selected</#if>>${district.regionName}</option>
        </#list>
      </select> <input type="submit" name="Submit" class="bnt_blue_2"  value="${lang.searchShip}" />
      <input type="hidden" name="act" value="viewship" />
    </td>
  </tr>
</table>

<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
  <tr>
    <th width="20%" bgcolor="#ffffff">${lang.name}</th>
    <th bgcolor="#ffffff">${lang.describe}</th>
    <th width="40%" bgcolor="#ffffff">${lang.fee}</th>
    <th width="15%" bgcolor="#ffffff">${lang.insureFee}</th>
  </tr>
  <#list shippingList as shipping>
  <tr>
    <td valign="top" bgcolor="#ffffff"><strong>${shipping.shippingName}</strong></td>
    <td valign="top" bgcolor="#ffffff" >${shipping.shippingDesc}</td>
    <td valign="top" bgcolor="#ffffff">${shipping.fee}</td>
    <td align="center" valign="top" bgcolor="#ffffff">
      <#if  shipping.insure  !=  0  >
      ${shipping.insureFormated}
      <#else>
      ${lang.notSupportInsure}
      </#if>    </td>
  </tr>
  </#list>
</table>