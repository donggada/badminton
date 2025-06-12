package com.toy.badminton.domain.match;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchGroup is a Querydsl query type for MatchGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchGroup extends EntityPathBase<MatchGroup> {

    private static final long serialVersionUID = -688891800L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchGroup matchGroup = new QMatchGroup("matchGroup");

    public final com.toy.badminton.domain.QBaseTimeEntity _super = new com.toy.badminton.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> createdId = _super.createdId;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isGameOver = createBoolean("isGameOver");

    public final QMatchingRoom matchingRoom;

    public final ListPath<com.toy.badminton.domain.member.Member, com.toy.badminton.domain.member.QMember> members = this.<com.toy.badminton.domain.member.Member, com.toy.badminton.domain.member.QMember>createList("members", com.toy.badminton.domain.member.Member.class, com.toy.badminton.domain.member.QMember.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    //inherited
    public final NumberPath<Long> updatedId = _super.updatedId;

    public QMatchGroup(String variable) {
        this(MatchGroup.class, forVariable(variable), INITS);
    }

    public QMatchGroup(Path<? extends MatchGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchGroup(PathMetadata metadata, PathInits inits) {
        this(MatchGroup.class, metadata, inits);
    }

    public QMatchGroup(Class<? extends MatchGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matchingRoom = inits.isInitialized("matchingRoom") ? new QMatchingRoom(forProperty("matchingRoom")) : null;
    }

}

