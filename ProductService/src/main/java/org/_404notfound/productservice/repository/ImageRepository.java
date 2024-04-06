package org._404notfound.productservice.repository;

import org._404notfound.productservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
