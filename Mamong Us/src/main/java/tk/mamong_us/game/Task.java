package tk.mamong_us.game;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Task {
    private static Random random = new Random();

    private TaskType type;
    private int completed;

    public Task(TaskType type) {
        this.type = type;
        completed = 0;
    }

    public TaskType getType() {
        return type;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "type=" + type +
                ", completed=" + completed +
                '}';
    }

    public static @NotNull List<Task> generate(TaskLength length, int n) {
        Task[] res = new Task[n];
        ArrayList<TaskType> used = new ArrayList<>();
        ArrayList<TaskType> possible = new ArrayList<>();
        for (TaskType type1 : TaskType.values()) {
            if (type1.length == length) {
                possible.add(type1);
            }
        }
        for (int i=0; i<n; i++) {
            int c = random.nextInt(possible.size());
            TaskType type = possible.get(c);
            if (used.contains(type)) {
                i--;
                continue;
            }
            used.add(type);
            res[i] = new Task(type);
        }
        return Arrays.asList(res);
    }

    public enum TaskType {
        SWIPE_CARD("Admin: Swipe Card", TaskLength.COMMON, 1),
        STABILIZE_STEERING("Navigation: Stabilize Steering", TaskLength.SHORT, 1),
        DIVERT_POWER("Electrical: Divert Power", TaskLength.SHORT, 2),
        FIX_WIRING("Electrical: Fix Wiring", TaskLength.SHORT, 3),
        DOWNLOAD_DATA("Communications: Download Data", TaskLength.LONG, 2),
        START_REACTOR("Reactor: Start Reactor", TaskLength.LONG, 1);

        public final String name;
        public final TaskLength length;
        public final int steps;

        TaskType(String n, TaskLength l, int s) {
            name = n;
            length = l;
            steps = s;
        }
    }

    public enum TaskLength {
        COMMON, LONG, SHORT;
    }
}
