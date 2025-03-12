import java.util.*;

class Task implements Comparable<Task> {
    private String taskName;
    private int priority;
    private int duration;

    public Task(String taskName, int priority, int duration) {
        this.taskName = taskName;
        this.priority = priority;
        this.duration = duration;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getPriority() {
        return priority;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int compareTo(Task other) {
        if (this.priority != other.priority) {
            return other.priority - this.priority;
        } else {
            return this.duration - other.duration;
        }
    }

    @Override
    public String toString() {
        return "[Priority " + priority + "] " + taskName + " (Duration: " + duration + " mins)";
    }
}

class TaskScheduler {
    private PriorityQueue<Task> taskQueue;
    private Queue<Task> pendingQueue;

    public TaskScheduler() {
        taskQueue = new PriorityQueue<>();
        pendingQueue = new LinkedList<>();
    }

    public void addTask(Task task) {
        taskQueue.add(task);
    }

    public void processTask() {
        if (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            System.out.println("Processing Task: " + task);
        } else if (!pendingQueue.isEmpty()) {
            Task task = pendingQueue.poll();
            System.out.println("Processing Task from Pending Queue: " + task);
        } else {
            System.out.println("No tasks to process.");
        }
    }

    public void delayTask(Task task) {
        pendingQueue.add(task);
        System.out.println("Delaying Task: " + task.getTaskName());
    }

    public void displayScheduledTasks() {
        System.out.println("Scheduled Tasks (sorted by priority):");
        for (Task task : taskQueue) {
            System.out.println(task);
        }
    }

    public void displayPendingTasks() {
        System.out.println("Pending Tasks (FIFO Order):");
        for (Task task : pendingQueue) {
            System.out.println(task);
        }
    }
}

class Main {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        scheduler.addTask(new Task("Write Report", 4, 30));
        scheduler.addTask(new Task("Prepare Presentation", 3, 45));
        scheduler.addTask(new Task("Team Meeting", 5, 60));
        scheduler.addTask(new Task("Email Clients", 2, 15));
        scheduler.addTask(new Task("Debug Code", 4, 25));

        System.out.println("Tasks Added:");
        scheduler.displayScheduledTasks();

        System.out.println("\nProcessing Task:");
        scheduler.processTask();

        System.out.println("\nDelaying Task: Prepare Presentation");
        scheduler.delayTask(new Task("Prepare Presentation", 3, 45));

        System.out.println("\nScheduled Tasks:");
        scheduler.displayScheduledTasks();

        System.out.println("\nPending Tasks:");
        scheduler.displayPendingTasks();

        System.out.println("\nProcessing Task:");
        scheduler.processTask();

        System.out.println("\nDelaying Task: Email Clients");
        scheduler.delayTask(new Task("Email Clients", 2, 15));

        System.out.println("\nScheduled Tasks:");
        scheduler.displayScheduledTasks();

        System.out.println("\nPending Tasks:");
        scheduler.displayPendingTasks();

        System.out.println("\nProcessing Task:");
        scheduler.processTask();

        System.out.println("\nProcessing Task:");
        scheduler.processTask();

        System.out.println("\nProcessing Task:");
        scheduler.processTask();

        System.out.println("\nProcessing Task:");
        scheduler.processTask();
    }
}