package io.vertx.ext.auth.htdigest;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class RegressionTest {

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void authTest(TestContext should) {
    final Async test = should.async();
    HtdigestAuth authProvider = HtdigestAuth.create(rule.vertx(), "regression.htdigest");
    JsonObject authInfo = new JsonObject()
      .put("username", "usain")
      .put("realm", "jcrealm@host.com")
      .put("nonce", "28ee8d494b645014eefcd66ac3ddcade")
      .put("method", "GET")
      .put("uri", "/private/private_page.html")
      .put("response", "373408962ca454892aba7c236af6d7a9");
    authProvider.authenticate(authInfo, res -> {
      if (res.succeeded()) {
        User user = res.result();
        should.assertEquals("jcrealm@host.com", user.principal().getString("realm"));
        test.complete();
      } else {
        should.fail(res.cause());
      }
    });
  }

}
