import java.util.*;

class Student {
    private int id;
    private String name;
    private int age;
    private Set<String> courses;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.courses = new HashSet<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public Set<String> getCourses() { return courses; }
    public void addCourse(String course) { courses.add(course); }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Age: %d, Courses: %s", id, name, age, courses);
    }
}

class StudentManager {
    private Map<Integer, Student> students = new HashMap<>();
    private Map<String, Set<Student>> courseEnrollments = new HashMap<>();

    public boolean addStudent(Student student) {
        if (students.containsKey(student.getId())) return false;
        students.put(student.getId(), student);
        updateCourseEnrollments(student, student.getCourses(), Collections.emptySet());
        return true;
    }

    public boolean removeStudent(int id) {
        Student student = students.remove(id);
        if (student == null) return false;
        updateCourseEnrollments(student, Collections.emptySet(), student.getCourses());
        return true;
    }

    public boolean updateStudent(int id, String newName, Integer newAge, Set<String> newCourses) {
        Student student = students.get(id);
        if (student == null) return false;

        if (newName != null) student.setName(newName);
        if (newAge != null) student.setAge(newAge);
        if (newCourses != null) {
            Set<String> oldCourses = new HashSet<>(student.getCourses());
            student.getCourses().clear();
            student.getCourses().addAll(newCourses);
            updateCourseEnrollments(student, newCourses, oldCourses);
        }
        return true;
    }

    public void displayAllStudentsSortedById() {
        List<Student> studentList = new ArrayList<>(students.values());
        Collections.sort(studentList, Comparator.comparingInt(Student::getId));
        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    public Student searchStudentById(int id) {
        return students.get(id);
    }

    public List<Student> listStudentsByCourse(String course) {
        List<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getCourses().contains(course)) {
                result.add(student);
            }
        }
        return result;
    }

    private void updateCourseEnrollments(Student student, Set<String> newCourses, Set<String> oldCourses) {
        for (String course : oldCourses) {
            if (courseEnrollments.containsKey(course)) {
                courseEnrollments.get(course).remove(student);
                if (courseEnrollments.get(course).isEmpty()) {
                    courseEnrollments.remove(course);
                }
            }
        }
        for (String course : newCourses) {
            if (!courseEnrollments.containsKey(course)) {
                courseEnrollments.put(course, new HashSet<>());
            }
            courseEnrollments.get(course).add(student);
        }
    }

    public void displayAllStudentsSortedByName() {
        List<Student> studentList = new ArrayList<>(students.values());
        Collections.sort(studentList, Comparator.comparing(Student::getName).thenComparingInt(Student::getId));
        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    public Set<Student> getStudentsByCourse(String course) {
        return courseEnrollments.getOrDefault(course, new HashSet<>());
    }
}

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();

        Student s1 = new Student(3, "Alice", 20);
        s1.addCourse("Java");
        s1.addCourse("Math");
        manager.addStudent(s1);

        Student s2 = new Student(1, "Bob", 22);
        s2.addCourse("Java");
        manager.addStudent(s2);

        System.out.println("--- Все студенты ---");
        manager.displayAllStudentsSortedById();

        System.out.println("\n--- Студенты на курсе 'Java' ---");
        for (Student student : manager.listStudentsByCourse("Java")) {
            System.out.println(student);
        }

        manager.updateStudent(1, "Bob Smith", null, Set.of("Java", "Physics"));

        System.out.println("\n--- Студенты по имени ---");
        manager.displayAllStudentsSortedByName();

        System.out.println("\n--- Студенты на курсе 'Physics' ---");
        for (Student student : manager.getStudentsByCourse("Physics")) {
            System.out.println(student);
        }
    }
}
