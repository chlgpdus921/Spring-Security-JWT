package com.example.securityspring.repository;

import com.example.securityspring.domain.Authority;
import com.example.securityspring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
