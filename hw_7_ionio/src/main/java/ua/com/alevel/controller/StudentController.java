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

public class StudentController {

    public final static StudentServiceImpl studentService = new StudentServiceImpl();
    public final static CourseServiceImpl courseService = new CourseServiceImpl();

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
        System.out.println("\n\tСущность \"Студент\"");
        System.out.println("1 - создать студента");
        System.out.println("2 - найти всех");
        System.out.println("3 - найти по идентификатору");
        System.out.println("4 - удалить всех");
        System.out.println("5 - обновить студентов");
        System.out.println("6 - найти все курсы студента");
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
            case "6" -> findCourses(input);
            case "7" -> delete(input);
            default -> System.out.println("Нет такой опции");
        }
        printMenu();
    }

    public boolean isNumber(String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void create(BufferedReader input) throws IOException {
        Student student = new Student();
        System.out.println("Введите фамилию:");
        String statement;
        statement = input.readLine();
        student.setLastName(statement);
        System.out.println("Введите имя:");
        statement = input.readLine();
        student.setFirstName(statement);
        System.out.println("Введите возраст");
        int age = 0;
        while (true) {
            statement = input.readLine();
            if (isNumber(statement)) {
                age = Integer.parseInt(statement);
                break;
            }
            System.out.println("Неверный ввод");
        }
        student.setAge(age);
        System.out.println("Введите идентификаторы курсов через запятую:");
        List<Course> allCourses = courseService.findAll();
        for (int i = 0; i < allCourses.size(); ++i) {
            System.out.println(allCourses.get(i));
        }
        statement = input.readLine();
        String[] coursesId = statement.split(",");
        List<String> courses = new ArrayList<>();
        for (String id : coursesId) {
            id = id.replaceAll(" ", "");
            if (courseService.findById(id) != null) {
                courses.add(id);
            } else {
                System.out.println(id + " не существует");
            }
        }
        student.setCourses(courses);
        studentService.create(student);
    }

    public void findAll() {
        List<Student> students = studentService.findAll();
        for (int i = 0; i < students.size(); ++i) {
            System.out.println(students.get(i));
        }
    }

    public void findById(BufferedReader input) {
        System.out.println("Введите идентификатор:");
        try {
            String statement = input.readLine();
            System.out.println(studentService.findById(statement));
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(BufferedReader input) {
        System.out.println("Введите идентификатор удаляемого:");
        try {
            String statement = input.readLine();
            studentService.delete(statement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        studentService.deleteAll();
    }

    public void update(BufferedReader input) {
        System.out.println("Введите идентфикатор:");
        try {
            String statement = input.readLine();
            if (studentService.findById(statement) != null) {
                Student tmp = new Student();
                tmp.setId(statement);
                System.out.println("Введите фамилию:");
                statement = input.readLine();
                tmp.setLastName(statement);
                System.out.println("Введите имя:");
                statement = input.readLine();
                tmp.setFirstName(statement);
                System.out.println("Введите возраст:");
                int age = 0;
                while (true) {
                    statement = input.readLine();
                    if (isNumber(statement)) {
                        age = Integer.parseInt(statement);
                        break;
                    }
                    System.out.println("Неверный ввод");
                }
                tmp.setAge(age);
                System.out.println("Введите идентификаторы курсов через запятую:");
                List<Course> allCourses = courseService.findAll();
                for (int i = 0; i < allCourses.size(); ++i) {
                    System.out.println(allCourses.get(i));
                }
                statement = input.readLine();
                String[] coursesId = statement.split(",");
                List<String> courses = new ArrayList<>();
                for (String string : coursesId) {
                    if (courseService.findById(string) != null) {
                        courses.add(string);
                    }
                }
                tmp.setCourses(courses);
                studentService.update(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findCourses(BufferedReader input) {
        System.out.println("Введите идентифкатор:");
        try {
            String statement = input.readLine();
            List<String> studentCourses = studentService.findById(statement).getCourses();
            List<Course> allCourses = courseService.findAll();
            for (Course course : allCourses) {
                if (studentCourses.contains(course.getId())) {
                    System.out.println(course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
