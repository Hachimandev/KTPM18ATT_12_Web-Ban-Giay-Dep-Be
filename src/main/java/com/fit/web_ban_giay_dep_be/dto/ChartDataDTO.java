package com.fit.web_ban_giay_dep_be.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChartDataDTO {
    private List<String> labels;
    private List<Number> data;
}