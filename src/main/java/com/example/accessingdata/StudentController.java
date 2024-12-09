package com.example.accessingdata;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    private final JdbcTemplate jdbcTemplate;

    public StudentController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/students")
    public List<Map<String, Object>> getStudentsByFirstName(@RequestParam String firstName) {
        return jdbcTemplate.queryForList(
                "SELECT * FROM Students WHERE FirstName = ?",
                firstName
        );
    }
}
