package com.toy.badminton.domain.model.matchingRoom;

import com.toy.badminton.domain.model.BaseTimeEntity;
import com.toy.badminton.domain.model.matchingInfo.MatchingInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingInfo> matchingInfos = new ArrayList<>();

    private MatchingRoom(String name) {
        this.name = name;
    }


    public static MatchingRoom createMatchingRoom(String name) {
        return new MatchingRoom(name);
    }
}
