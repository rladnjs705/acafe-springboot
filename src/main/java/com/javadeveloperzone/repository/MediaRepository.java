package com.javadeveloperzone.repository;

import com.javadeveloperzone.entity.Media;
import com.javadeveloperzone.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

}
