package com.airline.management.service;

import com.airline.management.entity.User;
import com.airline.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService") // <-- must match the name used in @PreAuthorize
@RequiredArgsConstructor
@Transactional
public class SecurityService {

    private final UserRepository userRepository;

    public boolean isSelf(Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) return false;

        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getId().equals(userId);
    }
}
