import java.util.ArrayList;
import java.util.Scanner;

class Task {
    String description;
    boolean isCompleted;

    Task(String description) {
        this.description = description;
        isCompleted = false;
    }

    @Override
    public String toString() {
        return description + " [" + (isCompleted ? "Done" : "not Done") + "]";
    }
}

public class TaskManager {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add a new task");
            System.out.println("2. Delete a task");
            System.out.println("3. Mark a task as completed");
            System.out.println("4. Show all tasks");
            System.out.println("5. Show all completed tasks");
            System.out.println("6. Show all not completed tasks");
            System.out.println("7. Exit");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    tasks.add(new Task(description));
                    System.out.println("Task added: !");
                    break;

                case 2:
                    System.out.print("Enter ID: ");
                    int indexToRemove = scanner.nextInt() - 1;
                    if (indexToRemove >= 0 && indexToRemove < tasks.size()) {
                        Task removedTask = tasks.remove(indexToRemove);
                        System.out.println("Task \"" + removedTask.description + "\" deleted.");
                    } else {
                        System.out.println("Wrong ID.");
                    }
                    break;

                case 3:
                    System.out.print("Enter ID: ");
                    int indexToComplete = scanner.nextInt() - 1;
                    if (indexToComplete >= 0 && indexToComplete < tasks.size()) {
                        Task task = tasks.get(indexToComplete);
                        task.isCompleted = true;
                        System.out.println("Task \"" + task.description + "\" marked as completed.");
                    } else {
                        System.out.println("Wrong ID.");
                    }
                    break;

                case 4:
                    System.out.println("All tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    break;

                case 5:
                    System.out.println("All completed tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        Task task = tasks.get(i);
                        if (task.isCompleted) {
                            System.out.println((i + 1) + ". " + task);
                        }
                    }
                    break;

                case 6:
                    System.out.println("All not completed tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        Task task = tasks.get(i);
                        if (!task.isCompleted) {
                            System.out.println((i + 1) + ". " + task);
                        }
                    }
                    break;

                case 7:
                    System.out.println("Exit program.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Wrong choice. Try again.");
            }
        }
    }
}
