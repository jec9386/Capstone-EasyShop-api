package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);

    //get
    Profile getByUserId(int userId);

    //update
    void update(int userId, Profile profile);
}
