/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.guacamole.auth.mongodb;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.morphia.InjectedAuthenticationProvider;
import org.apache.guacamole.morphia.JDBCAuthenticationProviderService;

/**
 * Provides a MongoDB based implementation of the AuthenticationProvider
 * functionality.
 */
public class MongoDBAuthenticationProvider
        extends InjectedAuthenticationProvider {

    /**
     * Creates a new MongoDBAuthenticationProvider that reads and writes
     * authentication data to a MongoDB database defined by properties in
     * guacamole.properties.
     *
     * @throws GuacamoleException
     *             If a required property is missing, or an error occurs while
     *             parsing a property.
     */
    public MongoDBAuthenticationProvider() throws GuacamoleException {
        super(new MongoDBInjectorProvider(),
                JDBCAuthenticationProviderService.class);
    }

    @Override
    public String getIdentifier() {
        return "mongodb";
    }

}
