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
public class BusinessRecord extends org.jooq.impl.UpdatableRecordImpl<com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord> implements org.jooq.Record12<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> {

	private static final long serialVersionUID = 1302281227;

	/**
	 * Setter for <code>swiftbuy.business.id</code>.
	 */
	public BusinessRecord setId(java.lang.Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>swiftbuy.business.uid</code>.
	 */
	public BusinessRecord setUid(java.lang.Integer value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.uid</code>.
	 */
	public java.lang.Integer getUid() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>swiftbuy.business.name</code>.
	 */
	public BusinessRecord setName(java.lang.String value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.name</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>swiftbuy.business.description</code>.
	 */
	public BusinessRecord setDescription(java.lang.String value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.description</code>.
	 */
	public java.lang.String getDescription() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>swiftbuy.business.create_time</code>.
	 */
	public BusinessRecord setCreateTime(java.sql.Timestamp value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.create_time</code>.
	 */
	public java.sql.Timestamp getCreateTime() {
		return (java.sql.Timestamp) getValue(4);
	}

	/**
	 * Setter for <code>swiftbuy.business.update_time</code>.
	 */
	public BusinessRecord setUpdateTime(java.sql.Timestamp value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.update_time</code>.
	 */
	public java.sql.Timestamp getUpdateTime() {
		return (java.sql.Timestamp) getValue(5);
	}

	/**
	 * Setter for <code>swiftbuy.business.city</code>.
	 */
	public BusinessRecord setCity(java.lang.String value) {
		setValue(6, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.city</code>.
	 */
	public java.lang.String getCity() {
		return (java.lang.String) getValue(6);
	}

	/**
	 * Setter for <code>swiftbuy.business.phone</code>.
	 */
	public BusinessRecord setPhone(java.lang.String value) {
		setValue(7, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.phone</code>.
	 */
	public java.lang.String getPhone() {
		return (java.lang.String) getValue(7);
	}

	/**
	 * Setter for <code>swiftbuy.business.county</code>.
	 */
	public BusinessRecord setCounty(java.lang.String value) {
		setValue(8, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.county</code>.
	 */
	public java.lang.String getCounty() {
		return (java.lang.String) getValue(8);
	}

	/**
	 * Setter for <code>swiftbuy.business.password</code>.
	 */
	public BusinessRecord setPassword(java.lang.String value) {
		setValue(9, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.password</code>.
	 */
	public java.lang.String getPassword() {
		return (java.lang.String) getValue(9);
	}

	/**
	 * Setter for <code>swiftbuy.business.icon</code>. 图标
	 */
	public BusinessRecord setIcon(java.lang.String value) {
		setValue(10, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.icon</code>. 图标
	 */
	public java.lang.String getIcon() {
		return (java.lang.String) getValue(10);
	}

	/**
	 * Setter for <code>swiftbuy.business.province</code>.
	 */
	public BusinessRecord setProvince(java.lang.String value) {
		setValue(11, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.business.province</code>.
	 */
	public java.lang.String getProvince() {
		return (java.lang.String) getValue(11);
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
	// Record12 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row12<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row12) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row12<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row12) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.UID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field5() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field6() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.UPDATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.CITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field8() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.PHONE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field9() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.COUNTY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field10() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.PASSWORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field11() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.ICON;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field12() {
		return com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS.PROVINCE;
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
	public java.lang.String value3() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value5() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value6() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getCity();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value8() {
		return getPhone();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value9() {
		return getCounty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value10() {
		return getPassword();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value11() {
		return getIcon();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value12() {
		return getProvince();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value2(java.lang.Integer value) {
		setUid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value3(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value4(java.lang.String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value5(java.sql.Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value6(java.sql.Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value7(java.lang.String value) {
		setCity(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value8(java.lang.String value) {
		setPhone(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value9(java.lang.String value) {
		setCounty(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value10(java.lang.String value) {
		setPassword(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value11(java.lang.String value) {
		setIcon(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord value12(java.lang.String value) {
		setProvince(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BusinessRecord values(java.lang.Integer value1, java.lang.Integer value2, java.lang.String value3, java.lang.String value4, java.sql.Timestamp value5, java.sql.Timestamp value6, java.lang.String value7, java.lang.String value8, java.lang.String value9, java.lang.String value10, java.lang.String value11, java.lang.String value12) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached BusinessRecord
	 */
	public BusinessRecord() {
		super(com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS);
	}

	/**
	 * Create a detached, initialised BusinessRecord
	 */
	public BusinessRecord(java.lang.Integer id, java.lang.Integer uid, java.lang.String name, java.lang.String description, java.sql.Timestamp createTime, java.sql.Timestamp updateTime, java.lang.String city, java.lang.String phone, java.lang.String county, java.lang.String password, java.lang.String icon, java.lang.String province) {
		super(com.ywj.swiftbuy.dao.model.tables.Business.BUSINESS);

		setValue(0, id);
		setValue(1, uid);
		setValue(2, name);
		setValue(3, description);
		setValue(4, createTime);
		setValue(5, updateTime);
		setValue(6, city);
		setValue(7, phone);
		setValue(8, county);
		setValue(9, password);
		setValue(10, icon);
		setValue(11, province);
	}
}
