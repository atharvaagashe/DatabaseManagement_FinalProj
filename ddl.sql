-- Create User table
CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

-- Create Movie table
CREATE TABLE Movie (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    genre VARCHAR(100),
    release_year INT,
    is_tv_show BOOLEAN DEFAULT FALSE
);

-- Create Review table
CREATE TABLE Review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 10),
    comment TEXT,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES Movie(movie_id) ON DELETE CASCADE
);

-- Create Watchlist table
CREATE TABLE Watchlist (
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    saved_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, movie_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES Movie(movie_id) ON DELETE CASCADE
);

-- Create Friendship table (self-relationship for friends)
CREATE TABLE Friendship (
    user_id1 INT NOT NULL,
    user_id2 INT NOT NULL,
    since_date DATE,
    PRIMARY KEY (user_id1, user_id2),
    FOREIGN KEY (user_id1) REFERENCES User(user_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id2) REFERENCES User(user_id) ON DELETE CASCADE,
    CHECK (user_id1 < user_id2) 
);

-- Optional: Comment on Reviews (not required but realistic)
CREATE TABLE Comment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    review_id INT NOT NULL,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (review_id) REFERENCES Review(review_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- Indexes to optimize performance
CREATE INDEX idx_review_user_id ON Review(user_id);
CREATE INDEX idx_review_movie_id ON Review(movie_id);
CREATE INDEX idx_watchlist_user_id ON Watchlist(user_id);
CREATE INDEX idx_watchlist_movie_id ON Watchlist(movie_id);
CREATE INDEX idx_movie_title ON Movie(title);