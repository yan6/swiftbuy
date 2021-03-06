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
public class UserRecord extends org.jooq.impl.UpdatableRecordImpl<com.ywj.swiftbuy.dao.model.tables.records.UserRecord> implements org.jooq.Record13<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.sql.Timestamp, java.lang.String, java.lang.String, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.String, java.lang.Integer> {

	private static final long serialVersionUID = -1987299625;

	/**
	 * Setter for <code>swiftbuy.user.uid</code>.
	 */
	public UserRecord setUid(java.lang.Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.uid</code>.
	 */
	public java.lang.Integer getUid() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>swiftbuy.user.username</code>.
	 */
	public UserRecord setUsername(java.lang.String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.username</code>.
	 */
	public java.lang.String getUsername() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>swiftbuy.user.nickname</code>.
	 */
	public UserRecord setNickname(java.lang.String value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.nickname</code>.
	 */
	public java.lang.String getNickname() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>swiftbuy.user.userType</code>.
	 */
	public UserRecord setUsertype(java.lang.String value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.userType</code>.
	 */
	public java.lang.String getUsertype() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>swiftbuy.user.gender</code>.
	 */
	public UserRecord setGender(java.lang.String value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.gender</code>.
	 */
	public java.lang.String getGender() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>swiftbuy.user.registDate</code>.
	 */
	public UserRecord setRegistdate(java.sql.Timestamp value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.registDate</code>.
	 */
	public java.sql.Timestamp getRegistdate() {
		return (java.sql.Timestamp) getValue(5);
	}

	/**
	 * Setter for <code>swiftbuy.user.description</code>.
	 */
	public UserRecord setDescription(java.lang.String value) {
		setValue(6, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.description</code>.
	 */
	public java.lang.String getDescription() {
		return (java.lang.String) getValue(6);
	}

	/**
	 * Setter for <code>swiftbuy.user.cover</code>.
	 */
	public UserRecord setCover(java.lang.String value) {
		setValue(7, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.cover</code>.
	 */
	public java.lang.String getCover() {
		return (java.lang.String) getValue(7);
	}

	/**
	 * Setter for <code>swiftbuy.user.releaseDate</code>.
	 */
	public UserRecord setReleasedate(java.sql.Timestamp value) {
		setValue(8, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.releaseDate</code>.
	 */
	public java.sql.Timestamp getReleasedate() {
		return (java.sql.Timestamp) getValue(8);
	}

	/**
	 * Setter for <code>swiftbuy.user.updateDate</code>.
	 */
	public UserRecord setUpdatedate(java.sql.Timestamp value) {
		setValue(9, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.updateDate</code>.
	 */
	public java.sql.Timestamp getUpdatedate() {
		return (java.sql.Timestamp) getValue(9);
	}

	/**
	 * Setter for <code>swiftbuy.user.password</code>.
	 */
	public UserRecord setPassword(java.lang.String value) {
		setValue(10, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.password</code>.
	 */
	public java.lang.String getPassword() {
		return (java.lang.String) getValue(10);
	}

	/**
	 * Setter for <code>swiftbuy.user.phone</code>.
	 */
	public UserRecord setPhone(java.lang.String value) {
		setValue(11, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.phone</code>.
	 */
	public java.lang.String getPhone() {
		return (java.lang.String) getValue(11);
	}

	/**
	 * Setter for <code>swiftbuy.user.address_id</code>.
	 */
	public UserRecord setAddressId(java.lang.Integer value) {
		setValue(12, value);
		return this;
	}

	/**
	 * Getter for <code>swiftbuy.user.address_id</code>.
	 */
	public java.lang.Integer getAddressId() {
		return (java.lang.Integer) getValue(12);
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
	// Record13 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row13<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.sql.Timestamp, java.lang.String, java.lang.String, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.String, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row13) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row13<java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.sql.Timestamp, java.lang.String, java.lang.String, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.String, java.lang.Integer> valuesRow() {
		return (org.jooq.Row13) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.UID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.USERNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.NICKNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.USERTYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.GENDER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field6() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.REGISTDATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field8() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.COVER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field9() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.RELEASEDATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field10() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.UPDATEDATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field11() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.PASSWORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field12() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.PHONE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field13() {
		return com.ywj.swiftbuy.dao.model.tables.User.USER.ADDRESS_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getUid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getNickname();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getUsertype();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getGender();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value6() {
		return getRegistdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value8() {
		return getCover();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value9() {
		return getReleasedate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value10() {
		return getUpdatedate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value11() {
		return getPassword();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value12() {
		return getPhone();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value13() {
		return getAddressId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value1(java.lang.Integer value) {
		setUid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value2(java.lang.String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value3(java.lang.String value) {
		setNickname(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value4(java.lang.String value) {
		setUsertype(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value5(java.lang.String value) {
		setGender(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value6(java.sql.Timestamp value) {
		setRegistdate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value7(java.lang.String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value8(java.lang.String value) {
		setCover(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value9(java.sql.Timestamp value) {
		setReleasedate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value10(java.sql.Timestamp value) {
		setUpdatedate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value11(java.lang.String value) {
		setPassword(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value12(java.lang.String value) {
		setPhone(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value13(java.lang.Integer value) {
		setAddressId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.String value3, java.lang.String value4, java.lang.String value5, java.sql.Timestamp value6, java.lang.String value7, java.lang.String value8, java.sql.Timestamp value9, java.sql.Timestamp value10, java.lang.String value11, java.lang.String value12, java.lang.Integer value13) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached UserRecord
	 */
	public UserRecord() {
		super(com.ywj.swiftbuy.dao.model.tables.User.USER);
	}

	/**
	 * Create a detached, initialised UserRecord
	 */
	public UserRecord(java.lang.Integer uid, java.lang.String username, java.lang.String nickname, java.lang.String usertype, java.lang.String gender, java.sql.Timestamp registdate, java.lang.String description, java.lang.String cover, java.sql.Timestamp releasedate, java.sql.Timestamp updatedate, java.lang.String password, java.lang.String phone, java.lang.Integer addressId) {
		super(com.ywj.swiftbuy.dao.model.tables.User.USER);

		setValue(0, uid);
		setValue(1, username);
		setValue(2, nickname);
		setValue(3, usertype);
		setValue(4, gender);
		setValue(5, registdate);
		setValue(6, description);
		setValue(7, cover);
		setValue(8, releasedate);
		setValue(9, updatedate);
		setValue(10, password);
		setValue(11, phone);
		setValue(12, addressId);
	}
}
