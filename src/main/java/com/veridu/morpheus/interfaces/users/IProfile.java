package com.veridu.morpheus.interfaces.users;

import java.io.Serializable;

public interface IProfile extends Serializable {

    /**
     * Get the provider
     *
     * @return
     */
    public String getProvider();

    /**
     * Get a profile Id, which is a string.
     *
     * @return
     */
    public String getProfileId();

}