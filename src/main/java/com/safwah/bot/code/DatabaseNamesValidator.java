package com.safwah.bot.code;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.safwah.Student;
import com.safwah.database.code.CodeDataBase;
import com.safwah.study.year.university.StudyYear1444;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.safwah.bot.code.corrector.CodeUtilities.getFormattedFullName;
import static com.safwah.bot.result._1444.ResultFinder1444.getTotalScore;

public class DatabaseNamesValidator {
    private static String sndPath = "D:\\13-Projects\\SafwaResults_Bot\\src\\main\\resources\\names\\snd.txt";
    private static String trdPath = "D:\\13-Projects\\SafwaResults_Bot\\src\\main\\resources\\names\\trd.txt";
    private static String fthPath = "D:\\13-Projects\\SafwaResults_Bot\\src\\main\\resources\\names\\fth.txt";

    public static void main(String[] args) {
        var connection = CodeDataBase.sndYearConnection;
        var studyYear = StudyYear1444.SND_YEAR;
        String path = sndPath;
        // beauty gson
        deleteDuplicatedStudents(connection, studyYear, path);
    }

    private static void deleteDuplicatedStudents(Connection connection, StudyYear1444 studyYear, String pathName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        var students = getStudents(studyYear, connection);
        var studentsByEmail = groupStudentsByEmail(students);
        var studentsByName = groupStudentsByName(students);

        //delete all items of size 1 from studentsByName and studentsByEmail
        studentsByName.values().removeIf(list -> list.size() == 1);
        studentsByEmail.values().removeIf(list -> list.size() == 1);

        dropDuplicates(studentsByName, connection, studyYear);
        dropDuplicates(studentsByEmail, connection, studyYear);

        Path path = Paths.get(pathName);
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            String studentsByEmailJson = gson.toJson(studentsByEmail);
            String studentsByNameJson = gson.toJson(studentsByName);
            writer.write(studentsByEmailJson);
            writer.append(studentsByNameJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dropDuplicates(Map<String, List<Student>> students, Connection con, StudyYear1444 studyYear1444) {
        //drop duplicates from database where student.totalScore = 0
        for (var entry : students.entrySet()) {
            var duplicatedStudentsList = entry.getValue();
            //find the student with max totalScore
            var maxTotalScore = duplicatedStudentsList.stream()
                    .mapToInt(s -> getTotalScore(studyYear1444, s.getCode()))
                    .max().getAsInt();

            var studentWithMaxScore = duplicatedStudentsList.stream()
                    .filter(s -> getTotalScore(studyYear1444, s.getCode()) == maxTotalScore)
                    .findFirst().get();

            duplicatedStudentsList.remove(studentWithMaxScore);

            if (!duplicatedStudentsList.isEmpty()) {
                for (var student : duplicatedStudentsList) {
                    deleteStudent(student, con);
                }
            }
        }
    }

    private static void deleteStudent(Student student, Connection con) {
        String deleteQuery = "DELETE FROM users WHERE code = '" + student.getCode() + "'";
        //execute delete query
        try (var statement = con.createStatement()) {
            statement.execute(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    static Map<String, List<Student>> groupStudentsByEmail(List<Student> students) {
        Map<String, List<Student>> studentsByEmail = new HashMap<>();
        for (Student student : students) {
            if (studentsByEmail.containsKey(student.getEmail())) {
                studentsByEmail.get(student.getEmail()).add(student);
            } else {
                List<Student> studentList = new ArrayList<>();
                studentList.add(student);
                studentsByEmail.put(student.getEmail(), studentList);
            }
        }
        return studentsByEmail;
    }

    static Map<String, List<Student>> groupStudentsByName(List<Student> students) {
        Map<String, List<Student>> studentsByName = new HashMap<>();
        for (Student student : students) {
            String name = student.getName();
            if (studentsByName.containsKey(student.getName())) {
                studentsByName.get(student.getName()).add(student);
            } else {
                List<Student> studentList = new ArrayList<>();
                studentList.add(student);
                studentsByName.put(student.getName(), studentList);
            }
        }
        return studentsByName;
    }

    static List<Student> getStudents(StudyYear1444 studyYear1444, Connection con) {
        List<Student> students = new ArrayList<>();
        String selectQuery = "SELECT * FROM users";

        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                students.add(new Student(resultSet.getString("email"),
                        getFormattedFullName(resultSet.getString("name")),
                        code,
                        getTotalScore(studyYear1444, code)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
