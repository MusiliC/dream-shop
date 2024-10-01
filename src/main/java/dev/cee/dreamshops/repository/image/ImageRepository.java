package dev.cee.dreamshops.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cee.dreamshops.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
