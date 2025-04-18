package com.toy.badminton.domain.model.match.matchGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchGroupRepository extends JpaRepository<MatchGroup, Long> {

}
