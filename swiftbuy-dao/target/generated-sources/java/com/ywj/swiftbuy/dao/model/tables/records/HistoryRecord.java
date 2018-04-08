/**
 * This class is generated by jOOQ
 */
package com.ywj.swiftbuy.dao.model.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.4" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryRecord extends org.jooq.impl.UpdatableRecordImpl<com.ywj.swiftbuy.dao.model.tables.records.HistoryRecord> implements org.jooq.Record6<java.lang.Integer, java.lang.Integer, java.sql.Timestamp, java.lang.Integer, java.lang.String, java.lang.Integer> {

	private static final long serialVersionUID = 1728440989;

	/**
	 * Setter for <code>swiftbuy.history.id</code>.
	 */
	public HistoryRecord setId(java.lang.Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.history.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>swiftbuy.history.uid</code>.
	 */
	public HistoryRecord setUid(java.lang.Integer value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.history.uid</code>.
	 */
	public java.lang.Integer getUid() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>swiftbuy.history.date</code>.
	 */
	public HistoryRecord setDate(java.sql.Timestamp value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.history.date</code>.
	 */
	public java.sql.Timestamp getDate() {
		return (java.sql.Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>swiftbuy.history.goods_id</code>.
	 */
	public HistoryRecord setGoodsId(java.lang.Integer value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.history.goods_id</code>.
	 */
	public java.lang.Integer getGoodsId() {
		return (java.lang.Integer) getValue(3);
	}

	/**
	 * Setter for <code>swiftbuy.history.city</code>. uid的地址
	 */
	public HistoryRecord setCity(java.lang.String value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.history.city</code>. uid的地址
	 */
	public java.lang.String getCity() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>swiftbuy.history.type</code>. 数据类型
	 */
	public HistoryRecord setType(java.lang.Integer value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.history.type</code>. 数据类型
	 */
	public java.lang.Integer getType() {
		return (java.lang.Integer) getValue(5);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.lang.Integer, java.sql.Timestamp, java.lang.Integer, java.lang.String, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Integer, java.lang.Integer, java.sql.Timestamp, java.lang.Integer, java.lang.String, java.lang.Integer> valuesRow() {
		return (org.jooq.Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.ywj.swiftbuy.dao.model.tables.History.HISTORY.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return com.ywj.swiftbuy.dao.model.tables.History.HISTORY.UID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field3() {
		return com.ywj.swiftbuy.dao.model.tables.History.HISTORY.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field4() {
		return com.ywj.swiftbuy.dao.model.tables.History.HISTORY.GOODS_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.ywj.swiftbuy.dao.model.tables.History.HISTORY.CITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field6() {
		return com.ywj.swiftbuy.dao.model.tables.History.HISTORY.TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value2() {
		return getUid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value3() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value4() {
		return getGoodsId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getCity();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value6() {
		return getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryRecord value2(java.lang.Integer value) {
		setUid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryRecord value3(java.sql.Timestamp value) {
		setDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryRecord value4(java.lang.Integer value) {
		setGoodsId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryRecord value5(java.lang.String value) {
		setCity(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryRecord value6(java.lang.Integer value) {
		setType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryRecord values(java.lang.Integer value1, java.lang.Integer value2, java.sql.Timestamp value3, java.lang.Integer value4, java.lang.String value5, java.lang.Integer value6) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HistoryRecord
	 */
	public HistoryRecord() {
		super(com.ywj.swiftbuy.dao.model.tables.History.HISTORY);
	}

	/**
	 * Create a detached, initialised HistoryRecord
	 */
	public HistoryRecord(java.lang.Integer id, java.lang.Integer uid, java.sql.Timestamp date, java.lang.Integer goodsId, java.lang.String city, java.lang.Integer type) {
		super(com.ywj.swiftbuy.dao.model.tables.History.HISTORY);

		setValue(0, id);
		setValue(1, uid);
		setValue(2, date);
		setValue(3, goodsId);
		setValue(4, city);
		setValue(5, type);
	}
}
