package com.example.shinc.final_project_2018;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users",
        indices = {@Index(value = {"user_name"}, unique = true)})
public class User {
    @PrimaryKey
    private int id;

    // user_name is checked for uniqueness
    @ColumnInfo(name="user_name")
    private String name;

    @ColumnInfo(name="user_password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
