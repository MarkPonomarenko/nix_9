package ua.com.alevel.util.query;

public class SqlQuery {

    private SqlQuery() {

    }

    public static final String CREATE_COURSE = "INSERT INTO courses VALUES(default,?,?,?,?,?)";
    public static final String COURSE_COUNT = "SELECT COUNT(*) AS counter FROM courses";
    public static final String UPDATE_COURSE_BY_ID = "UPDATE courses SET title = ?, teacher = ?, credits = ?, updated = ? WHERE id = ";
    public static final String DELETE_COURSE_BY_ID = "DELETE FROM courses WHERE id = ";
    public static final String EXIST_COURSE_BY_ID = "SELECT COUNT(*) FROM courses WHERE id = ";
    public static final String FIND_ALL_COURSES = "SELECT * FROM courses";
    public static final String FIND_COURSE_BY_ID = "SELECT * FROM courses WHERE id = ";
    public static final String FIND_COURSES_BY_STUDENT_ID = "SELECT * FROM accounting WHERE student_id = ";
    public static final String FIND_ALL_ACCOUNTINGS_BY_COURSE_ID = "SELECT courses.*, COUNT(accounting.student_id) AS students " +
            "FROM courses LEFT JOIN accounting ON courses.id = accounting.course_id GROUP BY courses.id ORDER BY ";

    public static final String CREATE_STUDENT = "INSERT INTO students VALUES(default,?,?,?,?,?)";
    public static final String STUDENT_COUNT = "SELECT COUNT(*) AS counter FROM students";
    public static final String UPDATE_STUDENT_BY_ID = "UPDATE students SET last_name = ?, first_name = ?, age = ? WHERE id = ";
    public static final String DELETE_STUDENT_BY_ID = "DELETE FROM students WHERE id = ";
    public static final String EXIST_STUDENT_BY_ID = "SELECT COUNT(*) FROM students WHERE id = ";
    public static final String FIND_ALL_STUDENTS = "SELECT * FROM students";
    public static final String FIND_STUDENT_BY_ID = "SELECT * FROM students WHERE id = ";
    public static final String FIND_STUDENTS_BY_COURSE_ID = "SELECT * FROM accounting WHERE course_id = ";
    public static final String FIND_ALL_ACCOUNTINGS_BY_STUDENT_ID = "SELECT students.*, COUNT(accounting.course_id) AS courses " +
            "FROM students LEFT JOIN accounting ON students.id = accounting.student_id GROUP BY students.id ORDER BY ";


    public static final String CREATE_ACCOUNTING = "INSERT INTO accounting VALUES(default,?,?)";
    public static final String DELETE_ACCOUNTING_BY_ID = "DELETE FROM accounting WHERE id = ";
    public static final String EXIST_ACCOUNTING_BY_ID = "SELECT COUNT(*) FROM accounting WHERE id = ";
    public static final String FIND_ACCOUNTING_BY_ID = "SELECT * FROM accounting WHERE id = ";
    public static final String FIND_ACCOUNTING_BY_COURSE_STUDENT_IDS = "SELECT * FROM accounting WHERE course_id = ? AND student_id = ?";


}
