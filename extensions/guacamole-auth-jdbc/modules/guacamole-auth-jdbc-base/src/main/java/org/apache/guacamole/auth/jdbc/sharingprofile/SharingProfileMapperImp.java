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

package org.apache.guacamole.auth.jdbc.sharingprofile;

import org.apache.guacamole.auth.common.sharingprofile.SharingProfileMapperInterface;
import org.apache.guacamole.auth.jdbc.base.ModeledDirectoryObjectMapperImp;

import com.google.inject.Inject;

/**
 * Mapper for sharing profile objects.
 */
public class SharingProfileMapperImp
		extends ModeledDirectoryObjectMapperImp<SharingProfileModel, SharingProfileMapper>
        implements SharingProfileMapperInterface {

	@Inject 
	private SharingProfileMapper sharingProfileMapper;
	
    /**
     * Selects the sharing profile associated with the given primary connection
     * and having the given name. If no such sharing profile exists, null is
     * returned.
     *
     * @param parentIdentifier
     *     The identifier of the primary connection to search against.
     *
     * @param name
     *     The name of the sharing profile to find.
     *
     * @return
     *     The sharing profile having the given name and associated with the
     *     given primary connection, or null if no such sharing profile exists.
     */
    public SharingProfileModel selectOneByName(String parentIdentifier, String name) {
    	return sharingProfileMapper.selectOneByName(parentIdentifier, name);
    }

	@Override
	protected SharingProfileMapper getMapper() {
		return sharingProfileMapper;
	}
    
}
