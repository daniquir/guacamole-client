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

import org.apache.guacamole.auth.common.user.UserModelInterface;
import org.apache.guacamole.auth.common.user.UserParentUserGroupMapperInterface;
import org.apache.guacamole.auth.jdbc.base.ObjectRelationMapperImp;

import com.google.inject.Inject;

/**
 * Mapper for the one-to-many relationship between a user and the user groups
 * of which it is a member.
 */
public class UserParentUserGroupMapperImp extends ObjectRelationMapperImp<UserModelInterface, UserParentUserGroupMapper> implements UserParentUserGroupMapperInterface<UserModelInterface> {
	
	@Inject 
	private UserParentUserGroupMapper userParentUserGroupMapper;
	
	@Override
	protected UserParentUserGroupMapper getMapper() {
		return userParentUserGroupMapper;
	}
	
}
