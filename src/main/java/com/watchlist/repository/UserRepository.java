package com.watchlist.repository;

import com.watchlist.models.User;

public interface UserRepository {
    User findByUsername(String username);
}