package com.kgregorczyk.store.cqrs.kafka;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class UUIDSerializer implements Serializer<UUID> {

  private String encoding = "UTF8";

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    String propertyName = isKey ? "key.serializer.encoding" : "value.serializer.encoding";
    Object encodingValue = configs.get(propertyName);
    if (encodingValue == null) {
      encodingValue = configs.get("serializer.encoding");
    }
    if (encodingValue instanceof String) {
      encoding = (String) encodingValue;
    }
  }

  @Override
  public byte[] serialize(String topic, UUID data) {
    try {
      if (data == null) {
        return null;
      }
      return data.toString().getBytes(encoding);
    } catch (UnsupportedEncodingException e) {
      throw new SerializationException(
          "Error when serializing string to byte[] due to unsupported encoding " + encoding);
    }
  }

  @Override
  public void close() {
    // nothing to do
  }
}
