package com.javadeveloperzone.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMedia is a Querydsl query type for Media
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMedia extends EntityPathBase<Media> {

    private static final long serialVersionUID = -536982496L;

    public static final QMedia media = new QMedia("media");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath delYn = createString("delYn");

    public final NumberPath<Long> mediaId = createNumber("mediaId", Long.class);

    public final NumberPath<Integer> mediaOrder = createNumber("mediaOrder", Integer.class);

    public final StringPath mediaPath = createString("mediaPath");

    public final NumberPath<Long> mediaSize = createNumber("mediaSize", Long.class);

    public final StringPath originalFileName = createString("originalFileName");

    public final NumberPath<Long> refSeq = createNumber("refSeq", Long.class);

    public final StringPath refType = createString("refType");

    public final StringPath registerId = createString("registerId");

    public final DateTimePath<java.time.LocalDateTime> updatedDate = createDateTime("updatedDate", java.time.LocalDateTime.class);

    public final StringPath updateId = createString("updateId");

    public QMedia(String variable) {
        super(Media.class, forVariable(variable));
    }

    public QMedia(Path<? extends Media> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMedia(PathMetadata metadata) {
        super(Media.class, metadata);
    }

}

