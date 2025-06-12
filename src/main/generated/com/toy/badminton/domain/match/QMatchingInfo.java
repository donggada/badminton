package com.toy.badminton.domain.match;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchingInfo is a Querydsl query type for MatchingInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchingInfo extends EntityPathBase<MatchingInfo> {

    private static final long serialVersionUID = -612916359L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchingInfo matchingInfo = new QMatchingInfo("matchingInfo");

    public final com.toy.badminton.domain.QBaseTimeEntity _super = new com.toy.badminton.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> createdId = _super.createdId;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> leaveDate = createDateTime("leaveDate", java.time.LocalDateTime.class);

    public final QMatchingRoom matchingRoom;

    public final com.toy.badminton.domain.member.QMember member;

    public final EnumPath<MatchingStatus> status = createEnum("status", MatchingStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    //inherited
    public final NumberPath<Long> updatedId = _super.updatedId;

    public QMatchingInfo(String variable) {
        this(MatchingInfo.class, forVariable(variable), INITS);
    }

    public QMatchingInfo(Path<? extends MatchingInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchingInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchingInfo(PathMetadata metadata, PathInits inits) {
        this(MatchingInfo.class, metadata, inits);
    }

    public QMatchingInfo(Class<? extends MatchingInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matchingRoom = inits.isInitialized("matchingRoom") ? new QMatchingRoom(forProperty("matchingRoom")) : null;
        this.member = inits.isInitialized("member") ? new com.toy.badminton.domain.member.QMember(forProperty("member")) : null;
    }

}

