package io.vertx.ext.auth.jdbc;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.ext.auth.jdbc.JDBCAuthorizationOptions}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.auth.jdbc.JDBCAuthorizationOptions} original class using Vert.x codegen.
 */
public class JDBCAuthorizationOptionsConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

   static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, JDBCAuthorizationOptions obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "permissionsQuery":
          if (member.getValue() instanceof String) {
            obj.setPermissionsQuery((String)member.getValue());
          }
          break;
        case "rolesQuery":
          if (member.getValue() instanceof String) {
            obj.setRolesQuery((String)member.getValue());
          }
          break;
      }
    }
  }

   static void toJson(JDBCAuthorizationOptions obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

   static void toJson(JDBCAuthorizationOptions obj, java.util.Map<String, Object> json) {
    if (obj.getPermissionsQuery() != null) {
      json.put("permissionsQuery", obj.getPermissionsQuery());
    }
    if (obj.getRolesQuery() != null) {
      json.put("rolesQuery", obj.getRolesQuery());
    }
  }
}
