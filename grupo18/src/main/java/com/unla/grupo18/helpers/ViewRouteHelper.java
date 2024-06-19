package com.unla.grupo18.helpers;

public class ViewRouteHelper {
	/**** Views ****/
	//HOME
	public final static String INDEX = "home/index";
	public final static String HELLO = "home/hello";
	
	//USER
	public final static String USER_LOGIN = "user/login";
	public final static String USER_LOGOUT = "user/logout";

	//LOTS
	public final static String LOT_LIST = "lot/lot-list";
	public final static String LOT_ADD = "lot/lot-add";
	

	//PRODUCTS
	public final static String PRODUCTS = "product/product-list";
	public final static String PRODUCTS_INACTIVE = "product/product-inactive-list";
	public final static String PRODUCTS_ACTIVE = "product/product-active-list";
	public final static String PRODUCT_ADD = "product/product-add";
	public final static String PRODUCT_UPDATE = "product/product-update";
	
	//PURCHASE ORDER
	public final static String PURCHASE = "purchaseOrder/purchaseOrder-list";
	public final static String PURCHASE_ADD = "purchaseOrder/purchaseOrder-add";
	
	//REPORTS
	public final static String REPORTS = "reports/reports";
	
	//STOCK
	public final static String STOCK = "stock/stock-list";
	public final static String STOCK_UPDATE = "stock/stock-update";
	
	//USER PURCHASE
	public final static String USER_PURCHASE ="userPurchase/userPurchase-list";
	public final static String USER_PURCHASE_ADD ="userPurchase/userPurchase-add";
	/**** Redirects ****/
	public final static String ROUTE = "redirect:/index";
	
	
	
	
}
