package com.watchlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watchlist.repository.FriendshipRepository;

import java.util.List;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    public boolean addFriend(int userId, int friendId) {
        // Don't allow users to add themselves as friends
        if (userId == friendId) {
            return false;
        }
        return friendshipRepository.addFriendship(userId, friendId);
    }

    public boolean removeFriend(int userId, int friendId) {
        return friendshipRepository.removeFriendship(userId, friendId);
    }

    public boolean areFriends(int userId, int friendId) {
        return friendshipRepository.isFriendship(userId, friendId);
    }

    public List<Integer> getUserFriends(int userId) {
        return friendshipRepository.getFriendIds(userId);
    }
}
