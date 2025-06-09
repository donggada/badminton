package com.toy.badminton.domain.model.match.matchingInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingInfoRepository extends JpaRepository<MatchingInfo, Long> {

}
