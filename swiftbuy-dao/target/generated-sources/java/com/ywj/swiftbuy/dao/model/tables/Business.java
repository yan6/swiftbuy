/**
 * This class is generated by jOOQ
 */
package com.ywj.swiftbuy.dao.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.4" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Business extends org.jooq.impl.TableImpl<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord> {

	private static final long serialVersionUID = -1929048201;

	/**
	 * The singleton instance of <code>swiftbuy.business</code>
	 */
	public static final com.ywj.swiftbuy.dao.model.tables.Business BUSINESS = new com.ywj.swiftbuy.dao.model.tables.Business();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord> getRecordType() {
		return com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord.class;
	}

	/**
	 * The column <code>swiftbuy.business.id</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>swiftbuy.business.uid</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.lang.Integer> UID = createField("uid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>swiftbuy.business.name</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.lang.String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>swiftbuy.business.description</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>swiftbuy.business.create_time</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.sql.Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * The column <code>swiftbuy.business.update_time</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * The column <code>swiftbuy.business.city</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.lang.String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>swiftbuy.business.phone</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.lang.String> PHONE = createField("phone", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>swiftbuy.business.county</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.lang.String> COUNTY = createField("county", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>swiftbuy.business.password</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.lang.String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>swiftbuy.business.icon</code>. 图标
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.lang.String> ICON = createField("icon", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "图标");

	/**
	 * Create a <code>swiftbuy.business</code> table reference
	 */
	public Business() {
		this("business", null);
	}

	/**
	 * Create an aliased <code>swiftbuy.business</code> table reference
	 */
	public Business(java.lang.String alias) {
		this(alias, com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS);
	}

	private Business(java.lang.String alias, org.jooq.Table<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord> aliased) {
		this(alias, aliased, null);
	}

	private Business(java.lang.String alias, org.jooq.Table<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.ywj.swiftbuy.dao.model.Swiftbuy.SWIFTBUY, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord, java.lang.Integer> getIdentity() {
		return com.ywj.swiftbuy.dao.model.Keys.IDENTITY_BUSINESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord> getPrimaryKey() {
		return com.ywj.swiftbuy.dao.model.Keys.KEY_BUSINESS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord>>asList(com.ywj.swiftbuy.dao.model.Keys.KEY_BUSINESS_PRIMARY, com.ywj.swiftbuy.dao.model.Keys.KEY_BUSINESS_UID_CITY_COUNTY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.ywj.swiftbuy.dao.model.tables.Business as(java.lang.String alias) {
		return new com.ywj.swiftbuy.dao.model.tables.Business(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.ywj.swiftbuy.dao.model.tables.Business rename(java.lang.String name) {
		return new com.ywj.swiftbuy.dao.model.tables.Business(name, null);
	}
}
