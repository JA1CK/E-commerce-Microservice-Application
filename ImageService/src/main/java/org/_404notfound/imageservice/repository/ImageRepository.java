package org._404notfound.imageservice.repository;

import org._404notfound.imageservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
