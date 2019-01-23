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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.guacamole.auth.common.base.ActivityRecordModelInterface;
import org.apache.guacamole.auth.common.base.ActivityRecordSearchTerm;
import org.apache.guacamole.auth.common.base.ActivityRecordSortPredicate;
import org.apache.guacamole.auth.common.user.UserModelInterface;
import org.apache.guacamole.auth.common.user.UserRecordMapperInterface;
import org.apache.guacamole.auth.jdbc.base.ActivityRecordModel;

import com.google.inject.Inject;

/**
 * Mapper for user login activity records.
 */
public class UserRecordMapperImp implements UserRecordMapperInterface {

	@Inject 
	private UserRecordMapper userRecordMapper;
	
    /**
     * Returns a collection of all user login records associated with the user
     * having the given username.
     *
     * @param username
     *     The username of the user whose login records are to be retrieved.
     *
     * @return
     *     A collection of all user login records associated with the user
     *     having the given username. This collection will be empty if no such
     *     user exists.
     */
    public List<ActivityRecordModelInterface> select(String username) {
    	return userRecordMapper.select(username);
    }

    /**
     * Inserts the given user login record.
     *
     * @param record
     *     The user login record to insert.
     *
     * @return
     *     The number of rows inserted.
     */
    public int insert(ActivityRecordModelInterface record) {
    	return userRecordMapper.insert((ActivityRecordModel) record);
    }

    /**
     * Updates the given user login record.
     *
     * @param record
     *     The user login record to update.
     *
     * @return
     *     The number of rows updated.
     */
    public int update(ActivityRecordModelInterface record) {
    	return userRecordMapper.update((ActivityRecordModel) record);
    }

    /**
     * Searches for up to <code>limit</code> user login records that contain
     * the given terms, sorted by the given predicates, regardless of whether
     * the data they are associated with is is readable by any particular user.
     * This should only be called on behalf of a system administrator. If
     * records are needed by a non-administrative user who must have explicit
     * read rights, use searchReadable() instead.
     *
     * @param terms
     *     The search terms that must match the returned records.
     *
     * @param sortPredicates
     *     A list of predicates to sort the returned records by, in order of
     *     priority.
     *
     * @param limit
     *     The maximum number of records that should be returned.
     *
     * @return
     *     The results of the search performed with the given parameters.
     */
    public List<ActivityRecordModelInterface> search(Collection<ActivityRecordSearchTerm> terms,
            List<ActivityRecordSortPredicate> sortPredicates,
            int limit) {
    	return userRecordMapper.search(terms, sortPredicates, limit);
    }

    /**
     * Searches for up to <code>limit</code> user login records that contain
     * the given terms, sorted by the given predicates. Only records that are
     * associated with data explicitly readable by the given user will be
     * returned. If records are needed by a system administrator (who, by
     * definition, does not need explicit read rights), use search() instead.
     *
     * @param user
     *    The user whose permissions should determine whether a record is
     *    returned.
     *
     * @param terms
     *     The search terms that must match the returned records.
     *
     * @param sortPredicates
     *     A list of predicates to sort the returned records by, in order of
     *     priority.
     *
     * @param limit
     *     The maximum number of records that should be returned.
     *
     * @param effectiveGroups
     *     The identifiers of all groups that should be taken into account
     *     when determining the permissions effectively granted to the user. If
     *     no groups are given, only permissions directly granted to the user
     *     will be used.
     *
     * @return
     *     The results of the search performed with the given parameters.
     */
    public List<ActivityRecordModelInterface> searchReadable(UserModelInterface user,
            Collection<ActivityRecordSearchTerm> terms,
            List<ActivityRecordSortPredicate> sortPredicates,
            int limit,
            Collection<String> effectiveGroups) {
    	return new ArrayList<ActivityRecordModelInterface>(userRecordMapper.searchReadable((UserModel) user, terms, sortPredicates, limit, effectiveGroups));
    }

}
