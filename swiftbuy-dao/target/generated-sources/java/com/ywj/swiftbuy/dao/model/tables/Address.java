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
public class Address extends org.jooq.impl.TableImpl<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord> {

	private static final long serialVersionUID = 948444049;

	/**
	 * The singleton instance of <code>swiftbuy.address</code>
	 */
	public static final com.ywj.swiftbuy.dao.model.tables.Address ADDRESS = new com.ywj.swiftbuy.dao.model.tables.Address();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord> getRecordType() {
		return com.ywj.swiftbuy.dao.model.tables.records.AddressRecord.class;
	}

	/**
	 * The column <code>swiftbuy.address.id</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>swiftbuy.address.city</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord, java.lang.String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>swiftbuy.address.county</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord, java.lang.String> COUNTY = createField("county", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>swiftbuy.address.update_time</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord, java.sql.Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * The column <code>swiftbuy.address.province</code>.
	 */
	public final org.jooq.TableField<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord, java.lang.String> PROVINCE = createField("province", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * Create a <code>swiftbuy.address</code> table reference
	 */
	public Address() {
		this("address", null);
	}

	/**
	 * Create an aliased <code>swiftbuy.address</code> table reference
	 */
	public Address(java.lang.String alias) {
		this(alias, com.ywj.swiftbuy.dao.model.tables.Address.ADDRESS);
	}

	private Address(java.lang.String alias, org.jooq.Table<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord> aliased) {
		this(alias, aliased, null);
	}

	private Address(java.lang.String alias, org.jooq.Table<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.ywj.swiftbuy.dao.model.Swiftbuy.SWIFTBUY, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord, java.lang.Integer> getIdentity() {
		return com.ywj.swiftbuy.dao.model.Keys.IDENTITY_ADDRESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord> getPrimaryKey() {
		return com.ywj.swiftbuy.dao.model.Keys.KEY_ADDRESS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.ywj.swiftbuy.dao.model.tables.records.AddressRecord>>asList(com.ywj.swiftbuy.dao.model.Keys.KEY_ADDRESS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.ywj.swiftbuy.dao.model.tables.Address as(java.lang.String alias) {
		return new com.ywj.swiftbuy.dao.model.tables.Address(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.ywj.swiftbuy.dao.model.tables.Address rename(java.lang.String name) {
		return new com.ywj.swiftbuy.dao.model.tables.Address(name, null);
	}
}
