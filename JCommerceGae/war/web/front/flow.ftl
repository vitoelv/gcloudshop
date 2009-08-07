<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="${keywords}" />
<meta name="Description" content="${description}" />
<!-- TemplateBeginEditable name="doctitle" -->
<title>${pageTitle}</title>
<!-- TemplateEndEditable --><!-- TemplateBeginEditable name="head" --><!-- TemplateEndEditable -->
<link rel="shortcut icon" href="favicon.ico" />
<link rel="icon" href="animated_favicon.gif" type="image/gif" />
<link href="style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/shoppingFlow.js"></script>

</head>
<body>
<#include "library/page_header.ftl">
<!--当前位置 start-->
<div class="block box">
 <div id="ur_here">
  <#include "library/ur_here.ftl">
 </div>
</div>
<!--当前位置 end-->
<div class="blank"></div>
<div class="block">
    <#if  step  ==  "cart"  >
  <!-- 购物车内容 -->
  <div class="flowBox">
    <h6><span>${lang.goodsList}</span></h6>
        <form id="formCart" name="formCart" method="post" action="flow.action">
           <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <tr>
              <th bgcolor="#ffffff">${lang.goodsName}</th>
							<#if  showGoodsAttribute  ==  1  >
              <th bgcolor="#ffffff">${lang.goodsAttr}</th>
              </#if>
              <#if  showMarketprice??  >
              <th bgcolor="#ffffff">${lang.marketPrices}</th>
              </#if>
              <th bgcolor="#ffffff">${lang.shopPrices}</th>
              <th bgcolor="#ffffff">${lang.number}</th>
              <th bgcolor="#ffffff">${lang.subtotal}</th>
              <th bgcolor="#ffffff">${lang.handle}</th>
            </tr>
            <#list goodsList as goods>
            <tr>
              <td bgcolor="#ffffff" align="center">
                <#if  goods.goodsId  >  0  >
                <#if  showGoodsThumb  ==  1  >
                  <a href="goods.action?id=${goods.goodsId}" target="_blank" class="f6">${goods.goodsName}</a>
                <#elseif  showGoodsThumb  ==  2  >
                  <a href="goods.action?id=${goods.goodsId}" target="_blank"><img src="${goods.goodsThumb}" border="0" title="${goods.goodsName?html}" /></a>
                <#else>
                  <a href="goods.action?id=${goods.goodsId}" target="_blank"><img src="${goods.goodsThumb}" border="0" title="${goods.goodsName?html}" /></a><br />
                  <a href="goods.action?id=${goods.goodsId}" target="_blank" class="f6">${goods.goodsName}</a>
                </#if>
                <#if  goods.parentId  >  0  >
                <span style="color:#FF0000">（${lang.accessories}）</span>
                </#if>
                <#if  goods.isGift  >  0  >
                <span style="color:#FF0000">（${lang.largess}）</span>
                </#if>
              <#else>
              ${goods.goodsName}
              </#if>
                </td>
							<#if  showGoodsAttribute  ==  1  >	
              <td bgcolor="#ffffff">${goods.goodsAttr|nl2br}</td>
							</#if>
              <#if  showMarketprice??  >
              <td align="right" bgcolor="#ffffff">${goods.marketPrice}</td>
              </#if>
              <td align="right" bgcolor="#ffffff">${goods.goodsPrice}</td>
              <td align="right" bgcolor="#ffffff">
                <#if  goods.goodsId  >  0  &&  goods.isGift??  ==  0  &&  goods.parentId??  ==  0  >
                <input type="text" name="goods_number[${goods.recId}]" id="goods_number_${goods.recId}" value="${goods.goodsNumber}" size="4" class="inputBg" style="text-align:center " />
                <#else>
                ${goods.goodsNumber}
                </#if>
              </td>
              <td align="right" bgcolor="#ffffff">${goods.subtotal}</td>
              <td align="center" bgcolor="#ffffff">
                <a href="javascript:if (confirm('${lang.dropGoodsConfirm}')) location.href='flow.action?step=drop_goods&amp;id=${goods.recId}'; " class="f6">${lang.drop}</a>
                <#if  smarty.session.userId  >  0  >
                <a href="javascript:if (confirm('${lang.dropGoodsConfirm}')) location.href='flow.action?step=drop_to_collect&amp;id=${goods.recId}'; " class="f6">${lang.dropToCollect}</a>
                </#if>            </td>
            </tr>
            </#list>
          </table>
          <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <tr>
              <td bgcolor="#ffffff">
              <#if  discount  >  0  >${yourDiscount}<br /></#if>
              ${shoppingMoney}<#if  showMarketprice??  >，${marketPriceDesc}</#if>
              </td>
              <td align="right" bgcolor="#ffffff">
                <input type="button" value="${lang.clearCart}" class="bnt_blue_1" onclick="location.href='flow.action?step=clear'" />
                <input name="submit" type="submit" class="bnt_blue_1" value="${lang.updateCart}" />
              </td>
            </tr>
          </table>
          <input type="hidden" name="step" value="update_cart" />
        </form>
        <table width="99%" align="center" border="0" cellpadding="5" cellspacing="0" bgcolor="#dddddd">
          <tr>
            <td bgcolor="#ffffff"><a href="./"><img src="images/continue.gif" alt="continue" /></a></td>
            <td bgcolor="#ffffff" align="right"><a href="flow.action?step=checkout"><img src="images/checkout.gif" alt="checkout" /></a></td>
          </tr>
        </table>
       <#if  smarty.session.userId  >  0  >
       <script type="text/javascript" src="js/transport.js"></script>

       <script type="text/javascript" charset="utf-8">
        function collect_to_flow(goodsId)
        {
          var goods        = new Object();
          var spec_arr     = new Array();
          var fittings_arr = new Array();
          var number       = 1;
          goods.spec     = spec_arr;
          goods.goods_id = goodsId;
          goods.number   = number;
          goods.parent   = 0;
          Ajax.call('flow.action?step=add_to_cart', 'goods=' + goods.toJSONString(), collect_to_flow_response, 'POST', 'JSON');
        }
        function collect_to_flow_response(result)
        {
          if (result.error > 0)
          {
            // 如果需要缺货登记，跳转
            if (result.error == 2)
            {
              if (confirm(result.message))
              {
                location.href = 'user.action?act=add_booking&id=' + result.goods_id;
              }
            }
            else if (result.error == 6)
            {
              if (confirm(result.message))
              {
                location.href = 'goods.action?id=' + result.goods_id;
              }
            }
            else
            {
              alert(result.message);
            }
          }
          else
          {
            location.href = 'flow.action';
          }
        }
      </script>
  </div>
	<div class="blank"></div>
  </#if>
  
  <#if  collectionGoods??  >
  <div class="flowBox">
    <h6><span>我的收藏</span></h6>
    <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
      <#list collectionGoods as goods>
          <tr>
            <td bgcolor="#ffffff"><a href="goods.action?id=${goods.goodsId}" class="f6">${goods.goodsName}</a></td>
            <td bgcolor="#ffffff" align="center" width="100"><a href="javascript:collect_to_flow(${goods.goodsId})" class="f6">立即购买</a></td>
          </tr>
      </#list>
        </table>
      </#if>
  </div>
	<div class="blank5"></div>
  </#if>
  
  <#if  favourableList??  >
  <div class="block">
    <div class="flowBox">
    <h6><span>优惠活动</span></h6>
        <#list favourableList as favourable>
        <form action="flow.action" method="post">
          <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <tr>
              <td align="right" bgcolor="#ffffff">${lang.favourableName}</td>
              <td bgcolor="#ffffff"><strong>${favourable.actName}</strong></td>
            </tr>
            <tr>
              <td align="right" bgcolor="#ffffff">${lang.favourablePeriod}</td>
              <td bgcolor="#ffffff">${favourable.startTime} --- ${favourable.endTime}</td>
            </tr>
            <tr>
              <td align="right" bgcolor="#ffffff">${lang.favourableRange}</td>
              <td bgcolor="#ffffff">${lang.farExt[favourable.actRange]}<br />
              ${favourable.actRangeDesc}</td>
            </tr>
            <tr>
              <td align="right" bgcolor="#ffffff">${lang.favourableAmount}</td>
              <td bgcolor="#ffffff">${favourable.formatedMinAmount} --- ${favourable.formatedMaxAmount}</td>
            </tr>
            <tr>
              <td align="right" bgcolor="#ffffff">${lang.favourableType}</td>
              <td bgcolor="#ffffff">
                <span class="STYLE1">${favourable.actTypeDesc}</span>
                <#if  favourable.actType  ==  0  >
                <#list favourable.gift as gift><br />
                  <input type="checkbox" value="${gift.id}" name="gift[]" />
                  <a href="goods.action?id=${gift.id}" target="_blank" class="f6">${gift.name}</a> [${gift.formatedPrice}]
                </#list>
              </#if>          </td>
            </tr>
            <#if  favourable.available??  >
            <tr>
              <td align="right" bgcolor="#ffffff">&nbsp;</td>
              <td align="center" bgcolor="#ffffff"><input type="image" src="images/bnt_cat.gif" alt="Add to cart"  border="0" /></td>
            </tr>
            </#if>
          </table>
          <input type="hidden" name="act_id" value="${favourable.actId}" />
          <input type="hidden" name="step" value="add_favourable" />
        </form>
        </#list>
  </div>
  </div>
  </#if>


        <#if  step  ==  "consignee"  >
        <!-- 开始收货人信息填写界面 -->
        <script type="text/javascript" src="js/region.js"></script>
<script type="text/javascript" src="js/utils.js"></script>

        <script type="text/javascript">
          region.isAdmin = false;
          <#list lang.flowJs as item>
          var ${key} = "${item}";
          </#list>

          {literal}
          onload = function() {
            if (!document.all)
            {
              document.forms['theForm'].reset();
            }
          }
          
        </script>
        <!-- 如果有收货地址，循环显示用户的收获地址 -->
        <!-- #list consigneeList?keys as sn> <!--#assign consignee = consigneeList[sn]--> 
        <#list consigneeList as consignee> <#assign sn = consignee_index> 
        <form action="flow.action" method="post" name="theForm" id="theForm" onsubmit="return checkConsignee(this)">
        <#include "library/consignee.ftl">
        </form>
        </#list>
        </#if>

        <#if  step  ==  "checkout"  >
        <form action="flow.action" method="post" name="theForm" id="theForm" onsubmit="return checkOrderForm(this)">
        <script type="text/javascript">
        var flow_no_payment = "${lang.flowNoPayment}";
        var flow_no_shipping = "${lang.flowNoShipping}";
        </script>
        <div class="flowBox">
        <h6><span>${lang.goodsList}</span><#if  allowEditCart??  ><a href="flow.action" class="f6">${lang.modify}</a></#if></h6>
        <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <tr>
              <th bgcolor="#ffffff">${lang.goodsName}</th>
              <th bgcolor="#ffffff">${lang.goodsAttr}</th>
              <#if  showMarketprice??  >
              <th bgcolor="#ffffff">${lang.marketPrices}</th>
              </#if>
              <th bgcolor="#ffffff"><#if  gbDeposit??  >${lang.deposit}<#else>${lang.shopPrices}</#if></th>
              <th bgcolor="#ffffff">${lang.number}</th>
              <th bgcolor="#ffffff">${lang.subtotal}</th>
            </tr>
            <#list goodsList as goods>
            <tr>
              <td bgcolor="#ffffff">
              <a href="goods.action?id=${goods.goodsId}" target="_blank" class="f6">${goods.goodsName}</a>
                <#if  (goods.parentId  !=  "")  >
                <span style="color:#FF0000">（${lang.accessories}）</span>
                <#elseif  goods.isGift??  >
                <span style="color:#FF0000">（${lang.largess}）</span>
                </#if>
              </td>
              <td bgcolor="#ffffff">${goods.goodsAttr}</td>
              <#if  showMarketprice??  >
              <td align="right" bgcolor="#ffffff">${goods.formatedMarketPrice}</td>
              </#if>
              <td bgcolor="#ffffff" align="right">${goods.formatedGoodsPrice}</td>
              <td bgcolor="#ffffff" align="right">${goods.goodsNumber}</td>
              <td bgcolor="#ffffff" align="right">${goods.formatedSubtotal}</td>
            </tr>
            </#list>
            <#if  !gbDeposit??  >
            <tr>
              <td bgcolor="#ffffff" colspan="7">
              <#if  discount??  >${yourDiscount}<br /></#if>
              ${shoppingMoney}<#if  showMarketprice??  >，${marketPriceDesc}</#if>
              </td>
            </tr>
            </#if>
          </table>
      </div>
      <div class="blank"></div>
      <div class="flowBox">
      <h6><span>${lang.consigneeInfo}</span><a href="flow.action?step=consignee" class="f6">${lang.modify}</a></h6>
      <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <tr>
              <td bgcolor="#ffffff">${lang.consigneeName}:</td>
              <td bgcolor="#ffffff">${consignee.consignee}</td>
              <td bgcolor="#ffffff">${lang.emailAddress}:</td>
              <td bgcolor="#ffffff">${consignee.email}</td>
            </tr>
            <#if  (total.realGoodsCount  >  0)  >
            <tr>
              <td bgcolor="#ffffff">${lang.detailedAddress}:</td>
              <td bgcolor="#ffffff">${consignee.address} </td>
              <td bgcolor="#ffffff">${lang.postalcode}:</td>
              <td bgcolor="#ffffff">${consignee.zipcode}</td>
            </tr>
            </#if>
            <tr>
              <td bgcolor="#ffffff">${lang.phone}:</td>
              <td bgcolor="#ffffff">${consignee.tel} </td>
              <td bgcolor="#ffffff">${lang.backupPhone}:</td>
              <td bgcolor="#ffffff">${consignee.mobile}</td>
            </tr>
             <#if  (total.realGoodsCount  >  0)  >
            <tr>
              <td bgcolor="#ffffff">${lang.signBuilding}:</td>
              <td bgcolor="#ffffff">${consignee.signBuilding} </td>
              <td bgcolor="#ffffff">${lang.deliverGoodsTime}:</td>
              <td bgcolor="#ffffff">${consignee.bestTime}</td>
            </tr>
            </#if>
          </table>
      </div>
     <div class="blank"></div>
    <#if  total.realGoodsCount  !=  0  >
    <div class="flowBox">
    <h6><span>${lang.shippingMethod}</span></h6>
    <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd" id="shippingTable">
            <tr>
              <th bgcolor="#ffffff" width="5%">&nbsp;</th>
              <th bgcolor="#ffffff" width="25%">${lang.name}</th>
              <th bgcolor="#ffffff">${lang.describe}</th>
              <th bgcolor="#ffffff" width="15%">${lang.fee}</th>
              <th bgcolor="#ffffff" width="15%">${lang.freeMoney}</th>
              <th bgcolor="#ffffff" width="15%">${lang.insureFee}</th>
            </tr>
            <#list shippingList as shipping>
            <tr>
              <td bgcolor="#ffffff" valign="top"><input name="shipping" type="radio" value="1" <#if  order.shippingId  ==  shipping.shippingId  >checked="true"</#if> supportCod="${shipping.supportCod}" insure="${shipping.insure}" onclick="selectShipping(this)" />
              </td>
              <td bgcolor="#ffffff" valign="top"><strong>${shipping.shippingName}</strong></td>
              <td bgcolor="#ffffff" valign="top">${shipping.shippingDesc}</td>
              <td bgcolor="#ffffff" align="right" valign="top">${shipping.formatShippingFee}</td>
              <td bgcolor="#ffffff" align="right" valign="top">${shipping.freeMoney}</td>
              <td bgcolor="#ffffff" align="right" valign="top"><#if  shipping.insure  !=  0  >${shipping.insureFormated}<#else>${lang.notSupportInsure}</#if></td>
            </tr>
            </#list>
            <tr>
              <td colspan="6" bgcolor="#ffffff" align="right"><label for="ECS_NEEDINSURE">
                <input name="need_insure" id="ECS_NEEDINSURE" type="checkbox"  onclick="selectInsure(this.checked)" value="1" <#if  order.needInsure??  >checked="true"</#if> <#if  insureDisabled??  >disabled="true"</#if>  />
                ${lang.needInsure} </label></td>
            </tr>
          </table>
    </div>
    <div class="blank"></div>
		<#else>
		<input name = "shipping" type="radio" value = "-1" checked="checked"  style="display:none"/>
		</#if>
    <div class="flowBox">
    <h6><span>${lang.paymentMethod}</span></h6>
    <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd" id="paymentTable">
            <tr>
              <th width="5%" bgcolor="#ffffff">&nbsp;</th>
              <th width="20%" bgcolor="#ffffff">${lang.name}</th>
              <th bgcolor="#ffffff">${lang.describe}</th>
              <th bgcolor="#ffffff" width="15%">${lang.payFee}</th>
            </tr>
            <#list paymentList as payment>
            <!-- 循环支付方式 -->
            <tr>
              <td valign="top" bgcolor="#ffffff"><input type="radio" name="payment" value="${payment.payId}" <#if  order.payId  ==  payment.payId  >checked</#if> isCod="${payment.isCod?string("yes", "no")}" onclick="selectPayment(this)" <#if  codDisabled??  &&  payment.isCod  >disabled="true"</#if>/></td>
              <td valign="top" bgcolor="#ffffff"><strong>${payment.payName}</strong></td>
              <td valign="top" bgcolor="#ffffff">${payment.payDesc}</td>
              <td align="right" bgcolor="#ffffff" valign="top">${payment.formatPayFee}</td>
            </tr>
            </#list>
          </table>
    </div>
    <div class="blank"></div>
          <#if  packList??  >
          <div class="flowBox">
          <h6><span>${lang.goodsPackage}</span></h6>
          <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd" id="packTable">
            <tr>
              <th width="5%" scope="col" bgcolor="#ffffff">&nbsp;</th>
              <th width="35%" scope="col" bgcolor="#ffffff">${lang.name}</th>
              <th width="22%" scope="col" bgcolor="#ffffff">${lang.price}</th>
              <th width="22%" scope="col" bgcolor="#ffffff">${lang.freeMoney}</th>
              <th scope="col" bgcolor="#ffffff">${lang.img}</th>
            </tr>
            <tr>
              <td valign="top" bgcolor="#ffffff"><input type="radio" name="pack" value="0" <#if  order.packId  ==  0  >checked="true"</#if> onclick="selectPack(this)" /></td>
              <td valign="top" bgcolor="#ffffff"><strong>${lang.noPack}</strong></td>
              <td valign="top" bgcolor="#ffffff">&nbsp;</td>
              <td valign="top" bgcolor="#ffffff">&nbsp;</td>
              <td valign="top" bgcolor="#ffffff">&nbsp;</td>
            </tr>
            <#list packList as pack>
            <tr>
              <td valign="top" bgcolor="#ffffff"><input type="radio" name="pack" value="${pack.packId}" <#if  order.packId  ==  pack.packId??  >checked="true"</#if> onclick="selectPack(this)" />
              </td>
              <td valign="top" bgcolor="#ffffff"><strong>${pack.packName}</strong></td>
              <td valign="top" bgcolor="#ffffff" align="right">${pack.formatPackFee}</td>
              <td valign="top" bgcolor="#ffffff" align="right">${pack.formatFreeMoney}</td>
              <td valign="top" bgcolor="#ffffff" align="center">
                  <#if  pack.packImg??  >
                  <a href="data/packimg/${pack.packImg}" target="_blank" class="f6">${lang.view}</a>
                  <#else>
                  ${lang.no}
                  </#if>
               </td>
            </tr>
            </#list>
          </table>
       </div>
			 <div class="blank"></div>
          </#if>
        
          <#if  cardList??  >
          <div class="flowBox">
          <h6><span>${lang.goodsCard}</span></h6>
          <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd" id="cardTable">
            <tr>
              <th bgcolor="#ffffff" width="5%" scope="col">&nbsp;</th>
              <th bgcolor="#ffffff" width="35%" scope="col">${lang.name}</th>
              <th bgcolor="#ffffff" width="22%" scope="col">${lang.price}</th>
              <th bgcolor="#ffffff" width="22%" scope="col">${lang.freeMoney}</th>
              <th bgcolor="#ffffff" scope="col">${lang.img}</th>
            </tr>
            <tr>
              <td bgcolor="#ffffff" valign="top"><input type="radio" name="card" value="0" <#if  order.cardId  ==  0  >checked="true"</#if> onclick="selectCard(this)" /></td>
              <td bgcolor="#ffffff" valign="top"><strong>${lang.noCard}</strong></td>
              <td bgcolor="#ffffff" valign="top">&nbsp;</td>
              <td bgcolor="#ffffff" valign="top">&nbsp;</td>
              <td bgcolor="#ffffff" valign="top">&nbsp;</td>
            </tr>
            <#list cardList as card>
            <tr>
              <td valign="top" bgcolor="#ffffff"><input type="radio" name="card" value="${card.cardId}" <#if  order.cardId  ==  card.cardId??  >checked="true"</#if> onclick="selectCard(this)"  />
              </td>
              <td valign="top" bgcolor="#ffffff"><strong>${card.cardName}</strong></td>
              <td valign="top" align="right" bgcolor="#ffffff">${card.formatCardFee}</td>
              <td valign="top" align="right" bgcolor="#ffffff">${card.formatFreeMoney}</td>
              <td valign="top" align="center" bgcolor="#ffffff">
                  <#if  card.cardImg??  >
                  <a href="data/cardimg/${card.cardImg}" target="_blank" class="f6">${lang.view}</a>
                  <#else>
                  ${lang.no}
                  </#if>
                </td>
            </tr>
            </#list>
            <tr>
              <td bgcolor="#ffffff"></td>
              <td bgcolor="#ffffff" valign="top"><strong>${lang.blessNote}:</strong></td>
              <td bgcolor="#ffffff" colspan="3"><textarea name="card_message" cols="60" rows="3" style="width:auto; border:1px solid #ccc;">${order.cardMessage}</textarea></td>
            </tr>
          </table>
        </div>
				<div class="blank"></div>
        </#if>
    
      <div class="flowBox">
    <h6><span>${lang.otherInfo}</span></h6>
      <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <#if  allowUseSurplus??  >
            <tr>
              <td width="20%" bgcolor="#ffffff"><strong>${lang.useSurplus}: </strong></td>
              <td bgcolor="#ffffff"><input name="surplus" type="text" class="inputBg" id="ECS_SURPLUS" size="10" value="${order.surplus}" onblur="changeSurplus(this.value)" <#if  disableSurplus??  >disabled="disabled"</#if> />
              ${lang.yourSurplus}${yourSurplus} <span id="ECS_SURPLUS_NOTICE" class="notice"></span></td>
            </tr>
            </#if>
            <#if  allowUseIntegral??  >
            <tr>
              <td bgcolor="#ffffff"><strong>${lang.useIntegral}</strong></td>
              <td bgcolor="#ffffff"><input name="integral" type="text" class="input" id="ECS_INTEGRAL" onblur="changeIntegral(this.value)" value="${order.integral}" size="10" />
              ${lang.canUseIntegral}:${yourIntegral} ${pointsName}，${lang.noworderCanIntegral}${orderMaxIntegral}  ${pointsName}. <span id="ECS_INTEGRAL_NOTICE" class="notice"></span></td>
            </tr>
            </#if>
            <#if  allowUseBonus??  >
            <tr>
              <td bgcolor="#ffffff"><strong>${lang.useBonus}:</strong></td>
              <td bgcolor="#ffffff">
                ${lang.selectBonus}
                <select name="bonus" onchange="changeBonus(this.value)" id="ECS_BONUS" style="border:1px solid #ccc;">
                  <option value="0" <#if  order.bonusId  ==  0  >selected</#if>>${lang.pleaseSelect}</option>
                  <#list bonusList as bonus>
                  <option value="${bonus.bonusId}" <#if  order.bonusId  ==  bonus.bonusId??  >selected</#if>>${bonus.typeName}[${bonus.bonusMoneyFormated}]</option>
                  </#list>
                </select>

                ${lang.inputBonusNo}
                <input name="bonus_sn" type="text" class="inputBg" size="15" />
                <input name="validate_bonus" type="button" class="bnt_blue_1" value="${lang.validateBonus}" onclick="validateBonus(document.forms['theForm'].elements['bonus_sn'].value)" style="vertical-align:middle;" />
              </td>
            </tr>
            </#if>
            <#if  invContentList??  >
            <tr>
              <td bgcolor="#ffffff"><strong>${lang.invoice}:</strong>
                <input name="need_inv" type="checkbox"  class="input" id="ECS_NEEDINV" onclick="changeNeedInv()" value="1" <#if  order.needInv??  >checked="true"</#if> />
              </td>
              <td bgcolor="#ffffff">
                <#if  invTypeList??  >
                ${lang.invoiceType}
                <select name="inv_type" id="ECS_INVTYPE" <#if  order.needInv  !=  1  >disabled="true"</#if> onchange="changeNeedInv()" style="border:1px solid #ccc;">
                TODO: htmlOptions CLAUSE</select>
                </#if>
                ${lang.invoiceTitle}
                <input name="inv_payee" type="text"  class="input" id="ECS_INVPAYEE" size="20" <#if  !order.needInv??  >disabled="true"</#if> value="${order.invPayee}" onblur="changeNeedInv()" />
                ${lang.invoiceContent}
              <select name="inv_content" id="ECS_INVCONTENT" <#if  order.needInv  !=  1  >disabled="true"</#if>  onchange="changeNeedInv()" style="border:1px solid #ccc;">

                TODO: htmlOptions CLAUSE

                </select></td>
            </tr>
            </#if>
            <tr>
              <td valign="top" bgcolor="#ffffff"><strong>${lang.orderPostscript}:</strong></td>
              <td bgcolor="#ffffff"><textarea name="postscript" cols="80" rows="3" id="postscript" style="border:1px solid #ccc;">${order.postscript}</textarea></td>
            </tr>
            <#if  howOosList??  >
            <tr>
              <td bgcolor="#ffffff"><strong>${lang.bookingProcess}:</strong></td>
              <td bgcolor="#ffffff"><#list how_oos_list as how_oos_name>
                <label>
                <input name="how_oos" type="radio" value="${howOosId}" <#if  order.howOos  ==  howOosId??  >checked</#if> onclick="changeOOS(this)" />
                ${howOosName}</label>
                </#list>
              </td>
            </tr>
            </#if>
          </table>
    </div>
    <div class="blank"></div>
    <div class="flowBox">
    <h6><span>${lang.feeTotal}</span></h6>
          <#include "library/order_total.ftl">
           <div align="center" style="margin:8px auto;">
            <input type="image" src="images/bnt_subOrder.gif" />
            <input type="hidden" name="step" value="done" />
            </div>
    </div>
    </form>
        </#if>

        <#if  step  ==  "done"  >
        <!-- 订单提交成功 -->
        <div class="flowBox" style="margin:30px auto 70px auto;">
         <h6 style="text-align:center; height:30px; line-height:30px;">${lang.rememberOrderNumber}: <font style="color:red">${order.orderSn}</font></h6>
          <table width="99%" align="center" border="0" cellpadding="15" cellspacing="0" bgcolor="#fff" style="border:1px solid #ddd; margin:20px auto;" >
            <tr>
              <td align="center" bgcolor="#FFFFFF">
              <#if  order.shippingName??  >${lang.selectShipping}: <strong>${order.shippingName}</strong>，</#if>${lang.selectPayment}: <strong>${order.payName}</strong>。${lang.orderAmount}: <strong>${total.amountFormated}</strong>
              </td>
            </tr>
            <tr>
              <td align="center" bgcolor="#FFFFFF">${order.payDesc}</td>
            </tr>
            <#if  payOnline??  >
            <!-- 如果是线上支付则显示支付按钮 -->
            <tr>
              <td align="center" bgcolor="#FFFFFF">${payOnline}</td>
            </tr>
            </#if>
          </table>
          <#if  virtualCard??  >
          <div style="text-align:center;overflow:hidden;border:1px solid #E2C822;background:#FFF9D7;margin:10px;padding:10px 50px 30px;">
          <#list virtualCard as vgoods>
            <h3 style="color:#2359B1; font-size:12px;">${vgoods.goodsName}</h3>
            <#list vgoods.info as card>
              <ul style="list-style:none;padding:0;margin:0;clear:both">
              <#if  card.cardSn??  >
              <li style="margin-right:50px;float:left;">
              <strong>${lang.cardSn}:</strong><span style="color:red;">${card.cardSn}</span>
              </li></#if>
              <#if  card.cardPassword??  >
              <li style="margin-right:50px;float:left;">
              <strong>${lang.cardPassword}:</strong><span style="color:red;">${card.cardPassword}</span>
              </li></#if>
              <#if  card.endDate??  >
              <li style="float:left;">
              <strong>${lang.endDate}:</strong>${card.endDate}
              </li></#if>
              </ul>
            </#list>
          </#list>
          </div>
          </#if>
          <p style="text-align:center; margin-bottom:20px;">${orderSubmitBack}</p>
        </div>
        </#if>
        <#if  step  ==  "login"  >
        <script type="text/javascript" src="js/utils.js"></script>

        <script type="text/javascript">
        <#list lang.flowLoginRegister as item>
          var ${key} = "${item}";
        </#list>

        {literal}
        function checkLoginForm(frm) {
          if (Utils.isEmpty(frm.elements['username'].value)) {
            alert(username_not_null);
            return false;
          }

          if (Utils.isEmpty(frm.elements['password'].value)) {
            alert(password_not_null);
            return false;
          }

          return true;
        }

        function checkSignupForm(frm) {
          if (Utils.isEmpty(frm.elements['username'].value)) {
            alert(username_not_null);
            return false;
          }

          if (Utils.trim(frm.elements['username'].value).match(/^\s*$|^c:\\con\\con$|[%,\'\*\"\s\t\<\>\&\\]/))
          {
            alert(username_invalid);
            return false;
          }

          if (Utils.isEmpty(frm.elements['email'].value)) {
            alert(email_not_null);
            return false;
          }

          if (!Utils.isEmail(frm.elements['email'].value)) {
            alert(email_invalid);
            return false;
          }

          if (Utils.isEmpty(frm.elements['password'].value)) {
            alert(password_not_null);
            return false;
          }

          if (frm.elements['password'].value.length < 6) {
            alert(password_lt_six);
            return false;
          }

          if (frm.elements['password'].value != frm.elements['confirm_password'].value) {
            alert(password_not_same);
            return false;
          }
          return true;
        }
        
        </script>
        <!-- 开始用户登录注册界面 -->
        <div class="flowBox">
        <table width="99%" align="center" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr>
            <td width="50%" valign="top" bgcolor="#ffffff">
            <h6><span>用户登录：</span></h6>
            <form action="flow.action?step=login" method="post" name="loginForm" id="loginForm" onsubmit="return checkLoginForm(this)">
                <table width="90%" border="0" cellpadding="8" cellspacing="1" bgcolor="#B0D8FF" class="table">
                  <tr>
                    <td bgcolor="#ffffff"><div align="right"><strong>${lang.username}</strong></div></td>
                    <td bgcolor="#ffffff"><input name="username" type="text" class="inputBg" id="username" /></td>
                  </tr>
                  <tr>
                    <td bgcolor="#ffffff"><div align="right"><strong>${lang.password}</strong></div></td>
                    <td bgcolor="#ffffff"><input name="password" class="inputBg" type="password" id="password" /></td>
                  </tr>
                  <#if  enabledLoginCaptcha??  >
                  <tr>
                    <td bgcolor="#ffffff"><div align="right"><strong>${lang.commentCaptcha}:</strong></div></td>
                    <td bgcolor="#ffffff"><input type="text" size="8" name="captcha" class="inputBg" />
                    <img src="captcha.action?is_login=1&${rand}" alt="captcha" style="vertical-align: middle;cursor: pointer;" onClick="this.src='captcha.action?is_login=1&'+Math.random()" /> </td>
                  </tr>
                  </#if>
                  <tr>
                    <td bgcolor="#ffffff" colspan="2" align="center"><a href="user.action?act=get_password" class="f6">${lang.forgotPassword}</a></td>
                  </tr>
                  <tr>
                    <td bgcolor="#ffffff" colspan="2"><div align="center">
                        <input type="submit" class="bnt_blue" name="login" value="${lang.forthwithLogin}" />
                        <#if  anonymousBuy  ==  1  >
                        <input type="button" class="bnt_blue_2" value="${lang.directShopping}" onclick="location.href='flow.action?step=consignee&amp;direct_shopping=1'" />
                        </#if>
                        <input name="act" type="hidden" value="signin" />
                      </div></td>
                  </tr>
                </table>
              </form>

              </td>
            <td valign="top" bgcolor="#ffffff">
            <h6><span>用户注册：</span></h6>
            <form action="flow.action?step=login" method="post" name="registerForm" id="registerForm" onsubmit="return checkSignupForm(this)">
               <table width="90%" border="0" cellpadding="8" cellspacing="1" bgcolor="#B0D8FF" class="table">
                  <tr>
                    <td bgcolor="#ffffff"><div align="right"><strong>${lang.username}</strong></div></td>
                    <td bgcolor="#ffffff"><input name="username" type="text" class="inputBg" id="username" /></td>
                  </tr>
                  <tr>
                    <td bgcolor="#ffffff"><div align="right"><strong>${lang.emailAddress}</strong></div></td>
                    <td bgcolor="#ffffff"><input name="email" type="text" class="inputBg" id="email" /></td>
                  </tr>
                  <tr>
                    <td bgcolor="#ffffff"><div align="right"><strong>${lang.password}</strong></div></td>
                    <td bgcolor="#ffffff"><input name="password" class="inputBg" type="password" id="password" /></td>
                  </tr>
                  <tr>
                    <td bgcolor="#ffffff"><div align="right"><strong>${lang.confirmPassword}</strong></div></td>
                    <td bgcolor="#ffffff"><input name="confirm_password" class="inputBg" type="password" id="confirm_password" /></td>
                  </tr>
                  <#if  enabledRegisterCaptcha??  >
                  <tr>
                    <td bgcolor="#ffffff"><div align="right"><strong>${lang.commentCaptcha}:</strong></div></td>
                    <td bgcolor="#ffffff"><input type="text" size="8" name="captcha" class="inputBg" />
                    <img src="captcha.action?${rand}" alt="captcha" style="vertical-align: middle;cursor: pointer;" onClick="this.src='captcha.action?'+Math.random()" /> </td>
                  </tr>
                  </#if>
                  <tr>
                    <td colspan="2" bgcolor="#ffffff" align="center">
                        <input type="submit" name="register" class="bnt_blue_1" value="${lang.forthwithRegister}" />
                        <input name="act" type="hidden" value="signup" />
                    </td>
                  </tr>
                </table>
              </form>
              </td>
          </tr>
          <#if  needRechooseGift??  >
          <tr>
            <td colspan="2" align="center" style="border-top:1px #ccc solid; padding:5px; color:red;">${lang.giftRemainder}</td>
          </tr>
          </#if>
        </table>
        </div>
        <!-- 结束用户登录注册界面 -->
        </#if>




</div>
<div class="blank5"></div>
<!--帮助-->
<div class="block">
  <div class="box">
   <div class="helpTitBg clearfix">
    <#include "library/help.ftl">
   </div>
  </div>
</div>
<div class="blank"></div>
<!--帮助-->
<!--友情链接 start-->
<#if  imgLinks??  ||  txtLinks??  >
<div id="bottomNav" class="box">
 <div class="box_1">
  <div class="links clearfix">
    <#list imgLinks as link>
    <a href="${link.url}" target="_blank" title="${link.name}"><img src="${link.logo}" alt="${link.name}" border="0" /></a>
    </#list>
    <#if  txtLinks??  >
    <#list txtLinks as link>
    [<a href="${link.url}" target="_blank" title="${link.name}">${link.name}</a>]
    </#list>
    </#if>
  </div>
 </div>
</div>
</#if>
<!--友情链接 end-->
<div class="blank"></div>
<#include "library/page_footer.ftl">
</body>
</html>
