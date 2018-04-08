/**
 * This class is generated by jOOQ
 */
package com.ywj.swiftbuy.dao.model;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.4" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Swiftbuy extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = 1200907067;

	/**
	 * The singleton instance of <code>swiftbuy</code>
	 */
	public static final Swiftbuy SWIFTBUY = new Swiftbuy();

	/**
	 * No further instances allowed
	 */
	private Swiftbuy() {
		super("swiftbuy");
	}

	@Override
	public final java.util.List<org.jooq.Table<?>> getTables() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final java.util.List<org.jooq.Table<?>> getTables0() {
		return java.util.Arrays.<org.jooq.Table<?>>asList(
			com.ywj.swiftbuy.dao.model.tables.Address.ADDRESS,
			com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS,
			com.ywj.swiftbuy.dao.model.tables.Buy.BUY,
			com.ywj.swiftbuy.dao.model.tables.Category.CATEGORY,
			com.ywj.swiftbuy.dao.model.tables.Goods.GOODS,
			com.ywj.swiftbuy.dao.model.tables.History.HISTORY,
			com.ywj.swiftbuy.dao.model.tables.PopularGoods.POPULAR_GOODS,
			com.ywj.swiftbuy.dao.model.tables.Reply.REPLY,
			com.ywj.swiftbuy.dao.model.tables.SearchHistory.SEARCH_HISTORY,
			com.ywj.swiftbuy.dao.model.tables.ShoppingCart.SHOPPING_CART,
			com.ywj.swiftbuy.dao.model.tables.User.USER);
	}
}
