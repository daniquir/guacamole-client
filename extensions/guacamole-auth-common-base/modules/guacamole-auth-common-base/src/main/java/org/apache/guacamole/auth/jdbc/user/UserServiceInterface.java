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

package org.apache.guacamole.auth.jdbc.user;

import java.util.Collection;
import java.util.List;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.auth.jdbc.base.ActivityRecordSearchTerm;
import org.apache.guacamole.auth.jdbc.base.ActivityRecordSortPredicate;
import org.apache.guacamole.net.auth.ActivityRecord;
import org.apache.guacamole.net.auth.AuthenticatedUser;
import org.apache.guacamole.net.auth.AuthenticationProvider;
import org.apache.guacamole.net.auth.Credentials;

/**
 * Service which provides convenience methods for creating, retrieving, and
 * manipulating users.
 */
public interface UserServiceInterface {

	public ModeledAuthenticatedUser retrieveAuthenticatedUser(
            AuthenticationProvider authenticationProvider,
            Credentials credentials) throws GuacamoleException;

	public ModeledUserAbstract retrieveUser(
            AuthenticationProvider authenticationProvider,
            AuthenticatedUser authenticatedUser) throws GuacamoleException;

	public void resetExpiredPassword(ModeledUserAbstract user, Credentials credentials)
            throws GuacamoleException;

	public List<ActivityRecord> retrieveHistory(
            ModeledAuthenticatedUser authenticatedUser, ModeledUserAbstract user)
            throws GuacamoleException;

	public List<ActivityRecord> retrieveHistory(ModeledAuthenticatedUser user,
            Collection<ActivityRecordSearchTerm> requiredContents,
            List<ActivityRecordSortPredicate> sortPredicates, int limit)
            throws GuacamoleException;

}
