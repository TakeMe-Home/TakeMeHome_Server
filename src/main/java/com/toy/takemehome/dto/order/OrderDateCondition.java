package com.toy.takemehome.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderDateCondition {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public OrderDateCondition(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
