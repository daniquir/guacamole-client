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

package org.apache.guacamole.auth.common.base;

import java.util.Collections;
import java.util.Set;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.auth.common.activeconnection.ActiveConnectionPermissionService;
import org.apache.guacamole.auth.common.permission.ConnectionGroupPermissionServiceInterface;
import org.apache.guacamole.auth.common.permission.ConnectionPermissionServiceInterface;
import org.apache.guacamole.auth.common.permission.SharingProfilePermissionServiceInterface;
import org.apache.guacamole.auth.common.permission.SystemPermissionServiceInterface;
import org.apache.guacamole.auth.common.permission.UserGroupPermissionServiceInterface;
import org.apache.guacamole.auth.common.permission.UserPermissionServiceInterface;
import org.apache.guacamole.auth.common.user.ModeledAuthenticatedUser;
import org.apache.guacamole.net.auth.Permissions;
import org.apache.guacamole.net.auth.permission.ObjectPermissionSet;
import org.apache.guacamole.net.auth.permission.SystemPermission;
import org.apache.guacamole.net.auth.permission.SystemPermissionSet;

import com.google.inject.Inject;

/**
 * An implementation of the base Permissions interface which is common to both
 * Users and UserGroups, backed by a database model.
 *
 * @param <ModelType>
 *     The type of model object that corresponds to this object.
 */
public abstract class ModeledPermissions<ModelType extends ObjectModelInterface & EntityModelInterface>
        extends ModeledDirectoryObject<ModelType> implements Permissions {

    /**
     * Service for retrieving entity details.
     */
    @Inject
    private EntityServiceInterface entityService;

    /**
     * Service for retrieving system permissions.
     */
    @Inject
    private SystemPermissionServiceInterface systemPermissionService;

    /**
     * Service for retrieving connection permissions.
     */
    @Inject
    private ConnectionPermissionServiceInterface connectionPermissionService;

    /**
     * Service for retrieving connection group permissions.
     */
    @Inject
    private ConnectionGroupPermissionServiceInterface connectionGroupPermissionService;

    /**
     * Service for retrieving sharing profile permissions.
     */
    @Inject
    private SharingProfilePermissionServiceInterface sharingProfilePermissionService;

    /**
     * Service for retrieving active connection permissions.
     */
    @Inject
    private ActiveConnectionPermissionService activeConnectionPermissionService;

    /**
     * Service for retrieving user permissions.
     */
    @Inject
    private UserPermissionServiceInterface userPermissionService;

    /**
     * Service for retrieving user group permissions.
     */
    @Inject
    private UserGroupPermissionServiceInterface userGroupPermissionService;

    /**
     * Returns whether the underlying entity is a user. Entities may be either
     * users or user groups.
     *
     * @return
     *     true if the underlying entity is a user, false otherwise.
     */
    public boolean isUser() {
        return getModel().getEntityType() == EntityType.USER;
    }

    /**
     * Returns whether the underlying entity represents a specific user having
     * the given username.
     *
     * @param username
     *     The username of a user.
     *
     * @return
     *     true if the underlying entity is a user that has the given username,
     *     false otherwise.
     */
    public boolean isUser(String username) {
        return isUser() && getIdentifier().equals(username);
    }

    /**
     * Returns whether the underlying entity is a user group. Entities may be
     * either users or user groups.
     *
     * @return
     *     true if the underlying entity is a user group, false otherwise.
     */
    public boolean isUserGroup() {
        return getModel().getEntityType() == EntityType.USER_GROUP;
    }

    /**
     * Returns whether this entity is a system administrator, and thus is not
     * restricted by permissions, taking into account permission inheritance
     * via user groups.
     *
     * @return
     *    true if this entity is a system administrator, false otherwise.
     *
     * @throws GuacamoleException
     *    If an error occurs while determining the entity's system administrator
     *    status.
     */
    public boolean isAdministrator() throws GuacamoleException {
        SystemPermissionSet systemPermissionSet = getEffective().getSystemPermissions();
        return systemPermissionSet.hasPermission(SystemPermission.Type.ADMINISTER);
    }

    @Override
    public SystemPermissionSet getSystemPermissions()
            throws GuacamoleException {
        return systemPermissionService.getPermissionSet(getCurrentUser(), this,
                Collections.<String>emptySet());
    }

    @Override
    public ObjectPermissionSet getConnectionPermissions()
            throws GuacamoleException {
        return connectionPermissionService.getPermissionSet(getCurrentUser(),
                this, Collections.<String>emptySet());
    }

    @Override
    public ObjectPermissionSet getConnectionGroupPermissions()
            throws GuacamoleException {
        return connectionGroupPermissionService.getPermissionSet(
                getCurrentUser(), this, Collections.<String>emptySet());
    }

    @Override
    public ObjectPermissionSet getSharingProfilePermissions()
            throws GuacamoleException {
        return sharingProfilePermissionService.getPermissionSet(
                getCurrentUser(), this, Collections.<String>emptySet());
    }

    @Override
    public ObjectPermissionSet getActiveConnectionPermissions()
            throws GuacamoleException {
        return activeConnectionPermissionService.getPermissionSet(
                getCurrentUser(), this, Collections.<String>emptySet());
    }

    @Override
    public ObjectPermissionSet getUserPermissions()
            throws GuacamoleException {
        return userPermissionService.getPermissionSet(getCurrentUser(), this,
                Collections.<String>emptySet());
    }

    @Override
    public ObjectPermissionSet getUserGroupPermissions() throws GuacamoleException {
        return userGroupPermissionService.getPermissionSet(getCurrentUser(),
                this, Collections.<String>emptySet());
    }

    /**
     * Returns the identifiers of all user groups defined within the database
     * which apply to this user, including any groups inherited through
     * membership in yet more groups.
     *
     * @return
     *     The identifiers of all user groups defined within the database which
     *     apply to this user.
     */
    public Set<String> getEffectiveUserGroups() {
        return entityService.retrieveEffectiveGroups(this,
                Collections.<String>emptySet());
    }

    /**
     * Returns a Permissions object which represents all permissions granted to
     * this entity, including any permissions inherited through group
     * membership.
     *
     * @return
     *     A Permissions object which represents all permissions granted to
     *     this entity.
     */
    public Permissions getEffective() {

        final ModeledAuthenticatedUser authenticatedUser = getCurrentUser();
        final Set<String> effectiveGroups;

        // If this user is the currently-authenticated user, include any
        // additional effective groups declared by the authentication system
        if (authenticatedUser.getIdentifier().equals(getIdentifier()))
            effectiveGroups = entityService.retrieveEffectiveGroups(this,
                    authenticatedUser.getEffectiveUserGroups());

        // Otherwise, just include effective groups from the database
        else
            effectiveGroups = getEffectiveUserGroups();

        // Return a permissions object which describes all effective
        // permissions, including any permissions inherited via user groups
        return new Permissions() {

            @Override
            public ObjectPermissionSet getActiveConnectionPermissions()
                    throws GuacamoleException {
                return activeConnectionPermissionService.getPermissionSet(authenticatedUser, ModeledPermissions.this, effectiveGroups);
            }

            @Override
            public ObjectPermissionSet getConnectionGroupPermissions()
                    throws GuacamoleException {
                return connectionGroupPermissionService.getPermissionSet(authenticatedUser, ModeledPermissions.this, effectiveGroups);
            }

            @Override
            public ObjectPermissionSet getConnectionPermissions()
                    throws GuacamoleException {
                return connectionPermissionService.getPermissionSet(authenticatedUser, ModeledPermissions.this, effectiveGroups);
            }

            @Override
            public ObjectPermissionSet getSharingProfilePermissions()
                    throws GuacamoleException {
                return sharingProfilePermissionService.getPermissionSet(authenticatedUser, ModeledPermissions.this, effectiveGroups);
            }

            @Override
            public SystemPermissionSet getSystemPermissions()
                    throws GuacamoleException {
                return systemPermissionService.getPermissionSet(authenticatedUser, ModeledPermissions.this, effectiveGroups);
            }

            @Override
            public ObjectPermissionSet getUserPermissions()
                    throws GuacamoleException {
                return userPermissionService.getPermissionSet(authenticatedUser, ModeledPermissions.this, effectiveGroups);
            }

            @Override
            public ObjectPermissionSet getUserGroupPermissions()
                    throws GuacamoleException {
                return userGroupPermissionService.getPermissionSet(getCurrentUser(), ModeledPermissions.this, effectiveGroups);
            }

        };
    }

}
