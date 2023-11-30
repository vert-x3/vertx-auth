/*
 * Copyright 2015 Red Hat, Inc.
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
package io.vertx.ext.auth.mongo;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.JsonGen;
import io.vertx.core.json.JsonObject;

/**
 * Options configuring Mongo authentication.
 *
 * @author francoisprunier
 *
 */
@DataObject
@JsonGen(publicConverter = false)
public class MongoAuthenticationOptions {

  private String collectionName;
  private String usernameField;
  private String passwordField;
  private String usernameCredentialField;
  private String passwordCredentialField;

  public MongoAuthenticationOptions() {
    collectionName = MongoAuthentication.DEFAULT_COLLECTION_NAME;
    usernameField = MongoAuthentication.DEFAULT_USERNAME_FIELD;
    passwordField = MongoAuthentication.DEFAULT_PASSWORD_FIELD;
    usernameCredentialField = MongoAuthentication.DEFAULT_CREDENTIAL_USERNAME_FIELD;
    passwordCredentialField = MongoAuthentication.DEFAULT_CREDENTIAL_PASSWORD_FIELD;
  }

  public MongoAuthenticationOptions(JsonObject json) {
    this();
    MongoAuthenticationOptionsConverter.fromJson(json, this);
  }

  public String getCollectionName() {
    return collectionName;
  }

  /**
   * The property name to be used to set the name of the collection inside the config.
   *
   * @param collectionName the collection name
   * @return a reference to this, so the API can be used fluently
   */
  public MongoAuthenticationOptions setCollectionName(String collectionName) {
    this.collectionName = collectionName;
    return this;
  }

  public String getUsernameField() {
    return usernameField;
  }

  /**
   * The property name to be used to set the name of the field, where the username is stored inside.
   *
   * @param usernameField the username field
   * @return a reference to this, so the API can be used fluently
   */
  public MongoAuthenticationOptions setUsernameField(String usernameField) {
    this.usernameField = usernameField;
    return this;
  }

  public String getPasswordField() {
    return passwordField;
  }

  /**
   * The property name to be used to set the name of the field, where the password is stored inside
   *
   * @param passwordField the password field
   * @return a reference to this, so the API can be used fluently
   */
  public MongoAuthenticationOptions setPasswordField(String passwordField) {
    this.passwordField = passwordField;
    return this;
  }

  public String getUsernameCredentialField() {
    return usernameCredentialField;
  }

  /**
   * The property name to be used to set the name of the field, where the username for the credentials is stored inside.
   *
   * @param usernameCredentialField the username credential field
   * @return a reference to this, so the API can be used fluently
   */
  public MongoAuthenticationOptions setUsernameCredentialField(String usernameCredentialField) {
    this.usernameCredentialField = usernameCredentialField;
    return this;
  }

  public String getPasswordCredentialField() {
    return passwordCredentialField;
  }

  public MongoAuthenticationOptions setPasswordCredentialField(String passwordCredentialField) {
    this.passwordCredentialField = passwordCredentialField;
    return this;
  }
}
