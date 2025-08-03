package com.example.user.infrastructure;

import com.example.user.domain.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final UserRowMapper userRowMapper = new UserRowMapper();

    @Override
    public User save(User user) {
        if (user.getUserId() == null) {
            user = new User(
                UUID.randomUUID().toString(),
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getBio(),
                user.getFollowers(),
                user.getFollowing()
            );
        }

        String sql = """
            INSERT INTO users (user_id, username, email, password_hash, bio, followers, following) 
            VALUES (?, ?, ?, ?, ?, ?, ?) 
            ON CONFLICT (user_id) 
            DO UPDATE SET username = ?, email = ?, password_hash = ?, bio = ?, followers = ?, following = ?
            """;

        jdbcTemplate.update(sql,
            user.getUserId(),
            user.getUsername().getValue(),
            user.getEmail().getValue(),
            user.getPasswordHash(),
            user.getBio(),
            String.join(",", user.getFollowers()),
            String.join(",", user.getFollowing()),
            user.getUsername().getValue(),
            user.getEmail().getValue(),
            user.getPasswordHash(),
            user.getBio(),
            String.join(",", user.getFollowers()),
            String.join(",", user.getFollowing())
        );

        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, userId);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, username);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public void deleteById(String userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public boolean existsById(String userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            String followersStr = rs.getString("followers");
            String followingStr = rs.getString("following");
            
            List<String> followers = followersStr != null && !followersStr.isEmpty() 
                ? List.of(followersStr.split(",")) 
                : List.of();
            
            List<String> following = followingStr != null && !followingStr.isEmpty() 
                ? List.of(followingStr.split(",")) 
                : List.of();

            return new User(
                rs.getString("user_id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("bio"),
                followers,
                following
            );
        }
    }
} 