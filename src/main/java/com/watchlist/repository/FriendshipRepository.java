package com.watchlist.repository;

import com.watchlist.models.Friendship;
import java.util.List;

public interface FriendshipRepository {
    boolean addFriendship(int userId, int friendId);
    boolean removeFriendship(int userId, int friendId);
    boolean isFriendship(int userId, int friendId);
    List<Integer> getFriendIds(int userId);
} 