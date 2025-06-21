package com.jaspersoft.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MonitoringController {

    @GetMapping("/monitoring")
    public String showMonitoringDashboard() {
        return "monitoring"; // Points to src/main/resources/templates/monitoring.html
    }
}
