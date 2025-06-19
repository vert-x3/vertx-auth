package io.vertx.ext.auth.jdbc;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.ext.auth.jdbc.JDBCAuthOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.auth.jdbc.JDBCAuthOptions} original class using Vert.x codegen.
 */
public class JDBCAuthOptionsConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

   static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, JDBCAuthOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "authenticationQuery":
          if (member.getValue() instanceof String) {
            obj.setAuthenticationQuery((String)member.getValue());
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
        case "permissionsQuery":
          if (member.getValue() instanceof String) {
            obj.setPermissionsQuery((String)member.getValue());
          }
          break;
        case "rolesPrefix":
          if (member.getValue() instanceof String) {
            obj.setRolesPrefix((String)member.getValue());
          }
          break;
        case "rolesQuery":
          if (member.getValue() instanceof String) {
            obj.setRolesQuery((String)member.getValue());
          }
          break;
        case "shared":
          if (member.getValue() instanceof Boolean) {
            obj.setShared((Boolean)member.getValue());
          }
          break;
      }
    }
  }

   static void toJson(JDBCAuthOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

   static void toJson(JDBCAuthOptions obj, java.util.Map<String, Object> json) {
    if (obj.getAuthenticationQuery() != null) {
      json.put("authenticationQuery", obj.getAuthenticationQuery());
    }
    if (obj.getConfig() != null) {
      json.put("config", obj.getConfig());
    }
    if (obj.getDatasourceName() != null) {
      json.put("datasourceName", obj.getDatasourceName());
    }
    if (obj.getPermissionsQuery() != null) {
      json.put("permissionsQuery", obj.getPermissionsQuery());
    }
    if (obj.getRolesPrefix() != null) {
      json.put("rolesPrefix", obj.getRolesPrefix());
    }
    if (obj.getRolesQuery() != null) {
      json.put("rolesQuery", obj.getRolesQuery());
    }
    json.put("shared", obj.isShared());
  }
}
