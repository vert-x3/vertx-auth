package io.vertx.ext.auth.mongo;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.ext.auth.mongo.MongoAuthOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.auth.mongo.MongoAuthOptions} original class using Vert.x codegen.
 */
public class MongoAuthOptionsConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

   static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, MongoAuthOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "collectionName":
          if (member.getValue() instanceof String) {
            obj.setCollectionName((String)member.getValue());
          }
          break;
        case "config":
          if (member.getValue() instanceof JsonObject) {
            obj.setConfig(((JsonObject)member.getValue()).copy());
          }
          break;
        case "datasourceName":
          if (member.getValue() instanceof String) {
            obj.setDatasourceName((String)member.getValue());
          }
          break;
        case "passwordField":
          if (member.getValue() instanceof String) {
            obj.setPasswordField((String)member.getValue());
          }
          break;
        case "permissionField":
          if (member.getValue() instanceof String) {
            obj.setPermissionField((String)member.getValue());
          }
          break;
        case "roleField":
          if (member.getValue() instanceof String) {
            obj.setRoleField((String)member.getValue());
          }
          break;
        case "saltField":
          if (member.getValue() instanceof String) {
            obj.setSaltField((String)member.getValue());
          }
          break;
        case "saltStyle":
          if (member.getValue() instanceof String) {
            obj.setSaltStyle(io.vertx.ext.auth.mongo.HashSaltStyle.valueOf((String)member.getValue()));
          }
          break;
        case "shared":
          if (member.getValue() instanceof Boolean) {
            obj.setShared((Boolean)member.getValue());
          }
          break;
        case "usernameCredentialField":
          if (member.getValue() instanceof String) {
            obj.setUsernameCredentialField((String)member.getValue());
          }
          break;
        case "usernameField":
          if (member.getValue() instanceof String) {
            obj.setUsernameField((String)member.getValue());
          }
          break;
      }
    }
  }

   static void toJson(MongoAuthOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

   static void toJson(MongoAuthOptions obj, java.util.Map<String, Object> json) {
    if (obj.getCollectionName() != null) {
      json.put("collectionName", obj.getCollectionName());
    }
    if (obj.getConfig() != null) {
      json.put("config", obj.getConfig());
    }
    if (obj.getDatasourceName() != null) {
      json.put("datasourceName", obj.getDatasourceName());
    }
    if (obj.getPasswordField() != null) {
      json.put("passwordField", obj.getPasswordField());
    }
    if (obj.getPermissionField() != null) {
      json.put("permissionField", obj.getPermissionField());
    }
    if (obj.getRoleField() != null) {
      json.put("roleField", obj.getRoleField());
    }
    if (obj.getSaltField() != null) {
      json.put("saltField", obj.getSaltField());
    }
    if (obj.getSaltStyle() != null) {
      json.put("saltStyle", obj.getSaltStyle().name());
    }
    json.put("shared", obj.getShared());
    if (obj.getUsernameCredentialField() != null) {
      json.put("usernameCredentialField", obj.getUsernameCredentialField());
    }
    if (obj.getUsernameField() != null) {
      json.put("usernameField", obj.getUsernameField());
    }
  }
}
