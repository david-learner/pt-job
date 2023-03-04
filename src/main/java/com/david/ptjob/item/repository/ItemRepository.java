package com.david.ptjob.item.repository;

import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findItemByCategoryAndName(Category category, String name);
}
