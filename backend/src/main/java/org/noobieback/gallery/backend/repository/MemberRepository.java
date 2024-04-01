package org.noobieback.gallery.backend.repository;

import org.noobieback.gallery.backend.entity.Item;
import org.noobieback.gallery.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByEmailAndPassword(String email, String password);
}
