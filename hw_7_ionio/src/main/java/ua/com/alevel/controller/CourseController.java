package ua.com.alevel.controller;

import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.impl.CourseServiceImpl;
import ua.com.alevel.service.impl.StudentServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CourseController {

    CourseServiceImpl courseService = new CourseServiceImpl();
    StudentServiceImpl studentService = new StudentServiceImpl();

    public void run() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String choice;
        try {
            printMenu();
            while ((choice = input.readLine()) != null) {
                if (choice.equals("0")) {
                    return;
                }
                options(choice, input);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\n\tСущность \"Курс\"");
        System.out.println("1 - создать курс");
        System.out.println("2 - найти всех");
        System.out.println("3 - найти по идентификатору");
        System.out.println("4 - удалить всех");
        System.out.println("5 - обновить курс");
        System.out.println("6 - найти всех студентов курса");
        System.out.println("7 - удалить по идентификатору");
        System.out.println("0 - назад");
        System.out.println("Ваш выбор:");
    }

    private void options(String choice, BufferedReader input) throws IOException {
        switch (choice) {
            case "1" -> create(input);
            case "2" -> findAll();
            case "3" -> findById(input);
            case "4" -> deleteAll();
            case "5" -> update(input);
            case "6" -> findStudents(input);
            case "7" -> delete(input);
            default -> System.out.println("Нет такой опции");
        }
        printMenu();
    }

    public void create(BufferedReader input) throws IOException {
        Course course = new Course();
        System.out.println("Введите название:");
        String statement;
        statement = input.readLine();
        course.setTitle(statement);
        System.out.println("Введите преподователя:");
        statement = input.readLine();
        course.setTeacher(statement);
        System.out.println("Введите кредиты:");
        int credits = 0;
        while (true) {
            statement = input.readLine();
            if (isNumber(statement)) {
                credits = Integer.parseInt(statement);
                break;
            }
            System.out.println("Неверный ввод");
        }
        course.setCredits(credits);
        System.out.println("Введите идентификаторы студентов через запятую:");
        List<Student> allStudent = studentService.findAll();
        for (int i = 0; i < allStudent.size(); ++i) {
            System.out.println(allStudent.get(i));
        }
        statement = input.readLine();
        String[] studentId = statement.split(",");
        List<String> students = new ArrayList<>();
        for (String id : studentId) {
            id = id.replaceAll(" ", "");
            if (studentService.findById(id) != null) {
                students.add(id);
            } else {
                System.out.println(id + " не существует");
            }
        }
        course.setStudents(students);
        courseService.create(course);
    }

    public void findAll() {
        List<Course> courses = courseService.findAll();
        for (int i = 0; i < courses.size(); ++i) {
            System.out.println(courses.get(i));
        }
    }

    public void findById(BufferedReader input) {
        System.out.println("Введите идентификатор:");
        try {
            String statement = input.readLine();
            System.out.println(courseService.findById(statement));
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(BufferedReader input) {
        System.out.println("Введите идентификатор удаляемого:");
        try {
            String statement = input.readLine();
            courseService.delete(statement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        courseService.deleteAll();
    }

    public boolean isNumber(String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void update(BufferedReader input) {
        System.out.println("Введите идентфикатор:");
        try {
            String statement = input.readLine();
            if (courseService.findById(statement) != null) {
                Course tmp = new Course();
                tmp.setId(statement);
                System.out.println("Введите название:");
                statement = input.readLine();
                tmp.setTitle(statement);
                System.out.println("Введите преподователя:");
                statement = input.readLine();
                tmp.setTeacher(statement);
                System.out.println("Введите кредиты:");
                int credits = 0;
                while (true) {
                    statement = input.readLine();
                    if (isNumber(statement)) {
                        credits = Integer.parseInt(statement);
                        break;
                    }
                    System.out.println("Неверный ввод");
                }
                tmp.setCredits(credits);
                System.out.println("Введите идентификаторы студентов через запятую:");
                List<Student> allStudents = studentService.findAll();
                for (int i = 0; i < allStudents.size(); ++i) {
                    System.out.println(allStudents.get(i));
                }
                statement = input.readLine();
                String[] studentId = statement.split(",");
                List<String> students = new ArrayList<>();
                for (String string : studentId) {
                    if (studentService.findById(string) != null) {
                        students.add(string);
                    }
                }
                tmp.setStudents(students);
                courseService.update(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findStudents(BufferedReader input) {
        System.out.println("Введите идентифкатор:");
        try {
            String statement = input.readLine();
            List<String> courseStudents = courseService.findById(statement).getStudents();
            List<Student> allStudents = studentService.findAll();
            for (Student student : allStudents) {
                if (courseStudents.contains(student.getId())) {
                    System.out.println(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
