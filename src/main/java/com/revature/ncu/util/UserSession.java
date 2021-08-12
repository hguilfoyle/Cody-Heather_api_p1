package com.revature.ncu.util;

import com.revature.ncu.documents.AppUser;

// Container to hold onto the current user

public class UserSession {

    private AppUser currentUser;

    // Checks that the user's session is still valid.
    public boolean isActive() {
        return currentUser != null;
    }

    // Clear out user's data when they log off
    public void closeSession() {
        setCurrentUser(null);
    }

    // Getters and setters
    public AppUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AppUser currentUser) {
        this.currentUser = currentUser;
    }

}
