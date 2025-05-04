package com.watchlist.repository;

import com.watchlist.models.User;
import java.util.List;

public interface UserRepository {
    User findByUsername(String username);
    List<User> findAllUsers();
}