// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: sumtimes.proto

package com.pavel.sumtimes.commons;

public final class Sumtimes {
  private Sumtimes() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface SumtimeOrBuilder extends
      // @@protoc_insertion_point(interface_extends:commons.Sumtime)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional .commons.Sumtime.Value value = 1;</code>
     */
    boolean hasValue();
    /**
     * <code>optional .commons.Sumtime.Value value = 1;</code>
     */
    com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value getValue();
    /**
     * <code>optional .commons.Sumtime.Value value = 1;</code>
     */
    com.pavel.sumtimes.commons.Sumtimes.Sumtime.ValueOrBuilder getValueOrBuilder();
  }
  /**
   * Protobuf type {@code commons.Sumtime}
   */
  public  static final class Sumtime extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:commons.Sumtime)
      SumtimeOrBuilder {
    // Use Sumtime.newBuilder() to construct.
    private Sumtime(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Sumtime() {
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private Sumtime(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.Builder subBuilder = null;
              if (value_ != null) {
                subBuilder = value_.toBuilder();
              }
              value_ = input.readMessage(com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(value_);
                value_ = subBuilder.buildPartial();
              }

              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.pavel.sumtimes.commons.Sumtimes.internal_static_commons_Sumtime_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.pavel.sumtimes.commons.Sumtimes.internal_static_commons_Sumtime_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.pavel.sumtimes.commons.Sumtimes.Sumtime.class, com.pavel.sumtimes.commons.Sumtimes.Sumtime.Builder.class);
    }

    public interface ValueOrBuilder extends
        // @@protoc_insertion_point(interface_extends:commons.Sumtime.Value)
        com.google.protobuf.MessageOrBuilder {

      /**
       * <code>optional int64 value = 1;</code>
       */
      long getValue();
    }
    /**
     * Protobuf type {@code commons.Sumtime.Value}
     */
    public  static final class Value extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:commons.Sumtime.Value)
        ValueOrBuilder {
      // Use Value.newBuilder() to construct.
      private Value(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
      }
      private Value() {
        value_ = 0L;
      }

      @java.lang.Override
      public final com.google.protobuf.UnknownFieldSet
      getUnknownFields() {
        return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
      }
      private Value(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        this();
        int mutable_bitField0_ = 0;
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              default: {
                if (!input.skipField(tag)) {
                  done = true;
                }
                break;
              }
              case 8: {

                value_ = input.readInt64();
                break;
              }
            }
          }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(
              e).setUnfinishedMessage(this);
        } finally {
          makeExtensionsImmutable();
        }
      }
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.pavel.sumtimes.commons.Sumtimes.internal_static_commons_Sumtime_Value_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.pavel.sumtimes.commons.Sumtimes.internal_static_commons_Sumtime_Value_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.class, com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.Builder.class);
      }

      public static final int VALUE_FIELD_NUMBER = 1;
      private long value_;
      /**
       * <code>optional int64 value = 1;</code>
       */
      public long getValue() {
        return value_;
      }

      private byte memoizedIsInitialized = -1;
      public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
      }

      public void writeTo(com.google.protobuf.CodedOutputStream output)
                          throws java.io.IOException {
        if (value_ != 0L) {
          output.writeInt64(1, value_);
        }
      }

      public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        if (value_ != 0L) {
          size += com.google.protobuf.CodedOutputStream
            .computeInt64Size(1, value_);
        }
        memoizedSize = size;
        return size;
      }

      private static final long serialVersionUID = 0L;
      @java.lang.Override
      public boolean equals(final java.lang.Object obj) {
        if (obj == this) {
         return true;
        }
        if (!(obj instanceof com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value)) {
          return super.equals(obj);
        }
        com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value other = (com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value) obj;

        boolean result = true;
        result = result && (getValue()
            == other.getValue());
        return result;
      }

      @java.lang.Override
      public int hashCode() {
        if (memoizedHashCode != 0) {
          return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptorForType().hashCode();
        hash = (37 * hash) + VALUE_FIELD_NUMBER;
        hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
            getValue());
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
      }

      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parseFrom(
          com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
      }
      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parseFrom(byte[] data)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
      }
      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parseFrom(
          byte[] data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
      }
      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parseFrom(java.io.InputStream input)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
      }
      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parseFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
      }
      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parseDelimitedFrom(java.io.InputStream input)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input);
      }
      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parseDelimitedFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
      }
      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parseFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
      }
      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parseFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
      }

      public Builder newBuilderForType() { return newBuilder(); }
      public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
      }
      public static Builder newBuilder(com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
      }
      public Builder toBuilder() {
        return this == DEFAULT_INSTANCE
            ? new Builder() : new Builder().mergeFrom(this);
      }

      @java.lang.Override
      protected Builder newBuilderForType(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
      }
      /**
       * Protobuf type {@code commons.Sumtime.Value}
       */
      public static final class Builder extends
          com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
          // @@protoc_insertion_point(builder_implements:commons.Sumtime.Value)
          com.pavel.sumtimes.commons.Sumtimes.Sumtime.ValueOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
          return com.pavel.sumtimes.commons.Sumtimes.internal_static_commons_Sumtime_Value_descriptor;
        }

        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internalGetFieldAccessorTable() {
          return com.pavel.sumtimes.commons.Sumtimes.internal_static_commons_Sumtime_Value_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                  com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.class, com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.Builder.class);
        }

        // Construct using com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.newBuilder()
        private Builder() {
          maybeForceBuilderInitialization();
        }

        private Builder(
            com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
          super(parent);
          maybeForceBuilderInitialization();
        }
        private void maybeForceBuilderInitialization() {
          if (com.google.protobuf.GeneratedMessageV3
                  .alwaysUseFieldBuilders) {
          }
        }
        public Builder clear() {
          super.clear();
          value_ = 0L;

          return this;
        }

        public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
          return com.pavel.sumtimes.commons.Sumtimes.internal_static_commons_Sumtime_Value_descriptor;
        }

        public com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value getDefaultInstanceForType() {
          return com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.getDefaultInstance();
        }

        public com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value build() {
          com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value result = buildPartial();
          if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
          }
          return result;
        }

        public com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value buildPartial() {
          com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value result = new com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value(this);
          result.value_ = value_;
          onBuilt();
          return result;
        }

        public Builder clone() {
          return (Builder) super.clone();
        }
        public Builder setField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            Object value) {
          return (Builder) super.setField(field, value);
        }
        public Builder clearField(
            com.google.protobuf.Descriptors.FieldDescriptor field) {
          return (Builder) super.clearField(field);
        }
        public Builder clearOneof(
            com.google.protobuf.Descriptors.OneofDescriptor oneof) {
          return (Builder) super.clearOneof(oneof);
        }
        public Builder setRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            int index, Object value) {
          return (Builder) super.setRepeatedField(field, index, value);
        }
        public Builder addRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            Object value) {
          return (Builder) super.addRepeatedField(field, value);
        }
        public Builder mergeFrom(com.google.protobuf.Message other) {
          if (other instanceof com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value) {
            return mergeFrom((com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value)other);
          } else {
            super.mergeFrom(other);
            return this;
          }
        }

        public Builder mergeFrom(com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value other) {
          if (other == com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.getDefaultInstance()) return this;
          if (other.getValue() != 0L) {
            setValue(other.getValue());
          }
          onChanged();
          return this;
        }

        public final boolean isInitialized() {
          return true;
        }

        public Builder mergeFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
          com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value parsedMessage = null;
          try {
            parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            parsedMessage = (com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value) e.getUnfinishedMessage();
            throw e.unwrapIOException();
          } finally {
            if (parsedMessage != null) {
              mergeFrom(parsedMessage);
            }
          }
          return this;
        }

        private long value_ ;
        /**
         * <code>optional int64 value = 1;</code>
         */
        public long getValue() {
          return value_;
        }
        /**
         * <code>optional int64 value = 1;</code>
         */
        public Builder setValue(long value) {
          
          value_ = value;
          onChanged();
          return this;
        }
        /**
         * <code>optional int64 value = 1;</code>
         */
        public Builder clearValue() {
          
          value_ = 0L;
          onChanged();
          return this;
        }
        public final Builder setUnknownFields(
            final com.google.protobuf.UnknownFieldSet unknownFields) {
          return this;
        }

        public final Builder mergeUnknownFields(
            final com.google.protobuf.UnknownFieldSet unknownFields) {
          return this;
        }


        // @@protoc_insertion_point(builder_scope:commons.Sumtime.Value)
      }

      // @@protoc_insertion_point(class_scope:commons.Sumtime.Value)
      private static final com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value DEFAULT_INSTANCE;
      static {
        DEFAULT_INSTANCE = new com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value();
      }

      public static com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value getDefaultInstance() {
        return DEFAULT_INSTANCE;
      }

      private static final com.google.protobuf.Parser<Value>
          PARSER = new com.google.protobuf.AbstractParser<Value>() {
        public Value parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
            return new Value(input, extensionRegistry);
        }
      };

      public static com.google.protobuf.Parser<Value> parser() {
        return PARSER;
      }

      @java.lang.Override
      public com.google.protobuf.Parser<Value> getParserForType() {
        return PARSER;
      }

      public com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
      }

    }

    public static final int VALUE_FIELD_NUMBER = 1;
    private com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value value_;
    /**
     * <code>optional .commons.Sumtime.Value value = 1;</code>
     */
    public boolean hasValue() {
      return value_ != null;
    }
    /**
     * <code>optional .commons.Sumtime.Value value = 1;</code>
     */
    public com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value getValue() {
      return value_ == null ? com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.getDefaultInstance() : value_;
    }
    /**
     * <code>optional .commons.Sumtime.Value value = 1;</code>
     */
    public com.pavel.sumtimes.commons.Sumtimes.Sumtime.ValueOrBuilder getValueOrBuilder() {
      return getValue();
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (value_ != null) {
        output.writeMessage(1, getValue());
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (value_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getValue());
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.pavel.sumtimes.commons.Sumtimes.Sumtime)) {
        return super.equals(obj);
      }
      com.pavel.sumtimes.commons.Sumtimes.Sumtime other = (com.pavel.sumtimes.commons.Sumtimes.Sumtime) obj;

      boolean result = true;
      result = result && (hasValue() == other.hasValue());
      if (hasValue()) {
        result = result && getValue()
            .equals(other.getValue());
      }
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      if (hasValue()) {
        hash = (37 * hash) + VALUE_FIELD_NUMBER;
        hash = (53 * hash) + getValue().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.pavel.sumtimes.commons.Sumtimes.Sumtime prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code commons.Sumtime}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:commons.Sumtime)
        com.pavel.sumtimes.commons.Sumtimes.SumtimeOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.pavel.sumtimes.commons.Sumtimes.internal_static_commons_Sumtime_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.pavel.sumtimes.commons.Sumtimes.internal_static_commons_Sumtime_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.pavel.sumtimes.commons.Sumtimes.Sumtime.class, com.pavel.sumtimes.commons.Sumtimes.Sumtime.Builder.class);
      }

      // Construct using com.pavel.sumtimes.commons.Sumtimes.Sumtime.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        if (valueBuilder_ == null) {
          value_ = null;
        } else {
          value_ = null;
          valueBuilder_ = null;
        }
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.pavel.sumtimes.commons.Sumtimes.internal_static_commons_Sumtime_descriptor;
      }

      public com.pavel.sumtimes.commons.Sumtimes.Sumtime getDefaultInstanceForType() {
        return com.pavel.sumtimes.commons.Sumtimes.Sumtime.getDefaultInstance();
      }

      public com.pavel.sumtimes.commons.Sumtimes.Sumtime build() {
        com.pavel.sumtimes.commons.Sumtimes.Sumtime result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.pavel.sumtimes.commons.Sumtimes.Sumtime buildPartial() {
        com.pavel.sumtimes.commons.Sumtimes.Sumtime result = new com.pavel.sumtimes.commons.Sumtimes.Sumtime(this);
        if (valueBuilder_ == null) {
          result.value_ = value_;
        } else {
          result.value_ = valueBuilder_.build();
        }
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.pavel.sumtimes.commons.Sumtimes.Sumtime) {
          return mergeFrom((com.pavel.sumtimes.commons.Sumtimes.Sumtime)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.pavel.sumtimes.commons.Sumtimes.Sumtime other) {
        if (other == com.pavel.sumtimes.commons.Sumtimes.Sumtime.getDefaultInstance()) return this;
        if (other.hasValue()) {
          mergeValue(other.getValue());
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.pavel.sumtimes.commons.Sumtimes.Sumtime parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.pavel.sumtimes.commons.Sumtimes.Sumtime) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value value_ = null;
      private com.google.protobuf.SingleFieldBuilderV3<
          com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value, com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.Builder, com.pavel.sumtimes.commons.Sumtimes.Sumtime.ValueOrBuilder> valueBuilder_;
      /**
       * <code>optional .commons.Sumtime.Value value = 1;</code>
       */
      public boolean hasValue() {
        return valueBuilder_ != null || value_ != null;
      }
      /**
       * <code>optional .commons.Sumtime.Value value = 1;</code>
       */
      public com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value getValue() {
        if (valueBuilder_ == null) {
          return value_ == null ? com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.getDefaultInstance() : value_;
        } else {
          return valueBuilder_.getMessage();
        }
      }
      /**
       * <code>optional .commons.Sumtime.Value value = 1;</code>
       */
      public Builder setValue(com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value value) {
        if (valueBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          value_ = value;
          onChanged();
        } else {
          valueBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>optional .commons.Sumtime.Value value = 1;</code>
       */
      public Builder setValue(
          com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.Builder builderForValue) {
        if (valueBuilder_ == null) {
          value_ = builderForValue.build();
          onChanged();
        } else {
          valueBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>optional .commons.Sumtime.Value value = 1;</code>
       */
      public Builder mergeValue(com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value value) {
        if (valueBuilder_ == null) {
          if (value_ != null) {
            value_ =
              com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.newBuilder(value_).mergeFrom(value).buildPartial();
          } else {
            value_ = value;
          }
          onChanged();
        } else {
          valueBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>optional .commons.Sumtime.Value value = 1;</code>
       */
      public Builder clearValue() {
        if (valueBuilder_ == null) {
          value_ = null;
          onChanged();
        } else {
          value_ = null;
          valueBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>optional .commons.Sumtime.Value value = 1;</code>
       */
      public com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.Builder getValueBuilder() {
        
        onChanged();
        return getValueFieldBuilder().getBuilder();
      }
      /**
       * <code>optional .commons.Sumtime.Value value = 1;</code>
       */
      public com.pavel.sumtimes.commons.Sumtimes.Sumtime.ValueOrBuilder getValueOrBuilder() {
        if (valueBuilder_ != null) {
          return valueBuilder_.getMessageOrBuilder();
        } else {
          return value_ == null ?
              com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.getDefaultInstance() : value_;
        }
      }
      /**
       * <code>optional .commons.Sumtime.Value value = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value, com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.Builder, com.pavel.sumtimes.commons.Sumtimes.Sumtime.ValueOrBuilder> 
          getValueFieldBuilder() {
        if (valueBuilder_ == null) {
          valueBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value, com.pavel.sumtimes.commons.Sumtimes.Sumtime.Value.Builder, com.pavel.sumtimes.commons.Sumtimes.Sumtime.ValueOrBuilder>(
                  getValue(),
                  getParentForChildren(),
                  isClean());
          value_ = null;
        }
        return valueBuilder_;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:commons.Sumtime)
    }

    // @@protoc_insertion_point(class_scope:commons.Sumtime)
    private static final com.pavel.sumtimes.commons.Sumtimes.Sumtime DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.pavel.sumtimes.commons.Sumtimes.Sumtime();
    }

    public static com.pavel.sumtimes.commons.Sumtimes.Sumtime getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Sumtime>
        PARSER = new com.google.protobuf.AbstractParser<Sumtime>() {
      public Sumtime parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new Sumtime(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Sumtime> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Sumtime> getParserForType() {
      return PARSER;
    }

    public com.pavel.sumtimes.commons.Sumtimes.Sumtime getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_commons_Sumtime_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_commons_Sumtime_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_commons_Sumtime_Value_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_commons_Sumtime_Value_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016sumtimes.proto\022\007commons\"H\n\007Sumtime\022%\n\005" +
      "value\030\001 \001(\0132\026.commons.Sumtime.Value\032\026\n\005V" +
      "alue\022\r\n\005value\030\001 \001(\003B\034\n\032com.pavel.sumtime" +
      "s.commonsb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_commons_Sumtime_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_commons_Sumtime_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_commons_Sumtime_descriptor,
        new java.lang.String[] { "Value", });
    internal_static_commons_Sumtime_Value_descriptor =
      internal_static_commons_Sumtime_descriptor.getNestedTypes().get(0);
    internal_static_commons_Sumtime_Value_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_commons_Sumtime_Value_descriptor,
        new java.lang.String[] { "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
