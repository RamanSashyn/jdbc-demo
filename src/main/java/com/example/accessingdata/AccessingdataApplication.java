package com.example.accessingdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@SpringBootApplication
public class AccessingdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccessingdataApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(JdbcTemplate jdbcTemplate) {
        return args -> {
            // Создаем таблицу Students, если она еще не существует
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS Students (" +
                    "Id SERIAL PRIMARY KEY, FirstName VARCHAR(255), LastName VARCHAR(255), StudentId VARCHAR(255))");

            // Добавляем две записи в таблицу
            jdbcTemplate.update("INSERT INTO Students (FirstName, LastName, StudentId) VALUES (?, ?, ?)",
                    "Raman", "Sashyn", "62248");
            jdbcTemplate.update("INSERT INTO Students (FirstName, LastName, StudentId) VALUES (?, ?, ?)",
                    "John", "Doe", "62249");

            // Запрос по FirstName для обеих записей
            String firstName = "Raman"; // Вы можете изменять это имя для поиска других записей
            List<Student> students = jdbcTemplate.query(
                    "SELECT * FROM Students WHERE FirstName = ?",
                    new Object[]{firstName},
                    new StudentRowMapper());

            // Логируем результаты в консоль
            System.out.println("Students found with FirstName '" + firstName + "':");
            for (Student student : students) {
                System.out.println(student);
            }
        };
    }

    // Маппер строк для Student
    public static class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            Student student = new Student();
            student.setId(rs.getLong("Id"));
            student.setFirstName(rs.getString("FirstName"));
            student.setLastName(rs.getString("LastName"));
            student.setStudentId(rs.getString("StudentId"));
            return student;
        }
    }

    // Класс для представления студента
    public static class Student {
        private Long id;
        private String firstName;
        private String lastName;
        private String studentId;

        // Геттеры и сеттеры
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        @Override
        public String toString() {
            return String.format("Student{id=%d, firstName='%s', lastName='%s', studentId='%s'}",
                    id, firstName, lastName, studentId);
        }
    }
}
