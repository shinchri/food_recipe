package com.example.shinc.final_project_2018;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MyDao {

    // this is used to insert user
    @Insert
    void addUser(User user);

    // getting user
    @Query("SELECT * from users")
    List<User> getUsers();

    // getting user with a given name
    @Query("SELECT * from users WHERE user_name IS :search")
    List<User> getUsersWithName(String search);

    @Delete
    int deleteUser(User user);

    @Update
    void updateUser(User user);

}