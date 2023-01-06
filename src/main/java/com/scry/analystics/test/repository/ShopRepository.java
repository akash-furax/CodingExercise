package com.scry.analystics.test.repository;

import com.scry.analystics.test.entity.Shop;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {

    String SKIP_LOCKED = "-2";

    public Optional<Shop> findById(String shopName);

    @QueryHints(@QueryHint(name = AvailableSettings.JPA_LOCK_TIMEOUT, value = SKIP_LOCKED))
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Shop save(Shop shop);
}
