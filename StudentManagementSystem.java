import java.util.*;

class Student {
    private final int id;
    private String name;
    private int age;
    private final Set<String> courses;

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
    private final Map<Integer, Student> students = new HashMap<>();
    private final Map<String, Set<Student>> courseEnrollments = new HashMap<>();

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
        students.values().stream()
                .sorted(Comparator.comparingInt(Student::getId))
                .forEach(System.out::println);
    }

    public Student searchStudentById(int id) {
        return students.get(id);
    }

    public List<Student> listStudentsByCourse(String course) {
        return students.values().stream()
                .filter(s -> s.getCourses().contains(course))
                .toList();
    }

    private void updateCourseEnrollments(Student student, Set<String> newCourses, Set<String> oldCourses) {
        oldCourses.forEach(course ->
                courseEnrollments.computeIfPresent(course, (k, v) -> {
                    v.remove(student);
                    return v.isEmpty() ? null : v;
                })
        );
        newCourses.forEach(course ->
                courseEnrollments.computeIfAbsent(course, k -> new HashSet<>()).add(student)
        );
    }

    public void displayAllStudentsSortedByName() {
        students.values().stream()
                .sorted(Comparator.comparing(Student::getName).thenComparingInt(Student::getId))
                .forEach(System.out::println);
    }

    public Set<Student> getStudentsByCourse(String course) {
        return courseEnrollments.getOrDefault(course, new HashSet<>());
    }
}

class Main {
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
        manager.listStudentsByCourse("Java").forEach(System.out::println);

        manager.updateStudent(1, "Bob Smith", null, Set.of("Java", "Physics"));

        System.out.println("\n--- Студенты по имени ---");
        manager.displayAllStudentsSortedByName();

        System.out.println("\n--- Студенты на курсе 'Physics' ---");
        manager.getStudentsByCourse("Physics").forEach(System.out::println);
    }
}