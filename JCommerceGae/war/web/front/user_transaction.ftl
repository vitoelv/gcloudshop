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
<script type="text/javascript" src="js/user.js"></script>

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
<div class="block clearfix">
  <!--left start-->
  <div class="AreaL">
    <div class="box">
     <div class="box_1">
      <div class="userCenterBox">
        <#include "library/user_menu.ftl">
      </div>
     </div>
    </div>
  </div>
  <!--left end-->
  <!--right start-->
  <div class="AreaR">
    <div class="box">
     <div class="box_1">
      <div class="userCenterBox boxCenterList clearfix" style="_height:1%;">
         <!-- 用户信息界面 start-->
         <#if  action  ==  'profile'  >
         <script type="text/javascript" src="js/utils.js"></script>

        <script type="text/javascript">
          <#list lang.profileJs as item>
            var ${key} = "${item}";
          </#list>
        </script>
      <h5><span>${lang.profile}</span></h5>
      <div class="blank"></div>
     <form name="formEdit" action="user.action" method="post" onSubmit="return userEdit()">
      <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
                <tr>
                  <td width="28%" align="right" bgcolor="#FFFFFF">${lang.birthday}： </td>
                  <td width="72%" align="left" bgcolor="#FFFFFF"> TODO: htmlSelectDate CLAUSE </td>
                </tr>
                <tr>
                  <td width="28%" align="right" bgcolor="#FFFFFF">${lang.sex}： </td>
                  <td width="72%" align="left" bgcolor="#FFFFFF"><input type="radio" name="sex" value="0" <#if  profile.sex==0??  >checked="checked"</#if> />
                    ${lang.secrecy}&nbsp;&nbsp;
                    <input type="radio" name="sex" value="1" <#if  profile.sex==1??  >checked="checked"</#if> />
                    ${lang.male}&nbsp;&nbsp;
                    <input type="radio" name="sex" value="2" <#if  profile.sex==2??  >checked="checked"</#if> />
                  ${lang.female}&nbsp;&nbsp; </td>
                </tr>
                <tr>
                  <td width="28%" align="right" bgcolor="#FFFFFF">${lang.email}： </td>
                  <td width="72%" align="left" bgcolor="#FFFFFF"><input name="email" type="text" value="${profile.email}" size="25" class="inputBg" /></td>
                </tr>
                <tr>
                  <td width="28%" align="right" bgcolor="#FFFFFF">${lang.otherMsn}：</td>
                  <td width="72%" align="left" bgcolor="#FFFFFF"><input name="other[msn]" type="text" value="${profile.msn}" class="inputBg" />
                  </td>
                </tr>
                <tr>
                  <td width="28%" align="right" bgcolor="#FFFFFF">${lang.otherQq}：</td>
                  <td width="72%" align="left" bgcolor="#FFFFFF"><input name="other[qq]" type="text" value="${profile.qq}" class="inputBg" />
                  </td>
                </tr>
                <tr>
                  <td width="28%" align="right" bgcolor="#FFFFFF">${lang.otherOfficePhone}：</td>
                  <td width="72%" align="left" bgcolor="#FFFFFF"><input name="other[office_phone]" type="text" value="${profile.officePhone}" class="inputBg" />
                  </td>
                </tr>
                <tr>
                  <td width="28%" align="right" bgcolor="#FFFFFF">${lang.otherHomePhone}：</td>
                  <td width="72%" align="left" bgcolor="#FFFFFF"><input name="other[home_phone]" type="text" value="${profile.homePhone}" class="inputBg" />
                  </td>
                </tr>
                <tr>
                  <td width="28%" align="right" bgcolor="#FFFFFF">${lang.otherMobilePhone}：</td>
                  <td width="72%" align="left" bgcolor="#FFFFFF"><input name="other[mobile_phone]" type="text" value="${profile.mobilePhone}" class="inputBg" />
                  </td>
                </tr>
                <tr>
                  <td colspan="2" align="center" bgcolor="#FFFFFF"><input name="act" type="hidden" value="act_edit_profile" />
                    <input name="submit" type="submit" value="${lang.confirmEdit}" class="bnt_blue_1" style="border:none;" />
                  </td>
                </tr>
       </table>
    </form>
     <form name="formPassword" action="user.action" method="post" onSubmit="return editPassword()" >
     <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
        <tr>
          <td width="28%" align="right" bgcolor="#FFFFFF">${lang.oldPassword}：</td>
          <td width="76%" align="left" bgcolor="#FFFFFF"><input name="old_password" type="password" size="25"  class="inputBg" /></td>
        </tr>
        <tr>
          <td width="28%" align="right" bgcolor="#FFFFFF">${lang.newPassword}：</td>
          <td align="left" bgcolor="#FFFFFF"><input name="new_password" type="password" size="25"  class="inputBg" /></td>
        </tr>
        <tr>
          <td width="28%" align="right" bgcolor="#FFFFFF">${lang.confirmPassword}：</td>
          <td align="left" bgcolor="#FFFFFF"><input name="comfirm_password" type="password" size="25"  class="inputBg" /></td>
        </tr>
        <tr>
          <td colspan="2" align="center" bgcolor="#FFFFFF"><input name="act" type="hidden" value="act_edit_password" />
            <input name="submit" type="submit" class="bnt_blue_1" style="border:none;" value="${lang.confirmEdit}" />
          </td>
        </tr>
      </table>
    </form>
     </#if>
     <!--#用户信息界面 end-->
     <#if  action  ==  'bonus'  >
      <script type="text/javascript">
        <#list lang.profileJs as item>
          var ${key} = "${item}";
        </#list>
      </script>
      <h5><span>${lang.labelBonus}</span></h5>
      <div class="blank"></div>
       <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
        <tr>
          <th align="center" bgcolor="#FFFFFF">${lang.bonusSn}</th>
          <th align="center" bgcolor="#FFFFFF">${lang.bonusName}</th>
          <th align="center" bgcolor="#FFFFFF">${lang.bonusAmount}</th>
          <th align="center" bgcolor="#FFFFFF">${lang.minGoodsAmount}</th>
          <th align="center" bgcolor="#FFFFFF">${lang.bonusEndDate}</th>
          <th align="center" bgcolor="#FFFFFF">${lang.bonusStatus}</th>
        </tr>
        <#if  bonus??  >
        <#list bonus as item>
        <tr>
          <td align="center" bgcolor="#FFFFFF">${item.bonusSn|default:N/A}</td>
          <td align="center" bgcolor="#FFFFFF">${item.typeName}</td>
          <td align="center" bgcolor="#FFFFFF">${item.typeMoney}</td>
          <td align="center" bgcolor="#FFFFFF">${item.minGoodsAmount}</td>
          <td align="center" bgcolor="#FFFFFF">${item.useEnddate}</td>
          <td align="center" bgcolor="#FFFFFF">${item.status}</td>
        </tr>
        </#list>
        <#else>
        <tr>
          <td colspan="6" bgcolor="#FFFFFF">${lang.userBonusEmpty}</td>
        </tr>
        </#if>
      </table>
      <div class="blank5"></div>
      <#include "library/pages.ftl">
      <div class="blank5"></div>
      <h5><span>${lang.addBonus}</span></h5>
      <div class="blank"></div>
      <form name="addBouns" action="user.action" method="post" onSubmit="return addBonus()">
        <div style="padding: 15px;">
        ${lang.bonusNumber}
          <input name="bonus_sn" type="text" size="30" class="inputBg" />
          <input type="hidden" name="act" value="act_add_bonus" class="inputBg" />
          <input type="submit" class="bnt_blue_1" style="border:none;" value="${lang.addBonus}" />
        </div>
      </form>
    </#if>
   <!--用户红包结束-->
      <!--#订单列表界面 start-->
       <#if  action  ==  'orderList'  >
       <h5><span>${lang.labelOrder}</span></h5>
       <div class="blank"></div>
       <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr align="center">
            <td bgcolor="#ffffff">${lang.orderNumber}</td>
            <td bgcolor="#ffffff">${lang.orderAddtime}</td>
            <td bgcolor="#ffffff">${lang.orderMoney}</td>
            <td bgcolor="#ffffff">${lang.orderStatus}</td>
            <td bgcolor="#ffffff">${lang.handle}</td>
          </tr>
          <#list orders as item>
          <tr>
            <td align="center" bgcolor="#ffffff"><a href="user.action?act=order_detail&order_id=${item.orderId}" class="f6">${item.orderSn}</a></td>
            <td align="center" bgcolor="#ffffff">${item.orderTime}</td>
            <td align="right" bgcolor="#ffffff">${item.totalFee}</td>
            <td align="center" bgcolor="#ffffff">${item.orderStatus}</td>
            <td align="center" bgcolor="#ffffff"><font class="f6">${item.handler}</font></td>
          </tr>
          </#list>
          </table>
        <div class="blank5"></div>
       <#include "library/pages.ftl">
       <div class="blank5"></div>
       <h5><span>${lang.mergeOrder}</span></h5>
       <div class="blank"></div>
        <script type="text/javascript">
        <#list lang.mergeOrderJs as item>
          var ${key} = "${item}";
        </#list>
        </script>
        <form action="user.action" method="post" name="formOrder" onsubmit="return mergeOrder()">
          <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <tr>
              <td width="22%" align="right" bgcolor="#ffffff">${lang.firstOrder}:</td>
              <td width="12%" align="left" bgcolor="#ffffff"><select name="to_order">
              <option value="0">${lang.select}</option>

                  TODO: htmlOptions CLAUSE

                </select></td>
              <td width="19%" align="right" bgcolor="#ffffff">${lang.secondOrder}:</td>
              <td width="11%" align="left" bgcolor="#ffffff"><select name="from_order">
              <option value="0">${lang.select}</option>

                  TODO: htmlOptions CLAUSE

                </select></td>
              <td width="36%" bgcolor="#ffffff">&nbsp;<input name="act" value="merge_order" type="hidden" />
              <input type="submit" name="Submit"  class="bnt_blue_1" style="border:none;"  value="${lang.mergeOrder}" /></td>
            </tr>
            <tr>
              <td bgcolor="#ffffff">&nbsp;</td>
              <td colspan="4" align="left" bgcolor="#ffffff">${lang.mergeOrderNotice}</td>
            </tr>
          </table>
        </form>
       </#if>
      <!--#订单列表界面 end-->
       <!--#包裹状态查询界面 start-->
      <#if  action  ==  'trackPackages'  >
        <h5><span>${lang.labelTrackPackages}</span></h5>
        <div class="blank"></div>
        <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd" id="order_table">
        <tr align="center">
          <td bgcolor="#ffffff">${lang.orderNumber}</td>
          <td bgcolor="#ffffff">${lang.handle}</td>
        </tr>
        <#list orders as item>
        <tr>
          <td align="center" bgcolor="#ffffff"><a href="user.action?act=order_detail&order_id=${item.orderId}">${item.orderSn}</a></td>
          <td align="center" bgcolor="#ffffff">${item.queryLink}</td>
        </tr>
        </#list>
      </table>
      <script>
      var query_status = '${lang.queryStatus}';
      var ot = document.getElementById('order_table');
      for (var i = 1; i < ot.rows.length; i++)
      {
        var row = ot.rows[i];
        var cel = row.cells[1];
        cel.getElementsByTagName('a').item(0).innerHTML = query_status;
      }
      </script>
      <div class="blank5"></div>
      <#include "library/pages.ftl">
      </#if>
    <!--#包裹状态查询界面 end-->
     <!-- ==========订单详情页面,包括：订单状态，商品列表，费用总计，收货人信息，支付方式，其它信息========== -->
      <#if  action  ==  orderDetail  >
        <h5><span>${lang.orderStatus}</span></h5>
        <div class="blank"></div>
         <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
        <tr>
          <td width="15%" align="right" bgcolor="#ffffff">${lang.detailOrderSn}：</td>
          <td align="left" bgcolor="#ffffff">${order.orderSn}
          <#if  order.extensionCode  ==  "groupBuy"  ><a href="./group_buy.action?act=view&id=${order.extensionId}"><strong>${lang.orderIsGroupBuy}</strong></a></#if>  <a href="user.action?act=message_list&order_id=${order.orderId}" class="f6">[${lang.businessMessage}]</a></td>
        </tr>
        <tr>
          <td align="right" bgcolor="#ffffff">${lang.detailOrderStatus}：</td>
          <td align="left" bgcolor="#ffffff">${order.orderStatus}&nbsp;&nbsp;&nbsp;&nbsp;${order.confirmTime}</td>
        </tr>
        <tr>
          <td align="right" bgcolor="#ffffff">${lang.detailPayStatus}：</td>
          <td align="left" bgcolor="#ffffff">${order.payStatus}&nbsp;&nbsp;&nbsp;&nbsp;<#if  order.orderAmount  >  0  >${order.payOnline}</#if>${order.payTime}</td>
        </tr>
        <tr>
          <td align="right" bgcolor="#ffffff">${lang.detailShippingStatus}：</td>
          <td align="left" bgcolor="#ffffff">${order.shippingStatus}&nbsp;&nbsp;&nbsp;&nbsp;${order.shippingTime}</td>
        </tr>
        <#if  order.invoiceNo??  >
        <tr>
          <td align="right" bgcolor="#ffffff">${lang.consignment}：</td>
          <td align="left" bgcolor="#ffffff">${order.invoiceNo}</td>
        </tr>
        </#if>
        <#if  order.toBuyer??  >
        <tr>
          <td align="right" bgcolor="#ffffff">${lang.detailToBuyer}：</td>
          <td align="left" bgcolor="#ffffff">${order.toBuyer}</td>
        </tr>
        </#if>

        <#if  virtualCard??  >
        <tr>
          <td align="right" bgcolor="#ffffff">${lang.virtualCardInfo}：</td>
          <td colspan="3" align="left" bgcolor="#ffffff">
          <#list virtualCard as vgoods>
            <#list vgoods.info as card>
              <#if  card.cardSn??  >${lang.cardSn}:<span style="color:red;">${card.cardSn}</span></#if>
              <#if  card.cardPassword??  >${lang.cardPassword}:<span style="color:red;">${card.cardPassword}</span></#if>
              <#if  card.endDate??  >${lang.endDate}:${card.endDate}</#if><br />
            </#list>
          </#list>
          </td>
        </tr>
        </#if>
      </table>
        <div class="blank"></div>
        <h5><span>${lang.goodsList}</span>
        <#if  allowToCart??  >
        <a href="javascript:;" onclick="returnToCart(${order.orderId})" class="f6">${lang.returnToCart}</a>
        </#if>
        </h5>
        <div class="blank"></div>
         <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr>
            <th width="23%" align="center" bgcolor="#ffffff">${lang.goodsName}</th>
            <th width="29%" align="center" bgcolor="#ffffff">${lang.goodsAttr}</th>
            ${lang.marketPrice}</th>-->
            <th width="26%" align="center" bgcolor="#ffffff">${lang.goodsPrice}<#if  order.extensionCode  ==  "groupBuy"  >${lang.gbDeposit}</#if></th>
            <th width="9%" align="center" bgcolor="#ffffff">${lang.number}</th>
            <th width="20%" align="center" bgcolor="#ffffff">${lang.subtotal}</th>
          </tr>
          <#list goodsList as goods>
          <tr>
            <td bgcolor="#ffffff"><a href="goods.action?id=${goods.goodsId}" target="_blank" class="f6">${goods.goodsName}</a>
              <#if  goods.parentId  >  0  >
              <span style="color:#FF0000">（${lang.accessories}）</span>
              <#elseif  goods.isGift??  >
              <span style="color:#FF0000">（${lang.largess}）</span>
              </#if></td>
            <td align="left" bgcolor="#ffffff">${goods.goodsAttr|nl2br}</td>
            ${goods.marketPrice}</td>-->
            <td align="right" bgcolor="#ffffff">${goods.goodsPrice}</td>
            <td align="center" bgcolor="#ffffff">${goods.goodsNumber}</td>
            <td align="right" bgcolor="#ffffff">${goods.subtotal}</td>
          </tr>
          </#list>
          <tr>
            <td colspan="8" bgcolor="#ffffff" align="right">
            ${lang.shoppingMoney}<#if  order.extensionCode  ==  "groupBuy"  >${lang.gbDeposit}</#if>: ${order.formatedGoodsAmount}
            </td>
          </tr>
        </table>
         <div class="blank"></div>
        <h5><span>${lang.feeTotal}</span></h5>
        <div class="blank"></div>
         <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr>
            <td align="right" bgcolor="#ffffff">
                ${lang.goodsAllPrice}<#if  order.extensionCode  ==  "groupBuy"  >${lang.gbDeposit}</#if>: ${order.formatedGoodsAmount}
              <#if  order.discount  >  0  >
              - ${lang.discount}: ${order.formatedDiscount}
              </#if>
              <#if  order.tax  >  0  >
              + ${lang.tax}: ${order.formatedTax}
              </#if>
              <#if  order.shippingFee  >  0  >
              + ${lang.shippingFee}: ${order.formatedShippingFee}
              </#if>
              <#if  order.insureFee  >  0  >
              + ${lang.insureFee}: ${order.formatedInsureFee}
              </#if>
              <#if  order.payFee  >  0  >
              + ${lang.payFee}: ${order.formatedPayFee}
              </#if>
              <#if  order.packFee  >  0  >
              + ${lang.packFee}: ${order.formatedPackFee}
              </#if>
              <#if  order.cardFee  >  0  >
              + ${lang.cardFee}: ${order.formatedCardFee}
              </#if>        </td>
          </tr>
          <tr>
            <td align="right" bgcolor="#ffffff">
              <#if  order.moneyPaid  >  0  >
              - ${lang.orderMoneyPaid}: ${order.formatedMoneyPaid}
              </#if>
              <#if  order.surplus  >  0  >
              - ${lang.useSurplus}: ${order.formatedSurplus}
              </#if>
              <#if  order.integralMoney  >  0  >
              - ${lang.useIntegral}: ${order.formatedIntegralMoney}
              </#if>
              <#if  order.bonus  >  0  >
              - ${lang.useBonus}: ${order.formatedBonus}
              </#if>        </td>
          </tr>
          <tr>
            <td align="right" bgcolor="#ffffff">${lang.orderAmount}: ${order.formatedOrderAmount}
            <#if  order.extensionCode  ==  "groupBuy"  ><br />
            ${lang.noticeGbOrderAmount}</#if></td>
          </tr>
            <#if  allowEditSurplus??  >
          <tr>
            <td align="right" bgcolor="#ffffff">
      <form action="user.action" method="post" name="formFee" id="formFee">${lang.useMoreSurplus}:
            <input name="surplus" type="text" size="8" value="0" style="border:1px solid #ccc;"/>${maxSurplus}
            <input type="submit" name="Submit" class="submit" value="${lang.buttonSubmit}" />
      <input type="hidden" name="act" value="act_edit_surplus" />
      <input type="hidden" name="order_id" value="${smarty.get.orderId}" />
      </form></td>
          </tr>
    </#if>
        </table>
         <div class="blank"></div>
        <h5><span>${lang.consigneeInfo}</span></h5>
        <div class="blank"></div>
         <#if  order.allowUpdateAddress  >  0  >
          <form action="user.action" method="post" name="formAddress" id="formAddress">
           <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
              <tr>
                <td width="15%" align="right" bgcolor="#ffffff">${lang.consigneeName}： </td>
                <td width="35%" align="left" bgcolor="#ffffff"><input name="consignee" type="text"  class="inputBg" value="${order.consignee}" size="25">
                </td>
                <td width="15%" align="right" bgcolor="#ffffff">${lang.emailAddress}： </td>
                <td width="35%" align="left" bgcolor="#ffffff"><input name="email" type="text"  class="inputBg" value="${order.email}" size="25" />
                </td>
              </tr>
              <#if  order.existRealGoods??  >
              <!-- 只有虚拟商品处理-->
              <tr>
                <td align="right" bgcolor="#ffffff">${lang.detailedAddress}： </td>
                <td align="left" bgcolor="#ffffff"><input name="address" type="text" class="inputBg" value="${order.address} " size="25" /></td>
                <td align="right" bgcolor="#ffffff">${lang.postalcode}：</td>
                <td align="left" bgcolor="#ffffff"><input name="zipcode" type="text"  class="inputBg" value="${order.zipcode}" size="25" /></td>
              </tr>
              </#if>
              <tr>
                <td align="right" bgcolor="#ffffff">${lang.phone}：</td>
                <td align="left" bgcolor="#ffffff"><input name="tel" type="text" class="inputBg" value="${order.tel}" size="25" /></td>
                <td align="right" bgcolor="#ffffff">${lang.backupPhone}：</td>
                <td align="left" bgcolor="#ffffff"><input name="mobile" type="text"  class="inputBg" value="${order.mobile}" size="25" /></td>
              </tr>
              <#if  order.existRealGoods??  >
              <!-- 只有虚拟商品处理-->
              <tr>
                <td align="right" bgcolor="#ffffff">${lang.signBuilding}：</td>
                <td align="left" bgcolor="#ffffff"><input name="sign_building" class="inputBg" type="text" value="${order.signBuilding}" size="25" />
                </td>
                <td align="right" bgcolor="#ffffff">${lang.deliverGoodsTime}：</td>
                <td align="left" bgcolor="#ffffff"><input name="best_time" type="text" class="inputBg" value="${order.bestTime}" size="25" />
                </td>
              </tr>
              </#if>
              <tr>
                <td colspan="4" align="center" bgcolor="#ffffff"><input type="hidden" name="act" value="save_order_address" />
                  <input type="hidden" name="order_id" value="${order.orderId}" />
                  <input type="submit" class="bnt_blue_2" value="${lang.updateAddress}"  />
                </td>
              </tr>
            </table>
          </form>
          <#else>
          <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <tr>
              <td width="15%" align="right" bgcolor="#ffffff">${lang.consigneeName}：</td>
              <td width="35%" align="left" bgcolor="#ffffff">${order.consignee}</td>
              <td width="15%" align="right" bgcolor="#ffffff" >${lang.emailAddress}：</td>
              <td width="35%" align="left" bgcolor="#ffffff">${order.email}</td>
            </tr>
            <#if  order.existRealGoods??  >
            <tr>
              <td align="right" bgcolor="#ffffff">${lang.detailedAddress}：</td>
              <td colspan="3" align="left" bgcolor="#ffffff">${order.address}
                <#if  order.zipcode??  >
                [${lang.postalcode}: ${order.zipcode}]
                </#if></td>
            </tr>
            </#if>
            <tr>
              <td align="right" bgcolor="#ffffff">${lang.phone}：</td>
              <td align="left" bgcolor="#ffffff">${order.tel} </td>
              <td align="right" bgcolor="#ffffff">${lang.backupPhone}：</td>
              <td align="left" bgcolor="#ffffff">${order.mobile}</td>
            </tr>
            <#if  order.existRealGoods??  >
            <tr>
              <td align="right" bgcolor="#ffffff" >${lang.signBuilding}：</td>
              <td align="left" bgcolor="#ffffff">${order.signBuilding} </td>
              <td align="right" bgcolor="#ffffff" >${lang.deliverGoodsTime}：</td>
              <td align="left" bgcolor="#ffffff">${order.bestTime}</td>
            </tr>
            </#if>
          </table>
          </#if>
          <div class="blank"></div>
        <h5><span>${lang.payment}</span></h5>
        <div class="blank"></div>
        <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
                <tr>
                  <td bgcolor="#ffffff">
                  ${lang.selectPayment}: ${order.payName}。${lang.orderAmount}: <strong>${order.formatedOrderAmount}</strong><br />
                  ${order.payDesc}
                  </td>
                </tr>
                  <tr>
                  <td bgcolor="#ffffff" align="right">
                  <#if  paymentList??  >
              <form name="payment" method="post" action="user.action">
              ${lang.changePayment}:
              <select name="pay_id">
                <#list paymentList as payment>
                <option value="${payment.payId}">
                ${payment.payName}(${lang.payFee}:${payment.formatPayFee})
                </option>
                </#list>
              </select>
              <input type="hidden" name="act" value="act_edit_payment" />
              <input type="hidden" name="order_id" value="${order.orderId}" />
              <input type="submit" name="Submit" class="submit" value="${lang.buttonSubmit}" />
              </form>
              </#if>
                  </td>
                </tr>
              </table>
        <div class="blank"></div>
        <h5><span>${lang.otherInfo}</span></h5>
        <div class="blank"></div>
        <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <#if  order.shippingId  >  0  >
          <tr>
            <td width="15%" align="right" bgcolor="#ffffff">${lang.shipping}：</td>
            <td colspan="3" width="85%" align="left" bgcolor="#ffffff">${order.shippingName}</td>
          </tr>
          </#if>

          <tr>
            <td width="15%" align="right" bgcolor="#ffffff">${lang.payment}：</td>
            <td colspan="3" align="left" bgcolor="#ffffff">${order.payName}</td>
          </tr>
          <#if  order.insureFee  >  0  >
          </#if>
          <#if  order.packName??  >
          <tr>
            <td width="15%" align="right" bgcolor="#ffffff">${lang.usePack}：</td>
            <td colspan="3" align="left" bgcolor="#ffffff">${order.packName}</td>
          </tr>
          </#if>
          <#if  order.cardName??  >
          <tr>
            <td width="15%" align="right" bgcolor="#ffffff">${lang.useCard}：</td>
            <td colspan="3" align="left" bgcolor="#ffffff">${order.cardName}</td>
          </tr>
          </#if>
          <#if  order.cardMessage??  >
          <tr>
            <td width="15%" align="right" bgcolor="#ffffff">${lang.blessNote}：</td>
            <td colspan="3" align="left" bgcolor="#ffffff">${order.cardMessage}</td>
          </tr>
          </#if>
          <#if  order.surplus  >  0  >
          </#if>
          <#if  order.integral  >  0  >
          <tr>
            <td width="15%" align="right" bgcolor="#ffffff">${lang.useIntegral}：</td>
            <td colspan="3" align="left" bgcolor="#ffffff">${order.integral}</td>
          </tr>
          </#if>
          <#if  order.bonus  >  0  >
          </#if>
          <#if  order.invPayee??  &&  order.invContent??  >
          <tr>
            <td width="15%" align="right" bgcolor="#ffffff">${lang.invoiceTitle}：</td>
            <td width="36%" align="left" bgcolor="#ffffff">${order.invPayee}</td>
            <td width="19%" align="right" bgcolor="#ffffff">${lang.invoiceContent}：</td>
            <td width="25%" align="left" bgcolor="#ffffff">${order.invContent}</td>
          </tr>
          </#if>
          <#if  order.postscript??  >
          <tr>
            <td width="15%" align="right" bgcolor="#ffffff">${lang.orderPostscript}：</td>
            <td colspan="3" align="left" bgcolor="#ffffff">${order.postscript}</td>
          </tr>
          </#if>
          <tr>
            <td width="15%" align="right" bgcolor="#ffffff">${lang.bookingProcess}：</td>
            <td colspan="3" align="left" bgcolor="#ffffff">${order.howOosName}</td>
          </tr>
        </table>
      </#if>
    <!--#订单详情页 end-->
    <!--#会员余额 start-->
      <#if  action  ==  "accountRaply"  ||  action??  ==  "accountLog"  ||  action??  ==  "accountDeposit"  ||  action??  ==  "accountDetail"  >
        <script type="text/javascript">
          <#list lang.accountJs as item>
            var ${key} = "${item}";
          </#list>
        </script>
        <h5><span>${lang.userBalance}</span></h5>
        <div class="blank"></div>
         <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr>
            <td align="right" bgcolor="#ffffff"><a href="user.action?act=account_deposit" class="f6">${lang.surplusType_0}</a> | <a href="user.action?act=account_raply" class="f6">${lang.surplusType_1}</a> | <a href="user.action?act=account_detail" class="f6">${lang.addSurplusLog}</a> | <a href="user.action?act=account_log" class="f6">${lang.viewApplication}</a> </td>
          </tr>
        </table>
        </#if>
        <#if  action  ==  "accountRaply"  >
        <form name="formSurplus" method="post" action="user.action" onSubmit="return submitSurplus()">
        <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr>
            <td width="15%" bgcolor="#ffffff">${lang.repayMoney}:</td>
            <td bgcolor="#ffffff" align="left"><input type="text" name="amount" value="${order.amount}" class="inputBg" size="30" />
            </td>
          </tr>
          <tr>
            <td bgcolor="#ffffff">${lang.processNotic}:</td>
            <td bgcolor="#ffffff" align="left"><textarea name="user_note" cols="55" rows="6" style="border:1px solid #ccc;">${order.userNote}</textarea></td>
          </tr>
          <tr>
            <td bgcolor="#ffffff" colspan="2" align="center">
            <input type="hidden" name="surplus_type" value="1" />
              <input type="hidden" name="act" value="act_account" />
              <input type="submit" name="submit"  class="bnt_blue_1" value="${lang.submitRequest}" />
              <input type="reset" name="reset" class="bnt_blue_1" value="${lang.buttonReset}" />
            </td>
          </tr>
        </table>
        </form>
        </#if>
        <#if  action  ==  "accountDeposit"  >
        <form name="formSurplus" method="post" action="user.action" onSubmit="return submitSurplus()">
        <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <tr>
              <td width="15%" bgcolor="#ffffff">${lang.depositMoney}:</td>
              <td align="left" bgcolor="#ffffff"><input type="text" name="amount"  class="inputBg" value="${order.amount}" size="30" /></td>
            </tr>
            <tr>
              <td bgcolor="#ffffff">${lang.processNotic}:</td>
              <td align="left" bgcolor="#ffffff"><textarea name="user_note" cols="55" rows="6" style="border:1px solid #ccc;">${order.userNote}</textarea></td>
            </tr>
          </table>
          <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
            <tr align="center">
              <td bgcolor="#ffffff"  colspan="3" align="left">${lang.payment}:</td>
            </tr>
            <tr align="center">
              <td bgcolor="#ffffff">${lang.payName}</td>
              <td bgcolor="#ffffff" width="60%">${lang.payDesc}</td>
              <td bgcolor="#ffffff" width="17%">${lang.payFee}</td>
            </tr>
            <#list payment as list>
            <tr>
              <td bgcolor="#ffffff" align="left">
              <input type="radio" name="payment_id" value="${list.payId}" />${list.payName}</td>
              <td bgcolor="#ffffff" align="left">${list.payDesc}</td>
              <td bgcolor="#ffffff" align="center">${list.payFee}</td>
            </tr>
            </#list>
            <tr>
        <td bgcolor="#ffffff" colspan="3"  align="center">
        <input type="hidden" name="surplus_type" value="0" />
          <input type="hidden" name="rec_id" value="${order.id}" />
          <input type="hidden" name="act" value="act_account" />
          <input type="submit" class="bnt_blue_1" name="submit" value="${lang.submitRequest}" />
          <input type="reset" class="bnt_blue_1" name="reset" value="${lang.buttonReset}" />
        </td>
      </tr>
          </table>
        </form>
        </#if>
        <#if  action  ==  "actAccount"  >
        <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr>
            <td width="25%" align="right" bgcolor="#ffffff">${lang.surplusAmount}</td>
            <td width="80%" bgcolor="#ffffff">${amount}</td>
          </tr>
          <tr>
            <td align="right" bgcolor="#ffffff">${lang.paymentName}</td>
            <td bgcolor="#ffffff">${payment.payName}</td>
          </tr>
          <tr>
            <td align="right" bgcolor="#ffffff">${lang.paymentFee}</td>
            <td bgcolor="#ffffff">${payFee}</td>
          </tr>
          <tr>
            <td align="right" valign="middle" bgcolor="#ffffff">${lang.paymentDesc}</td>
            <td bgcolor="#ffffff">${payment.payDesc}</td>
          </tr>
          <tr>
            <td colspan="2" bgcolor="#ffffff">${payment.payButton}</td>
          </tr>
        </table>
        </#if>
       <#if  action  ==  "accountDetail"  >
        <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr align="center">
            <td bgcolor="#ffffff">${lang.processTime}</td>
            <td bgcolor="#ffffff">${lang.surplusProType}</td>
            <td bgcolor="#ffffff">${lang.money}</td>
            <td bgcolor="#ffffff">${lang.changeDesc}</td>
          </tr>
          <#list accountLog as item>
          <tr>
            <td align="center" bgcolor="#ffffff">${item.changeTime}</td>
            <td align="center" bgcolor="#ffffff">${item.type}</td>
            <td align="right" bgcolor="#ffffff">${item.amount}</td>
            <td bgcolor="#ffffff" title="${item.changeDesc}">&nbsp;&nbsp;${item.shortChangeDesc}</td>
          </tr>
          </#list>
          <tr>
            <td colspan="4" align="center" bgcolor="#ffffff"><div align="right">${lang.currentSurplus}${surplusAmount}</div></td>
          </tr>
        </table>
        <#include "library/pages.ftl">
        </#if>
        <#if  action  ==  "accountLog"  >
        <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr align="center">
            <td bgcolor="#ffffff">${lang.processTime}</td>
            <td bgcolor="#ffffff">${lang.surplusProType}</td>
            <td bgcolor="#ffffff">${lang.money}</td>
            <td bgcolor="#ffffff">${lang.processNotic}</td>
            <td bgcolor="#ffffff">${lang.adminNotic}</td>
            <td bgcolor="#ffffff">${lang.isPaid}</td>
            <td bgcolor="#ffffff">${lang.handle}</td>
          </tr>
          <#list accountLog as item>
          <tr>
            <td align="center" bgcolor="#ffffff">${item.addTime}</td>
            <td align="left" bgcolor="#ffffff">${item.type}</td>
            <td align="right" bgcolor="#ffffff">${item.amount}</td>
            <td align="left" bgcolor="#ffffff">${item.shortUserNote}</td>
            <td align="left" bgcolor="#ffffff">${item.shortAdminNote}</td>
            <td align="center" bgcolor="#ffffff">${item.payStatus}</td>
            <td align="right" bgcolor="#ffffff">${item.handle}
              <#if  (item.isPaid  ==  0  &&  item.processType??  ==  1)  ||  item.handle??  >
              <a href="user.action?act=cancel&id=${item.id}" onclick="if (!confirm('${lang.confirmRemoveAccount}')) return false;">${lang.isCancel}</a>
              </#if>
							</td>
          </tr>
          </#list>
          <tr>
            <td colspan="7" align="right" bgcolor="#ffffff">${lang.currentSurplus}${surplusAmount}</td>
          </tr>
        </table>
        <#include "library/pages.ftl">
      </#if>
      <!--#会员余额 end-->
      <!--#收货地址页面 -->
      <#if  action  ==  'addressList'  >
        <h5><span>${lang.consigneeInfo}</span></h5>
        <div class="blank"></div>
         
            <script type="text/javascript" src="js/utils.js"></script>
<script type="text/javascript" src="js/transport.js"></script>
<script type="text/javascript" src="js/region.js"></script>
<script type="text/javascript" src="js/shoppingFlow.js"></script>

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
            <#list consigneeList as consignee>
            <form action="user.action" method="post" name="theForm" onsubmit="return checkConsignee(this)">
              <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
                <tr>
                  <td align="right" bgcolor="#ffffff">${lang.countryProvince}：</td>
                  <td colspan="3" align="left" bgcolor="#ffffff"><select name="country" id="selCountries_${sn}" onchange="region.changed(this, 1, 'selProvinces_${sn}')">
                      <option value="0">${lang.pleaseSelect}${nameOfRegion[0]}</option>
                      <#list countryList as country>
                      <option value="${country.regionId}" <#if  consignee.country  ==  country.regionId??  >selected</#if>>${country.regionName}</option>
                      </#list>
                    </select>
                    <select name="province" id="selProvinces_${sn}" onchange="region.changed(this, 2, 'selCities_${sn}')">
                      <option value="0">${lang.pleaseSelect}${nameOfRegion[1]}</option>
                      <#list provinceList.${sn} as province>
                      <option value="${province.regionId}" <#if  consignee.province  ==  province.regionId??  >selected</#if>>${province.regionName}</option>
                      </#list>
                    </select>
                    <select name="city" id="selCities_${sn}" onchange="region.changed(this, 3, 'selDistricts_${sn}')">
                      <option value="0">${lang.pleaseSelect}${nameOfRegion[2]}</option>
                      <#list cityList.${sn} as city>
                      <option value="${city.regionId}" <#if  consignee.city  ==  city.regionId??  >selected</#if>>${city.regionName}</option>
                      </#list>
                    </select>
                    <select name="district" id="selDistricts_${sn}" <#if  !districtList.sn??  >style="display:none"</#if>>
                      <option value="0">${lang.pleaseSelect}${nameOfRegion[3]}</option>
                      <#list districtList.${sn} as district>
                      <option value="${district.regionId}" <#if  consignee.district  ==  district.regionId??  >selected</#if>>${district.regionName}</option>
                      </#list>
                    </select>
                  ${lang.requireField} </td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#ffffff">${lang.consigneeName}：</td>
                  <td align="left" bgcolor="#ffffff"><input name="consignee" type="text" class="inputBg" id="consignee_${sn}" value="${consignee.consignee}" />
                  ${lang.requireField} </td>
                  <td align="right" bgcolor="#ffffff">${lang.emailAddress}：</td>
                  <td align="left" bgcolor="#ffffff"><input name="email" type="text" class="inputBg" id="email_${sn}" value="${consignee.email}" />
                  ${lang.requireField}</td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#ffffff">${lang.detailedAddress}：</td>
                  <td align="left" bgcolor="#ffffff"><input name="address" type="text" class="inputBg" id="address_${sn}" value="${consignee.address}" />
                  ${lang.requireField}</td>
                  <td align="right" bgcolor="#ffffff">${lang.postalcode}：</td>
                  <td align="left" bgcolor="#ffffff"><input name="zipcode" type="text" class="inputBg" id="zipcode_${sn}" value="${consignee.zipcode}" /></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#ffffff">${lang.phone}：</td>
                  <td align="left" bgcolor="#ffffff"><input name="tel" type="text" class="inputBg" id="tel_${sn}" value="${consignee.tel}" />
                  ${lang.requireField}</td>
                  <td align="right" bgcolor="#ffffff">${lang.backupPhone}：</td>
                  <td align="left" bgcolor="#ffffff"><input name="mobile" type="text" class="inputBg" id="mobile_${sn}" value="${consignee.mobile}" /></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#ffffff">${lang.signBuilding}：</td>
                  <td align="left" bgcolor="#ffffff"><input name="sign_building" type="text" class="inputBg" id="sign_building_${sn}" value="${consignee.signBuilding}" /></td>
                  <td align="right" bgcolor="#ffffff">${lang.deliverGoodsTime}：</td>
                  <td align="left" bgcolor="#ffffff"><input name="best_time" type="text"  class="inputBg" id="best_time_${sn}" value="${consignee.bestTime}" /></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#ffffff">&nbsp;</td>
                  <td colspan="3" align="center" bgcolor="#ffffff"><#if  consignee.consignee??  &&  consignee.email??  >
                    <input type="submit" name="submit" class="bnt_blue_1" value="${lang.confirmEdit}" />
                    <input name="button" type="button" class="bnt_blue"  onclick="if (confirm('${lang.confirmDropAddress}'))location.href='user.action?act=drop_consignee&id=${consignee.addressId}'" value="${lang.drop}" />
                    <#else>
                    <input type="submit" name="submit" class="bnt_blue_2"  value="${lang.addAddress}"/>
                    </#if>
                    <input type="hidden" name="act" value="act_edit_address" />
                    <input name="address_id" type="hidden" value="${consignee.addressId}" />
                  </td>
                </tr>
              </table>
            </form>
            </#list>
      </#if>
    <!--#收货地址添加页面 -->
      <!--* 积分兑换-->
       <#if  action  ==  'transformPoints'  >
       <h5><span>${lang.transformPoints}</span></h5>
			 <div class="blank"></div>
       <#if  exchangeType  ==  'ucenter'  >
        <form action="user.action" method="post" name="transForm" onsubmit="return calcredit();">
       <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
			    <tr>
					<th width="120" bgcolor="#FFFFFF" align="right" valign="top">${lang.curPoints}:</th>
					<td bgcolor="#FFFFFF">
					<label for="pay_points">${lang.exchangePoints.1}:</label><input type="text" size="15" id="pay_points" name="pay_points" value="${shopPoints.payPoints}" style="border:0;border-bottom:1px solid #DADADA;" readonly="readonly" /><br />
					<div class="blank"></div>
					<label for="rank_points">${lang.exchangePoints.0}:</label><input type="text" size="15" id="rank_points" name="rank_points" value="${shopPoints.rankPoints}" style="border:0;border-bottom:1px solid #DADADA;" readonly="readonly" />
					</td>
					</tr>
          <tr><td bgcolor="#FFFFFF">&nbsp;</td>
          <td bgcolor="#FFFFFF">&nbsp;</td>
          </tr>
          <tr>
            <th align="right" bgcolor="#FFFFFF"><label for="amount">${lang.exchangeAmount}:</label></th>
            <td bgcolor="#FFFFFF"><input size="15" name="amount" id="amount" value="0" onkeyup="calcredit();" type="text" />
                <select name="fromcredits" id="fromcredits" onchange="calcredit();">
                  TODO: htmlOptions CLAUSE
                </select>
            </td>
          </tr>
          <tr>
            <th align="right" bgcolor="#FFFFFF"><label for="desamount">${lang.exchangeDesamount}:</label></th>
            <td bgcolor="#FFFFFF"><input type="text" name="desamount" id="desamount" disabled="disabled" value="0" size="15" />
              <select name="tocredits" id="tocredits" onchange="calcredit();">
                TODO: htmlOptions CLAUSE
              </select>
            </td>
          </tr>
          <tr>
            <th align="right" bgcolor="#FFFFFF">${lang.exchangeRatio}:</th>
            <td bgcolor="#FFFFFF">1 <span id="orgcreditunit">${orgcreditunit}</span> <span id="orgcredittitle">${orgcredittitle}</span> ${lang.exchangeAction} <span id="descreditamount">${descreditamount}</span> <span id="descreditunit">${descreditunit}</span> <span id="descredittitle">${descredittitle}</span></td>
          </tr>
          <tr><td bgcolor="#FFFFFF">&nbsp;</td>
          <td bgcolor="#FFFFFF"><input type="hidden" name="act" value="act_transform_ucenter_points" /><input type="submit" name="transfrom" value="${lang.transform}" /></td></tr>
  </table>
        </form>
       <script type="text/javascript">
        <#list lang.exchangeJs as langJs>
        var ${key} = '${langJs}';
        </#list>

        var out_exchange_allow = new Array();
        <#list outExchangeAllow as ratio>
        out_exchange_allow['${key}'] = '${ratio}';
        </#list>

        function calcredit()
        {
            var frm = document.forms['transForm'];
            var src_credit = frm.fromcredits.value;
            var dest_credit = frm.tocredits.value;
            var in_credit = frm.amount.value;
            var org_title = frm.fromcredits[frm.fromcredits.selectedIndex].innerHTML;
            var dst_title = frm.tocredits[frm.tocredits.selectedIndex].innerHTML;
            var radio = 0;
            var shop_points = ['rank_points', 'pay_points'];
            if (parseFloat(in_credit) > parseFloat(document.getElementById(shop_points[src_credit]).value))
            {
                alert(balance.replace('{%s}', org_title));
                frm.amount.value = frm.desamount.value = 0;
                return false;
            }
            if (typeof(out_exchange_allow[dest_credit+'|'+src_credit]) == 'string')
            {
                radio = (1 / parseFloat(out_exchange_allow[dest_credit+'|'+src_credit])).toFixed(2);
            }
            document.getElementById('orgcredittitle').innerHTML = org_title;
            document.getElementById('descreditamount').innerHTML = radio;
            document.getElementById('descredittitle').innerHTML = dst_title;
            if (in_credit > 0)
            {
                if (typeof(out_exchange_allow[dest_credit+'|'+src_credit]) == 'string')
                {
                    frm.desamount.value = Math.floor(parseFloat(in_credit) / parseFloat(out_exchange_allow[dest_credit+'|'+src_credit]));
                    frm.transfrom.disabled = false;
                    return true;
                }
                else
                {
                    frm.desamount.value = deny;
                    frm.transfrom.disabled = true;
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
       </script>
       <#else>
        <b>${lang.curPoints}:</b>
        <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#dddddd">
          <tr>
            <td width="30%" valign="top" bgcolor="#FFFFFF"><table border="0">
              <#list bbsPoints as points>
              <tr>
                <th>${points.title}:</th>
                <td width="120" style="border-bottom:1px solid #DADADA;">${points.value}</td>
              </tr>
              </#list>
            </table></td>
            <td width="50%" valign="top" bgcolor="#FFFFFF"><table>
                    <tr>
                <th>${lang.payPoints}:</th>
                <td width="120" style="border-bottom:1px solid #DADADA;">${shopPoints.payPoints}</td>
                    </tr>
              <tr>
                <th>${lang.rankPoints}:</th>
                <td width="120" style="border-bottom:1px solid #DADADA;">${shopPoints.rankPoints}</td>
              </tr>
            </table></td>
          </tr>
        </table>
        <br />
        <b>${lang.ruleList}:</b>
        <ul>
          <#list ruleList as rule>
          <li>"${rule.from}" ${lang.transform} "${rule.to}" ${lang.rateIs} ${rule.rate}
          </#list>
        </ul>
        <form action="user.action" method="post" name="theForm">
        <table width="100%" border="1" align="center" cellpadding="5" cellspacing="0" style="border-collapse:collapse;border:1px solid #DADADA;">
          <tr style="background:#F1F1F1;">
            <th>${lang.rule}</th>
            <th>${lang.transformNum}</th>
            <th>${lang.transformResult}</th>
          </tr>
          <tr>
            <td>
              <select name="rule_index" onchange="changeRule()">
                <#list ruleList as rule>
                <option value="${key}">${rule.from}->${rule.to}</option>
                </#list>
              </select>
          </td>
          <td>
            <input type="text" name="num" value="0" onkeyup="calPoints()"/>
          </td>
          <td><span id="ECS_RESULT">0</span></td>
          </tr>
          <tr>
            <td colspan="3" align="center"><input type="hidden" name="act" value="act_transform_points"  /><input type="submit" value="${lang.transform}" /></td>
          </tr>
        </table>
        </form>
       <script type="text/javascript">
          //<![CDATA[
            var rule_list = new Object();
            var invalid_input = '${lang.invalidInput}';
            <#list ruleList as rule>
            rule_list['${key}'] = '${rule.rate}';
            </#list>
            function calPoints()
            {
              var frm = document.forms['theForm'];
              var rule_index = frm.elements['rule_index'].value;
              var num = parseInt(frm.elements['num'].value);
              var rate = rule_list[rule_index];

              if (isNaN(num) || num < 0 || num != frm.elements['num'].value)
              {
                document.getElementById('ECS_RESULT').innerHTML = invalid_input;
                rerutn;
              }
              var arr = rate.split(':');
              var from = parseInt(arr[0]);
              var to = parseInt(arr[1]);

              if (from <=0 || to <=0)
              {
                from = 1;
                to = 0;
              }
              document.getElementById('ECS_RESULT').innerHTML = parseInt(num * to / from);
            }

            function changeRule()
            {
              document.forms['theForm'].elements['num'].value = 0;
              document.getElementById('ECS_RESULT').innerHTML = 0;
            }
          //]]>
       </script>
       </#if>
        </#if>
        <!--#积分兑换 -->




      </div>
     </div>
    </div>
  </div>
  <!--right end-->
</div>
<div class="blank"></div>
<#include "library/page_footer.ftl">
</body>
<script type="text/javascript">
<#list lang.clipsJs as item>
var ${key} = "${item}";
</#list>
</script>
</html>
