// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api.proto

package store.viewer;

/**
 * Protobuf enum {@code store.viewer.Type}
 */
public enum Type
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>ACCESSORIES = 0;</code>
   */
  ACCESSORIES(0),
  /**
   * <code>LEISURE = 1;</code>
   */
  LEISURE(1),
  /**
   * <code>BOOKS_MEDIA_ELECTRONICS = 3;</code>
   */
  BOOKS_MEDIA_ELECTRONICS(3),
  /**
   * <code>HEALTH_BEAUTY = 4;</code>
   */
  HEALTH_BEAUTY(4),
  /**
   * <code>OPTICIAN = 5;</code>
   */
  OPTICIAN(5),
  /**
   * <code>HOME_DECORATION = 6;</code>
   */
  HOME_DECORATION(6),
  /**
   * <code>TOYS_HOBBY = 7;</code>
   */
  TOYS_HOBBY(7),
  /**
   * <code>GROCERIES = 8;</code>
   */
  GROCERIES(8),
  /**
   * <code>RESTAURANT_CAFE = 9;</code>
   */
  RESTAURANT_CAFE(9),
  /**
   * <code>JEWELLERY = 10;</code>
   */
  JEWELLERY(10),
  /**
   * <code>STAND = 11;</code>
   */
  STAND(11),
  /**
   * <code>FASHION = 12;</code>
   */
  FASHION(12),
  /**
   * <code>SERVICE = 13;</code>
   */
  SERVICE(13),
  /**
   * <code>SPORTS = 14;</code>
   */
  SPORTS(14),
  /**
   * <code>UNKOWN = 15;</code>
   */
  UNKOWN(15),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>ACCESSORIES = 0;</code>
   */
  public static final int ACCESSORIES_VALUE = 0;
  /**
   * <code>LEISURE = 1;</code>
   */
  public static final int LEISURE_VALUE = 1;
  /**
   * <code>BOOKS_MEDIA_ELECTRONICS = 3;</code>
   */
  public static final int BOOKS_MEDIA_ELECTRONICS_VALUE = 3;
  /**
   * <code>HEALTH_BEAUTY = 4;</code>
   */
  public static final int HEALTH_BEAUTY_VALUE = 4;
  /**
   * <code>OPTICIAN = 5;</code>
   */
  public static final int OPTICIAN_VALUE = 5;
  /**
   * <code>HOME_DECORATION = 6;</code>
   */
  public static final int HOME_DECORATION_VALUE = 6;
  /**
   * <code>TOYS_HOBBY = 7;</code>
   */
  public static final int TOYS_HOBBY_VALUE = 7;
  /**
   * <code>GROCERIES = 8;</code>
   */
  public static final int GROCERIES_VALUE = 8;
  /**
   * <code>RESTAURANT_CAFE = 9;</code>
   */
  public static final int RESTAURANT_CAFE_VALUE = 9;
  /**
   * <code>JEWELLERY = 10;</code>
   */
  public static final int JEWELLERY_VALUE = 10;
  /**
   * <code>STAND = 11;</code>
   */
  public static final int STAND_VALUE = 11;
  /**
   * <code>FASHION = 12;</code>
   */
  public static final int FASHION_VALUE = 12;
  /**
   * <code>SERVICE = 13;</code>
   */
  public static final int SERVICE_VALUE = 13;
  /**
   * <code>SPORTS = 14;</code>
   */
  public static final int SPORTS_VALUE = 14;
  /**
   * <code>UNKOWN = 15;</code>
   */
  public static final int UNKOWN_VALUE = 15;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static Type valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static Type forNumber(int value) {
    switch (value) {
      case 0: return ACCESSORIES;
      case 1: return LEISURE;
      case 3: return BOOKS_MEDIA_ELECTRONICS;
      case 4: return HEALTH_BEAUTY;
      case 5: return OPTICIAN;
      case 6: return HOME_DECORATION;
      case 7: return TOYS_HOBBY;
      case 8: return GROCERIES;
      case 9: return RESTAURANT_CAFE;
      case 10: return JEWELLERY;
      case 11: return STAND;
      case 12: return FASHION;
      case 13: return SERVICE;
      case 14: return SPORTS;
      case 15: return UNKOWN;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Type>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      Type> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Type>() {
          public Type findValueByNumber(int number) {
            return Type.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return store.viewer.Api.getDescriptor().getEnumTypes().get(0);
  }

  private static final Type[] VALUES = values();

  public static Type valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private Type(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:store.viewer.Type)
}

