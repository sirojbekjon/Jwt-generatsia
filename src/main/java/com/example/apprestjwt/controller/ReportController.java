package com.example.apprestjwt.controller;

import com.example.apprestjwt.payload.LoginDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @GetMapping
    public HttpEntity<?> getReports(){
        return ResponseEntity.ok("Reports send");
    }

    @PostMapping("/test")
    public HttpEntity<?> addTest(@RequestBody LoginDto loginDto){
        System.out.println(loginDto);
        return ResponseEntity.ok(loginDto);
    }
}
