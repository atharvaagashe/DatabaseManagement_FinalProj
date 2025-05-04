package com.watchlist.models;

import java.util.Date;

public class Friendship {
    private int userId1;
    private int userId2;
    private Date sinceDate;
    
    public Friendship() {}
    
    public Friendship(int userId1, int userId2, Date sinceDate) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.sinceDate = sinceDate;
    }

    public int getUserId1() {
        return userId1;
    }

    public void setUserId1(int userId1) {
        this.userId1 = userId1;
    }

    public int getUserId2() {
        return userId2;
    }

    public void setUserId2(int userId2) {
        this.userId2 = userId2;
    }

    public Date getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(Date sinceDate) {
        this.sinceDate = sinceDate;
    }
}
