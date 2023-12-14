package com.runningtracker.data.runs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RunsRepository extends JpaRepository<RunsEntity, Long> {

    List<RunsEntity> findByUserId(Long userId);

    List<RunsEntity> findByUserIdAndStartDatetimeBetweenAndFinishDatetimeBetween(
            Long userId,
            LocalDateTime startDatetimeStart, LocalDateTime startDatetimeEnd,
            LocalDateTime finishDatetimeStart, LocalDateTime finishDatetimeEnd
    );
}
