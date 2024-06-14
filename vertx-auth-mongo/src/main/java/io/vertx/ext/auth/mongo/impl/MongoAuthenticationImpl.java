/*
 * Copyright 2014 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.ext.auth.mongo.impl;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.hashing.HashingStrategy;
import io.vertx.ext.auth.user.User;
import io.vertx.ext.auth.authentication.Credentials;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.auth.mongo.*;
import io.vertx.ext.mongo.MongoClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * An implementation of {@link MongoAuthentication}
 *
 * @author mremme
 */
public class MongoAuthenticationImpl implements MongoAuthentication {

  private final HashingStrategy strategy = HashingStrategy.load();
  private final MongoClient mongoClient;
  private final MongoAuthenticationOptions options;

  /**
   * Creates a new instance
   *
   * @param mongoClient the {@link MongoClient} to be used
   * @param options     the options for configuring the new instance
   */
  public MongoAuthenticationImpl(MongoClient mongoClient, MongoAuthenticationOptions options) {
    this.mongoClient = mongoClient;
    this.options = options;
  }

  @Override
  public Future<User> authenticate(Credentials credentials) {
    // Null username is invalid
    if (credentials == null) {
      return Future.failedFuture("Credentials must be set for authentication.");
    }

    final UsernamePasswordCredentials authInfo;

    try {
      authInfo = (UsernamePasswordCredentials) credentials;
      authInfo.checkValid(null);
    } catch (RuntimeException e) {
      return Future.failedFuture(e);
    }

    AuthToken token = new AuthToken(authInfo.getUsername(), authInfo.getPassword());

    JsonObject query = createQuery(authInfo.getUsername());
    return mongoClient
      .find(options.getCollectionName(), query)
      .compose(rows -> {
        try {
          User user = handleSelection(rows, token);
          return Future.succeededFuture(user);
        } catch (Exception e) {
          return Future.failedFuture(e);
        }
      });
  }

  /**
   * The default implementation uses the usernameField as search field
   */
  protected JsonObject createQuery(String username) {
    return new JsonObject().put(options.getUsernameField(), username);
  }

  /**
   * Examine the selection of found users and return one, if password is fitting,
   */
  private User handleSelection(List<JsonObject> resultList, AuthToken authToken) throws Exception {
    switch (resultList.size()) {
      case 0: {
        String message = "No account found for user [" + authToken.username + "]";
        throw new Exception(message);
      }
      case 1: {
        JsonObject json = resultList.get(0);
        User user = createUser(json);
        if (strategy.verify(json.getString(options.getPasswordField()), authToken.password))
          return user;
        else {
          String message = "Invalid username/password [" + authToken.username + "]";
          throw new Exception(message);
        }
      }
      default: {
        // More than one row returned!
        String message = "More than one user row found for user [" + authToken.username + "( " + resultList.size() + " )]. Usernames must be unique.";
        throw new Exception(message);
      }
    }
  }

  private User createUser(JsonObject json) {
    User user = User.create(json);
    // metadata "amr"
    user.principal().put("amr", Collections.singletonList("pwd"));

    return user;
  }

  @Override
  public String hash(String id, Map<String, String> params, String salt, String password) {
    return strategy.hash(id, params, salt, password);
  }


  /**
   * The incoming data from an authentication request
   *
   * @author mremme
   */
  static class AuthToken {
    final String username;
    final String password;

    AuthToken(String username, String password) {
      this.username = username;
      this.password = password;
    }
  }
}
