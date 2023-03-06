package com.javadeveloperzone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long mediaId;

    @Column(name = "ref_type", length = 3, nullable = false)
    private String refType;

    @Column(name = "ref_seq", nullable = false)
    private Long refSeq;

    @Column(name = "media_order", nullable = false)
    private Integer mediaOrder;

    @Column(name = "original_file_name", length = 256)
    private String originalFileName;

    @Column(name = "media_size", nullable = false)
    private Long mediaSize;

    @Column(name = "media_path", length = 1000, nullable = false)
    private String mediaPath;

    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn;

    @Column(name = "register_id", length = 50, nullable = false)
    private String registerId;

    @CreationTimestamp // INSERT 쿼리 시 현재 시간으로 생성
    private LocalDateTime createDate= LocalDateTime.now();

    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedDate = LocalDateTime.now();

    @Column(name = "update_id", length = 50, nullable = false)
    private String updateId;
}
