/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.controller;

import com.client.model.BulkStatus;
import com.client.model.CreditSummary;
import com.client.model.Dashboard;
import com.client.model.UsageTrend;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author leonard
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    
    @Autowired
    private SessionController sessionController;
    
    @GetMapping("/{date_from}/{date_to}")
    public ResponseEntity fetchDashboardData() {
        sessionController.getLoggedInUser(false);
        Dashboard data = new Dashboard();
        
        data.setBulkStatus(getBulkStatus());
        data.setUsageTrend(getUsageTrend());
        data.setCreditSummary(getCreditSummary());
        
        return ResponseEntity.status(200).body(data);
    }
    
    private List<BulkStatus> getBulkStatus() {
        List<BulkStatus> bulkStatuses = new ArrayList<>();
        
        bulkStatuses.add(new BulkStatus(10, "pending", 120, null, null));
        bulkStatuses.add(new BulkStatus(20, "delivered to phone", 52, null, null));
        bulkStatuses.add(new BulkStatus(30, "DLR pending", 7, null, null));
        
        return bulkStatuses;
    }
    
    private List<UsageTrend> getUsageTrend() {
        List<UsageTrend> usageTrends = new ArrayList<>();
        
        usageTrends.add(new UsageTrend("13th Nov", 300, 50, 1050, 1200));
        usageTrends.add(new UsageTrend("27th Nov", 100, 70, 4020, 700));
        usageTrends.add(new UsageTrend("22th Nov", 13, 150, 800, 1050));
        usageTrends.add(new UsageTrend("88th Nov", 73, 45, 260, 450));
        
        return usageTrends;
    }
    
    private CreditSummary getCreditSummary() {
        CreditSummary creditSummary = new CreditSummary(420, 1250, "12th Dec 2022");
        
        return creditSummary;
    }
}
