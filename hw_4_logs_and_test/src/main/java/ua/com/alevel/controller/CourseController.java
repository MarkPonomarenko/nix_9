package ua.com.alevel.controller;

import ua.com.alevel.entity.Course;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.CourseService;
import ua.com.alevel.util.CustomArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CourseController {

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
        System.out.println("\n\tСущность \"Курс\"");
        System.out.println("1 - создать курс");
        System.out.println("2 - обновить курс");
        System.out.println("3 - удалить курс");
        System.out.println("4 - найти по идентификатору");
        System.out.println("5 - найти все");
        System.out.println("6 - найти всех студентов курса");
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
            case "6" -> findCourseStudents(input);
            default -> System.out.println("Нет такой опции");
        }
        printMenu();
    }

    private int safeInput(String raw) {
        for (int i = 0; i < raw.length(); ++i) {
            if (!Character.isDigit(raw.charAt(i))) {
                return -1;
            }
        }
        return Integer.parseInt(raw);
    }

    private void create(BufferedReader input) {
        System.out.println("\tСоздание курса");
        try {
            System.out.println("Название курса:");
            String title = input.readLine();
            System.out.println("Введите имя переподователя:");
            String teacher = input.readLine();
            CustomArray<Student> students = new CustomArray<>();
            System.out.println("Сколько новых студентов хотите создать?:");
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
                System.out.println("\tСтудент " + (i + 1));
                System.out.println("Фамилия студента:");
                String lastName = input.readLine();
                System.out.println("Введите имя студента");
                String firstName = input.readLine();
                System.out.println("Возраст студента:");
                int age;
                String rawAge;
                while (true) {
                    rawAge = input.readLine();
                    if ((age = safeInput(rawAge)) > 0) {
                        break;
                    }
                    System.out.println("Неверный ввод");
                }
                Student student = new Student();
                student.setLastName(lastName);
                student.setFirstName(firstName);
                student.setAge(age);
                students.add(student);
            }
            Course course = new Course();
            course.setTitle(title);
            course.setTeacher(teacher);
            course.setStudents(students);
            courseService.create(course);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void update(BufferedReader input) {
        System.out.println("\tОбновление курса");
        try {
            System.out.println("Введите идентификатор:");
            String id = input.readLine();
            System.out.println("Название курса:");
            String title = input.readLine();
            System.out.println("Введите имя преподователя:");
            String teacher = input.readLine();
            Course course = new Course();
            course.setId(id);
            course.setTitle(title);
            course.setTeacher(teacher);
            courseService.update(course);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void delete(BufferedReader input) {
        System.out.println("\tУдаление курса");
        try {
            System.out.println("Введите идентификатор удаляемого курса:");
            String id = input.readLine();
            courseService.delete(id);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void findById(BufferedReader input) {
        System.out.println("\tПоиск по идентификатору");
        try {
            System.out.println("Введите идентификатор:");
            String id = input.readLine();
            System.out.println(courseService.findById(id));
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private void findAll() {
        System.out.println("\tПоиск всех");
        CustomArray<Course> courses = courseService.findAll();
        for (int i = 0; i < courses.size(); ++i) {
            System.out.println(courses.getAt(i));
        }
    }

    private void findCourseStudents(BufferedReader input) {
        System.out.println("\tСтуденты курса");
        try {
            System.out.println("Введите идентификатор курса:");
            String id = input.readLine();
            System.out.println(courseService.findCourseStudents(id));
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
