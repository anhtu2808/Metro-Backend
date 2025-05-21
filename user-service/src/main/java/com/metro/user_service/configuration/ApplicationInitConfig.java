package com.metro.user_service.configuration;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.metro.user_service.entity.Role;
import com.metro.user_service.entity.User;
import com.metro.user_service.enums.RoleType;
import com.metro.user_service.repository.RoleRepository;
import com.metro.user_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "admin";

    // Mô tả tiếng Anh cho từng RoleType
    private static final Map<RoleType, String> DESCRIPTIONS = new EnumMap<>(RoleType.class);
    static {
        DESCRIPTIONS.put(RoleType.CUSTOMER, "Ticket purchasing customer");
        DESCRIPTIONS.put(RoleType.STAFF,    "Ticket sales staff member");
        DESCRIPTIONS.put(RoleType.MANAGER,  "Ticket sales manager");
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "spring.datasource",
            name   = "driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver"
    )
    public ApplicationRunner initializer(UserRepository userRepo, RoleRepository roleRepo) {
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) {
                log.info("-- Initializing default roles and admin user --");

                // Tạo roles nếu chưa có
                for (RoleType type : RoleType.values()) {
                    if (!roleRepo.existsByName(type)) {
                        Role r = Role.builder()
                                .name(type)
                                .description(DESCRIPTIONS.get(type))
                                .build();
                        roleRepo.save(r);
                        log.info("Created role: {}", type);
                    }
                }

                // Tạo admin user nếu chưa có
                Optional<User> adminOpt = userRepo.findByUsername(ADMIN_USERNAME);
                if (adminOpt.isEmpty()) {
                    // Lấy RoleManager
                    Role managerRole = roleRepo
                            .findByName(RoleType.MANAGER)
                            .orElseThrow(() -> new IllegalStateException("Manager role not found"));

                    User admin = User.builder()
                            .username(ADMIN_USERNAME)
                            .password(passwordEncoder.encode(ADMIN_PASSWORD))
                            .role(managerRole)
                            .build();
                    userRepo.save(admin);
                    log.warn(
                            "Default admin created (username='{}', password='{}'), please change password immediately",
                            ADMIN_USERNAME, ADMIN_PASSWORD
                    );
                }

                log.info("-- Application initialization completed --");
            }
        };
    }
}
