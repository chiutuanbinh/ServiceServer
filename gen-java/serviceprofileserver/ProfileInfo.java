/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package serviceprofileserver;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2018-03-07")
public class ProfileInfo implements org.apache.thrift.TBase<ProfileInfo, ProfileInfo._Fields>, java.io.Serializable, Cloneable, Comparable<ProfileInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProfileInfo");

  private static final org.apache.thrift.protocol.TField USER_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("userName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField EMAIL_FIELD_DESC = new org.apache.thrift.protocol.TField("email", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PHONE_NUMBER_FIELD_DESC = new org.apache.thrift.protocol.TField("phoneNumber", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField BIRTHDAY_FIELD_DESC = new org.apache.thrift.protocol.TField("birthday", org.apache.thrift.protocol.TType.STRUCT, (short)4);
  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProfileInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProfileInfoTupleSchemeFactory());
  }

  public String userName; // required
  public String email; // required
  public String phoneNumber; // required
  public Day birthday; // required
  public String id; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_NAME((short)1, "userName"),
    EMAIL((short)2, "email"),
    PHONE_NUMBER((short)3, "phoneNumber"),
    BIRTHDAY((short)4, "birthday"),
    ID((short)5, "id");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // USER_NAME
          return USER_NAME;
        case 2: // EMAIL
          return EMAIL;
        case 3: // PHONE_NUMBER
          return PHONE_NUMBER;
        case 4: // BIRTHDAY
          return BIRTHDAY;
        case 5: // ID
          return ID;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_NAME, new org.apache.thrift.meta_data.FieldMetaData("userName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.EMAIL, new org.apache.thrift.meta_data.FieldMetaData("email", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PHONE_NUMBER, new org.apache.thrift.meta_data.FieldMetaData("phoneNumber", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BIRTHDAY, new org.apache.thrift.meta_data.FieldMetaData("birthday", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Day.class)));
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProfileInfo.class, metaDataMap);
  }

  public ProfileInfo() {
  }

  public ProfileInfo(
    String userName,
    String email,
    String phoneNumber,
    Day birthday,
    String id)
  {
    this();
    this.userName = userName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.birthday = birthday;
    this.id = id;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProfileInfo(ProfileInfo other) {
    if (other.isSetUserName()) {
      this.userName = other.userName;
    }
    if (other.isSetEmail()) {
      this.email = other.email;
    }
    if (other.isSetPhoneNumber()) {
      this.phoneNumber = other.phoneNumber;
    }
    if (other.isSetBirthday()) {
      this.birthday = new Day(other.birthday);
    }
    if (other.isSetId()) {
      this.id = other.id;
    }
  }

  public ProfileInfo deepCopy() {
    return new ProfileInfo(this);
  }

  @Override
  public void clear() {
    this.userName = null;
    this.email = null;
    this.phoneNumber = null;
    this.birthday = null;
    this.id = null;
  }

  public String getUserName() {
    return this.userName;
  }

  public ProfileInfo setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public void unsetUserName() {
    this.userName = null;
  }

  /** Returns true if field userName is set (has been assigned a value) and false otherwise */
  public boolean isSetUserName() {
    return this.userName != null;
  }

  public void setUserNameIsSet(boolean value) {
    if (!value) {
      this.userName = null;
    }
  }

  public String getEmail() {
    return this.email;
  }

  public ProfileInfo setEmail(String email) {
    this.email = email;
    return this;
  }

  public void unsetEmail() {
    this.email = null;
  }

  /** Returns true if field email is set (has been assigned a value) and false otherwise */
  public boolean isSetEmail() {
    return this.email != null;
  }

  public void setEmailIsSet(boolean value) {
    if (!value) {
      this.email = null;
    }
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public ProfileInfo setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public void unsetPhoneNumber() {
    this.phoneNumber = null;
  }

  /** Returns true if field phoneNumber is set (has been assigned a value) and false otherwise */
  public boolean isSetPhoneNumber() {
    return this.phoneNumber != null;
  }

  public void setPhoneNumberIsSet(boolean value) {
    if (!value) {
      this.phoneNumber = null;
    }
  }

  public Day getBirthday() {
    return this.birthday;
  }

  public ProfileInfo setBirthday(Day birthday) {
    this.birthday = birthday;
    return this;
  }

  public void unsetBirthday() {
    this.birthday = null;
  }

  /** Returns true if field birthday is set (has been assigned a value) and false otherwise */
  public boolean isSetBirthday() {
    return this.birthday != null;
  }

  public void setBirthdayIsSet(boolean value) {
    if (!value) {
      this.birthday = null;
    }
  }

  public String getId() {
    return this.id;
  }

  public ProfileInfo setId(String id) {
    this.id = id;
    return this;
  }

  public void unsetId() {
    this.id = null;
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return this.id != null;
  }

  public void setIdIsSet(boolean value) {
    if (!value) {
      this.id = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case USER_NAME:
      if (value == null) {
        unsetUserName();
      } else {
        setUserName((String)value);
      }
      break;

    case EMAIL:
      if (value == null) {
        unsetEmail();
      } else {
        setEmail((String)value);
      }
      break;

    case PHONE_NUMBER:
      if (value == null) {
        unsetPhoneNumber();
      } else {
        setPhoneNumber((String)value);
      }
      break;

    case BIRTHDAY:
      if (value == null) {
        unsetBirthday();
      } else {
        setBirthday((Day)value);
      }
      break;

    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_NAME:
      return getUserName();

    case EMAIL:
      return getEmail();

    case PHONE_NUMBER:
      return getPhoneNumber();

    case BIRTHDAY:
      return getBirthday();

    case ID:
      return getId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case USER_NAME:
      return isSetUserName();
    case EMAIL:
      return isSetEmail();
    case PHONE_NUMBER:
      return isSetPhoneNumber();
    case BIRTHDAY:
      return isSetBirthday();
    case ID:
      return isSetId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProfileInfo)
      return this.equals((ProfileInfo)that);
    return false;
  }

  public boolean equals(ProfileInfo that) {
    if (that == null)
      return false;

    boolean this_present_userName = true && this.isSetUserName();
    boolean that_present_userName = true && that.isSetUserName();
    if (this_present_userName || that_present_userName) {
      if (!(this_present_userName && that_present_userName))
        return false;
      if (!this.userName.equals(that.userName))
        return false;
    }

    boolean this_present_email = true && this.isSetEmail();
    boolean that_present_email = true && that.isSetEmail();
    if (this_present_email || that_present_email) {
      if (!(this_present_email && that_present_email))
        return false;
      if (!this.email.equals(that.email))
        return false;
    }

    boolean this_present_phoneNumber = true && this.isSetPhoneNumber();
    boolean that_present_phoneNumber = true && that.isSetPhoneNumber();
    if (this_present_phoneNumber || that_present_phoneNumber) {
      if (!(this_present_phoneNumber && that_present_phoneNumber))
        return false;
      if (!this.phoneNumber.equals(that.phoneNumber))
        return false;
    }

    boolean this_present_birthday = true && this.isSetBirthday();
    boolean that_present_birthday = true && that.isSetBirthday();
    if (this_present_birthday || that_present_birthday) {
      if (!(this_present_birthday && that_present_birthday))
        return false;
      if (!this.birthday.equals(that.birthday))
        return false;
    }

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (!this.id.equals(that.id))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_userName = true && (isSetUserName());
    list.add(present_userName);
    if (present_userName)
      list.add(userName);

    boolean present_email = true && (isSetEmail());
    list.add(present_email);
    if (present_email)
      list.add(email);

    boolean present_phoneNumber = true && (isSetPhoneNumber());
    list.add(present_phoneNumber);
    if (present_phoneNumber)
      list.add(phoneNumber);

    boolean present_birthday = true && (isSetBirthday());
    list.add(present_birthday);
    if (present_birthday)
      list.add(birthday);

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    return list.hashCode();
  }

  @Override
  public int compareTo(ProfileInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetUserName()).compareTo(other.isSetUserName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userName, other.userName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEmail()).compareTo(other.isSetEmail());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEmail()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.email, other.email);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPhoneNumber()).compareTo(other.isSetPhoneNumber());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPhoneNumber()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.phoneNumber, other.phoneNumber);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBirthday()).compareTo(other.isSetBirthday());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBirthday()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.birthday, other.birthday);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ProfileInfo(");
    boolean first = true;

    sb.append("userName:");
    if (this.userName == null) {
      sb.append("null");
    } else {
      sb.append(this.userName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("email:");
    if (this.email == null) {
      sb.append("null");
    } else {
      sb.append(this.email);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("phoneNumber:");
    if (this.phoneNumber == null) {
      sb.append("null");
    } else {
      sb.append(this.phoneNumber);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("birthday:");
    if (this.birthday == null) {
      sb.append("null");
    } else {
      sb.append(this.birthday);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("id:");
    if (this.id == null) {
      sb.append("null");
    } else {
      sb.append(this.id);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (birthday != null) {
      birthday.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ProfileInfoStandardSchemeFactory implements SchemeFactory {
    public ProfileInfoStandardScheme getScheme() {
      return new ProfileInfoStandardScheme();
    }
  }

  private static class ProfileInfoStandardScheme extends StandardScheme<ProfileInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProfileInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USER_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userName = iprot.readString();
              struct.setUserNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // EMAIL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.email = iprot.readString();
              struct.setEmailIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PHONE_NUMBER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.phoneNumber = iprot.readString();
              struct.setPhoneNumberIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // BIRTHDAY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.birthday = new Day();
              struct.birthday.read(iprot);
              struct.setBirthdayIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.id = iprot.readString();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProfileInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.userName != null) {
        oprot.writeFieldBegin(USER_NAME_FIELD_DESC);
        oprot.writeString(struct.userName);
        oprot.writeFieldEnd();
      }
      if (struct.email != null) {
        oprot.writeFieldBegin(EMAIL_FIELD_DESC);
        oprot.writeString(struct.email);
        oprot.writeFieldEnd();
      }
      if (struct.phoneNumber != null) {
        oprot.writeFieldBegin(PHONE_NUMBER_FIELD_DESC);
        oprot.writeString(struct.phoneNumber);
        oprot.writeFieldEnd();
      }
      if (struct.birthday != null) {
        oprot.writeFieldBegin(BIRTHDAY_FIELD_DESC);
        struct.birthday.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.id != null) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeString(struct.id);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProfileInfoTupleSchemeFactory implements SchemeFactory {
    public ProfileInfoTupleScheme getScheme() {
      return new ProfileInfoTupleScheme();
    }
  }

  private static class ProfileInfoTupleScheme extends TupleScheme<ProfileInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProfileInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUserName()) {
        optionals.set(0);
      }
      if (struct.isSetEmail()) {
        optionals.set(1);
      }
      if (struct.isSetPhoneNumber()) {
        optionals.set(2);
      }
      if (struct.isSetBirthday()) {
        optionals.set(3);
      }
      if (struct.isSetId()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetUserName()) {
        oprot.writeString(struct.userName);
      }
      if (struct.isSetEmail()) {
        oprot.writeString(struct.email);
      }
      if (struct.isSetPhoneNumber()) {
        oprot.writeString(struct.phoneNumber);
      }
      if (struct.isSetBirthday()) {
        struct.birthday.write(oprot);
      }
      if (struct.isSetId()) {
        oprot.writeString(struct.id);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProfileInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.userName = iprot.readString();
        struct.setUserNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.email = iprot.readString();
        struct.setEmailIsSet(true);
      }
      if (incoming.get(2)) {
        struct.phoneNumber = iprot.readString();
        struct.setPhoneNumberIsSet(true);
      }
      if (incoming.get(3)) {
        struct.birthday = new Day();
        struct.birthday.read(iprot);
        struct.setBirthdayIsSet(true);
      }
      if (incoming.get(4)) {
        struct.id = iprot.readString();
        struct.setIdIsSet(true);
      }
    }
  }

}

