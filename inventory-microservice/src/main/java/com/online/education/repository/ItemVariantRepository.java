package com.online.education.repository;

import com.online.education.entity.ItemVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemVariantRepository extends JpaRepository<ItemVariant, Long> {
}