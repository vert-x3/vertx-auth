/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.ext.auth.mongo.test;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.Credentials;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.auth.mongo.MongoAuthentication;
import io.vertx.ext.auth.mongo.MongoAuthenticationOptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Testing MongoAuth with no encryption for the user password
 *
 * @author mremme
 */

public class MongoAuthenticationTest extends MongoBaseTest {
  private static final Logger log = LoggerFactory.getLogger(MongoAuthenticationTest.class);

  protected MongoAuthenticationOptions authenticationOptions = new MongoAuthenticationOptions().setCollectionName(createCollectionName(MongoAuthentication.DEFAULT_COLLECTION_NAME));

  @Override
  public void setUp() throws Exception {
    super.setUp();
    getMongoClient(); // note: also drop existing collections
  }

  @Before
  public void initTestUsers() throws Exception {
    log.info("initTestUsers");
    List<InternalUser> users = createUserList();
    CountDownLatch latch = new CountDownLatch(users.size());

    for (InternalUser user : users) {
      if (!initOneUser(getAuthenticationProvider(), authenticationOptions, user.username, user.password, latch))
        throw new InitializationError("could not create users");
    }
    awaitLatch(latch);
    if (!verifyUserData(authenticationOptions))
      throw new InitializationError("users weren't created");

  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected MongoAuthentication getAuthenticationProvider() {
    return getAuthenticationProvider(authenticationOptions);
  }

  protected MongoAuthentication getAuthenticationProvider(MongoAuthenticationOptions options) {
      try {
        return MongoAuthentication.create(getMongoClient(), options);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
  }

  @Test
  public void testAuthenticate() {
    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("tim", "sausages");
    getAuthenticationProvider().authenticate(credentials).onComplete(onSuccess(user -> {
      assertNotNull(user);
      testComplete();
    }));
    await();
  }

  @Test
  public void testAuthenticateFailBadPwd() {
    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("tim", "eggs");
    getAuthenticationProvider().authenticate(credentials).onComplete(onFailure(v -> {
      assertTrue(v instanceof Exception);
      testComplete();
    }));
    await();
  }

  @Test
  public void testAuthenticateFailBadUser() {
    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("blah", "whatever");
    getAuthenticationProvider().authenticate(credentials).onComplete(onFailure(v -> {
      assertTrue(v instanceof Exception);
      testComplete();
    }));
    await();
  }

  @Test
  public void testAuthenticateUsesCustomPropertyNames() {
    MongoAuthenticationOptions options = new MongoAuthenticationOptions()
        .setCollectionName(createCollectionName(MongoAuthentication.DEFAULT_COLLECTION_NAME))
        .setUsernameCredentialField("login")
        .setPasswordCredentialField("pwd");
    Credentials credentials = new UsernamePasswordCredentials(
      "tim", "sausages");
    getAuthenticationProvider(options).authenticate(credentials)
      .onSuccess(user -> {
        log.info("authenticated user: " + user);
        assertNotNull(user);
        testComplete();
      })
      .onFailure(this::fail);
    await(5, TimeUnit.SECONDS);
  }

  /*
   * ################################################## preparation methods
   * ##################################################
   */
  private List<InternalUser> createUserList() {
    List<InternalUser> users = new ArrayList<>();
    users.add(new InternalUser("Michael", "ps1"));
    users.add(new InternalUser("Doublette", "ps1"));
    users.add(new InternalUser("Doublette", "ps2"));
    users.add(new InternalUser("Doublette", "ps2"));

    users.add(new InternalUser("tim", "sausages"));
    return users;
  }

  /**
   * Creates a user inside mongo. Returns true, if user was successfully added
   *
   * @param latch
   * @return
   * @throws Exception
   * @throws Throwable
   */
  protected boolean initOneUser(MongoAuthentication authenticationProvider, MongoAuthenticationOptions authenticationOptions, String username, String password, CountDownLatch latch) throws Exception {
    CountDownLatch intLatch = new CountDownLatch(1);
    final StringBuffer buffer = new StringBuffer();

    insertUser(authenticationProvider, authenticationOptions, username, password).onComplete(res -> {
      if (res.succeeded()) {
        log.info("user added: " + username);
        latch.countDown();
      } else {
        log.error("", res.cause());
        buffer.append("false");
      }
      intLatch.countDown();
    });
    awaitLatch(intLatch);
    return buffer.length() == 0;
  }

  private class InternalUser {
    String username;
    String password;

    InternalUser(String username, String password) {
      this.username = username;
      this.password = password;
    }

  }
}
