package org._404notfound.adminservice.repository;

import org._404notfound.adminservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
