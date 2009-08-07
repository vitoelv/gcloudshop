package com.jcommerce.gwt.client.model;


public interface IOrder {
	
    public static final int ORDER_UNCONFIRMED = 0; // 未确认
    public static final int ORDER_CONFIRMED = 1; // 已确认
    public static final int ORDER_CANCELED = 2; // 已取消
    public static final int ORDER_INVALID = 3; // 无效
    public static final int ORDER_RETURNED = 4; // 退货

    public static final int SHIPPING_UNSHIPPED = 0; // 未发货
    public static final int SHIPPING_SHIPPED = 1; // 已发货
    public static final int SHIPPING_RECEIVED = 2; // 已收货
    public static final int SHIPPING_PREPARING = 3; // 备货中

    public static final int PAY_UNPAYED = 0; // 未付款
    public static final int PAY_PAYING = 1; // 付款中
    public static final int PAY_PAYED = 2; // 已付款

    public static final String ID = "id";
    public static final String ORDERACTIONS = "orderActions";
    public static final String ORDERGOODS = "orderGoods";
    public static final String SN = "orderSn";
    public static final String USER = "user";
    public static final String STATUS = "status";
    public static final String SHIPPINGSTATUS = "shippingStatus";
    public static final String PAYSTATUS = "payStatus";
    public static final String CONSIGNEE = "consignee";
    public static final String EMAIL = "email";
    //public static final String REGION = "region";
    public static final String ADDRESS = "address";
    public static final String ZIP = "zip";
    public static final String PHONE = "phone";
    public static final String MOBILE = "mobile";
    public static final String SIGNBUILDING = "signBuilding";
    public static final String BESTTIME = "bestTime";
    public static final String POSTSCRIPT = "postScript";
    public static final String PACKNAME = "packName";
    public static final String CARDNAME = "cardName";
    public static final String CARDMESSAGE = "cardMessage";
    public static final String INVOICEPAYEE = "invoicePayee";
    public static final String INVOICECONTENT = "invoiceContent";
    public static final String GOODSAMOUNT = "goodsAmount";
    public static final String SHIPPINGFEE = "shippingFee";
    public static final String INSUREFEE = "insureFee";
    public static final String PAYFEE = "payFee";
    //public static final String PAYMENT = "payment";
    //public static final String SHIPPING = "shipping";
    public static final String HOWOSS = "howOss";
    public static final String HOWSURPLUS = "howSurplus";
    public static final String MONEYPAID = "moneyPaid";
    public static final String SURPLUS = "surplus";
    public static final String INTEGRAL = "integral";
    public static final String INTEGRALMONEY = "integralMoney";
    public static final String ORDERAMOUNT = "orderAmount";
    public static final String BONUSMONEY = "bonusMoney";
    public static final String FROMAD = "fromAD";
    public static final String REFERER = "referer";
    public static final String ADDTIME = "addTime";
    public static final String CONFIRMTIME = "confirmTime";
    public static final String PAYTIME = "payTime";
    public static final String SHIPPINGTIME = "shippingTime";
    //public static final String PACK = "pack";
    //public static final String CARD = "card";
    //public static final String USERBONUS = "userBonus";
    public static final String INVOICENO = "invoiceNO";
    public static final String EXTENSIONCODE = "extensionCode";
    public static final String EXTENSIONID = "extensionId";
    public static final String TOBUYER = "toBuyer";
    public static final String PAYNOTE = "payNote";
    //public static final String AGENCY = "agency";
    public static final String INVOICETYPE = "invoiceType";
    public static final String TAX = "tax";
    public static final String SEPARATE = "separate";
    public static final String DISCOUNT = "discount";
    public static final String PARENTORDER = "parentOrder";
    public static final String CARDFEE = "cardFee";
    public static final String PACKFEE = "packFee";
    public static final String SHIPPINGNAME = "shippingName";
    public static final String PAYNAME = "payName";

}
