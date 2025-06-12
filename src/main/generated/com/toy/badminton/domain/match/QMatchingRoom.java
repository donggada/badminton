package com.toy.badminton.domain.match;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchingRoom is a Querydsl query type for MatchingRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchingRoom extends EntityPathBase<MatchingRoom> {

    private static final long serialVersionUID = -612647002L;

    public static final QMatchingRoom matchingRoom = new QMatchingRoom("matchingRoom");

    public final com.toy.badminton.domain.QBaseTimeEntity _super = new com.toy.badminton.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> createdId = _super.createdId;

    public final StringPath entryCode = createString("entryCode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isActive = createBoolean("isActive");

    public final SetPath<com.toy.badminton.domain.member.Member, com.toy.badminton.domain.member.QMember> managerList = this.<com.toy.badminton.domain.member.Member, com.toy.badminton.domain.member.QMember>createSet("managerList", com.toy.badminton.domain.member.Member.class, com.toy.badminton.domain.member.QMember.class, PathInits.DIRECT2);

    public final ListPath<MatchGroup, QMatchGroup> matchGroups = this.<MatchGroup, QMatchGroup>createList("matchGroups", MatchGroup.class, QMatchGroup.class, PathInits.DIRECT2);

    public final ListPath<MatchingInfo, QMatchingInfo> matchingInfos = this.<MatchingInfo, QMatchingInfo>createList("matchingInfos", MatchingInfo.class, QMatchingInfo.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    //inherited
    public final NumberPath<Long> updatedId = _super.updatedId;

    public QMatchingRoom(String variable) {
        super(MatchingRoom.class, forVariable(variable));
    }

    public QMatchingRoom(Path<? extends MatchingRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMatchingRoom(PathMetadata metadata) {
        super(MatchingRoom.class, metadata);
    }

}

