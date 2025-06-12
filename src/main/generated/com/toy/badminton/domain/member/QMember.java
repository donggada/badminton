package com.toy.badminton.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -764478999L;

    public static final QMember member = new QMember("member1");

    public final com.toy.badminton.domain.QBaseTimeEntity _super = new com.toy.badminton.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> createdId = _super.createdId;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final EnumPath<Level> level = createEnum("level", Level.class);

    public final StringPath loginId = createString("loginId");

    public final ListPath<com.toy.badminton.domain.match.MatchingInfo, com.toy.badminton.domain.match.QMatchingInfo> matchingInfos = this.<com.toy.badminton.domain.match.MatchingInfo, com.toy.badminton.domain.match.QMatchingInfo>createList("matchingInfos", com.toy.badminton.domain.match.MatchingInfo.class, com.toy.badminton.domain.match.QMatchingInfo.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImage = createString("profileImage");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    //inherited
    public final NumberPath<Long> updatedId = _super.updatedId;

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

