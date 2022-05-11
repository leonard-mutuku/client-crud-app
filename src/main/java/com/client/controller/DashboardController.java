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
import com.client.utils.AbstractUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author leonard
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController extends AbstractUtils{
    
    @Autowired
    private SessionController sessionController;
    
    @GetMapping("/{date_from}/{date_to}")
    public ResponseEntity fetchDashboardData(@PathVariable("date_from") String date_from, @PathVariable("date_to") String date_to) {
        sessionController.getLoggedInUser(false);
        Dashboard data = new Dashboard();
        
        data.setBulkStatus(getBulkStatus());
        data.setUsageTrend(getUsageTrend(date_from, date_to));
        data.setCreditSummary(getCreditSummary());
        
        return ResponseEntity.status(200).body(data);
    }
    
    private List<BulkStatus> getBulkStatus() {
        List<BulkStatus> bulkStatuses = new ArrayList<>();
        
        bulkStatuses.add(new BulkStatus(10, "pending", randomizeData(), null, null));
        bulkStatuses.add(new BulkStatus(20, "delivered to phone", randomizeData(), null, null));
        bulkStatuses.add(new BulkStatus(30, "DLR pending", randomizeData(), null, null));
        
        return bulkStatuses;
    }
    
    private List<UsageTrend> getUsageTrend(String date_from, String date_to) {
        List<UsageTrend> usageTrends = new ArrayList<>();
        
        List<String> dates = getDates(date_from, date_to);
        
        dates.forEach((date) -> {
            usageTrends.add(new UsageTrend(date, randomizeData(), randomizeData(), randomizeData(), randomizeData()));
        });
        
        return usageTrends;
    }
    
    private CreditSummary getCreditSummary() {
        CreditSummary creditSummary = new CreditSummary(randomizeData(), randomizeData(), "12th Dec 2022");
        
        return creditSummary;
    }
    
    private Integer randomizeData() {
        int min = 100;
        int max = 5000;
        int number = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return number;
    }
}
