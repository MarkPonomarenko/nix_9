package ua.com.alevel.controller;

import ua.com.alevel.entity.Student;
import ua.com.alevel.entity.Course;
import ua.com.alevel.service.CourseService;
import ua.com.alevel.service.StudentService;
import ua.com.alevel.util.CustomArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StudentController {

    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();

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
        System.out.println("2 - обновить студента");
        System.out.println("3 - удалить студента");
        System.out.println("4 - найти по идентификатору");
        System.out.println("5 - найти всех");
        System.out.println("6 - найти все курсы студента");
        System.out.println("0 - назад");
        System.out.println("Ваш выбор:");
    }

    private void options(String choice, BufferedReader input) {
        switch (choice) {
            case "1" -> create(input);
            case "2" -> update(input);
            case "3" -> delete(input);
            case "4" -> findById(input);
            case "5" -> findAll();
            case "6" -> findStudentCourses(input);
            default -> System.out.println("Нет такой опции");
        }
        printMenu();
    }

    private int safeInput(String rawAge) {
        for (int i = 0; i < rawAge.length(); ++i) {
            if (!Character.isDigit(rawAge.charAt(i))) {
                return -1;
            }
        }
        return Integer.parseInt(rawAge);
    }

    private void create(BufferedReader input) {
        System.out.println("\tСоздание студента");
        try {
            System.out.println("Введите фамилию:");
            String lastName = input.readLine();
            System.out.println("Введите имя:");
            String firstName = input.readLine();
            System.out.println("Введите возраст:");
            int age;
            String rawAge;
            while (true) {
                rawAge = input.readLine();
                if ((age = safeInput(rawAge)) > 0) {
                    break;
                }
                System.out.println("Неверный ввод");
            }
            CustomArray<Course> courses = new CustomArray<>();
            System.out.println("На скольки курсах состоит студент?:");
            int count;
            String countRaw;
            while (true) {
                countRaw = input.readLine();
                if ((count = safeInput(countRaw)) > 0) {
                    break;
                }
                System.out.println("Неверный ввод");
            }
            for (int i = 0; i < count; ++i) {
                System.out.println("Введите идентификатор курса " + (i + 1) + " : ");
                String id = input.readLine();
                if (courseService.findById(id) != null) {
                    courses.add(courseService.findById(id));
                }
            }
            Student student = new Student();
            student.setLastName(lastName);
            student.setFirstName(firstName);
            student.setAge(age);
            student.setCourses(courses);
            studentService.create(student);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void update(BufferedReader input) {
        System.out.println("\tОбновление студента");
        try {
            System.out.println("Введите идентификатор:");
            String id = input.readLine();
            System.out.println("Фамилия студента:");
            String lastName = input.readLine();
            System.out.println("Введите имя студента:");
            String firstName = input.readLine();
            System.out.println("Введите возраст:");
            int age;
            String rawAge;
            while (true) {
                rawAge = input.readLine();
                if ((age = safeInput(rawAge)) > 0) {
                    break;
                }
                System.out.println("Неверный ввод");
            }
            System.out.println("На скольки курсах будет обновленный студент:");
            int count;
            String countRaw;
            while (true) {
                countRaw = input.readLine();
                if ((count = safeInput(countRaw)) > 0) {
                    break;
                }
                System.out.println("Неверный ввод");
            }
            CustomArray<Course> courses = new CustomArray<>();
            for (int i = 0; i < count; ++i) {
                System.out.println("Введите идентификатор курса " + (i + 1) + " : ");
                String cId = input.readLine();
                if (courseService.findById(cId) != null) {
                    courses.add(courseService.findById(cId));
                }
            }
            Student student = new Student();
            student.setId(id);
            student.setLastName(lastName);
            student.setFirstName(firstName);
            student.setAge(age);
            student.setCourses(courses);
            studentService.update(student);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void delete(BufferedReader input) {
        System.out.println("\tУдаление студента");
        try {
            System.out.println("Введите идентификатор:");
            String id = input.readLine();
            studentService.delete(id);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void findById(BufferedReader input) {
        System.out.println("\tПоиск по идентификатору");
        try {
            System.out.println("Введите дентификатор:");
            String id = input.readLine();
            System.out.println(studentService.findById(id));
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void findAll() {
        System.out.println("\tПоиск всех");
        CustomArray<Student> students = studentService.findAll();
        for (int i = 0; i < students.size(); ++i) {
            System.out.println(students.getAt(i));
        }
    }

    private void findStudentCourses(BufferedReader input) {
        System.out.println("\tКурсы студента");
        try {
            System.out.println("Введите идентификатор:");
            String id = input.readLine();
            System.out.println(studentService.findStudentCourses(id));
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
