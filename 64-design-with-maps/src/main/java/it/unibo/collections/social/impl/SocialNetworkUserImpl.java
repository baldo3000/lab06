/**
 * 
 */
package it.unibo.collections.social.impl;

import it.unibo.collections.social.api.SocialNetworkUser;
import it.unibo.collections.social.api.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * This will be an implementation of
 * {@link SocialNetworkUser}:
 * 1) complete the definition of the methods by following the suggestions
 * included in the comments below.
 * 
 * @param <U>
 *            Specific {@link User} type
 */
public final class SocialNetworkUserImpl<U extends User> extends UserImpl implements SocialNetworkUser<U> {

    /*
     *
     * [FIELDS]
     *
     * Define any necessary field
     *
     * In order to save the people followed by a user organized in groups, adopt
     * a generic-type Map: think of what type of keys and values would best suit the
     * requirements
     */
    private final Map<String, Set<U>> followers;

    /*
     * [CONSTRUCTORS]
     *
     * 1) Complete the definition of the constructor below, for building a user
     * participating in a social network, with 4 parameters, initializing:
     *
     * - firstName
     * - lastName
     * - username
     * - age and every other necessary field
     */
    /**
     * Builds a user participating in a social network.
     *
     * @param name
     *                the user firstname
     * @param surname
     *                the user lastname
     * @param userAge
     *                user's age
     * @param user
     *                alias of the user, i.e. the way a user is identified on an
     *                application
     */
    public SocialNetworkUserImpl(final String name, final String surname, final String user, final int userAge) {
        super(name, surname, user, userAge);
        this.followers = new LinkedHashMap<>();
    }

    /*
     * 2) Define a further constructor where the age defaults to -1
     */
    public SocialNetworkUserImpl(final String name, final String surname, final String user) {
        super(name, surname, user);
        this.followers = new LinkedHashMap<>();
    }

    /*
     * [METHODS]
     *
     * Implements the methods below
     */
    @Override
    public boolean addFollowedUser(final String circle, final U user) {
        if (!userIsFriend(user)) {
            if (!groupExists(circle)) {
                addGroup(circle);
            }
            this.followers.get(circle).add(user);
        }
        return false;
    }

    /**
     *
     * [NOTE] If no group with groupName exists yet, this implementation must
     * return an empty Collection.
     */
    @Override
    public Collection<U> getFollowedUsersInGroup(final String groupName) {
        if (this.groupExists(groupName)) {
            return new LinkedHashSet<>(this.followers.get(groupName));
        }
        return Collections.emptySet();
    }

    private final boolean groupExists(final String groupName) {
        return this.followers.containsKey(groupName);
    }

    private final boolean userIsFriend(final U user) {
        for (var group : this.followers.values()) {
            for (var o : group) {
                if (user.equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final void addGroup(final String groupName) {
        this.followers.put(groupName, new LinkedHashSet<>());
    }

    @Override
    public List<U> getFollowedUsers() {
        List<U> totalFollowers = new ArrayList<>();
        for (var group : this.followers.values()) {
            totalFollowers.addAll(group);
        }
        return totalFollowers;
    }
}
