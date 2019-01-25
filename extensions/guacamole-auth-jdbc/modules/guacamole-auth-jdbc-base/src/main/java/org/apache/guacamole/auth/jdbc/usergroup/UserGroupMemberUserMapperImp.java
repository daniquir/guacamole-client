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

package org.apache.guacamole.auth.jdbc.usergroup;

import org.apache.guacamole.auth.common.base.ObjectRelationMapperInterface;
import org.apache.guacamole.auth.common.usergroup.UserGroupModelInterface;
import org.apache.guacamole.auth.jdbc.base.ObjectRelationMapperImp;

import com.google.inject.Inject;

/**
 * Mapper for the one-to-many relationship between a user group and its user
 * members.
 */
public class UserGroupMemberUserMapperImp extends ObjectRelationMapperImp<UserGroupModelInterface, UserGroupMemberUserMapper> implements ObjectRelationMapperInterface<UserGroupModelInterface> {
	
	@Inject 
	private UserGroupMemberUserMapper userGroupMemberUserMapper;
	
	@Override
	protected UserGroupMemberUserMapper getMapper() {
		return userGroupMemberUserMapper;
	}
	
}
