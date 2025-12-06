package com.fit.web_ban_giay_dep_be.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalStatsDTO {
    private long totalRevenue;
    private long totalOrders;
    private long totalStock;
    private long totalCustomers;
}