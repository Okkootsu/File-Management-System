package org.example.managers;

public abstract class BaseUser {

    public abstract String getUsername();
    public abstract String getPassword();
    public abstract void setUsername(String username);
    public abstract void setPassword(String password);
    public abstract int getId();
    public abstract void delThisUser();
}
