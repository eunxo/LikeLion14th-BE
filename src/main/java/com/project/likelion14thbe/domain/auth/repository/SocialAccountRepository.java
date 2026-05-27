package com.project.likelion14thbe.domain.auth.repository;

import com.project.likelion14thbe.domain.auth.entity.SocialAccount;
import com.project.likelion14thbe.domain.auth.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    Optional<SocialAccount> findByProviderAndProviderId(Provider provider, String providerId);
}
