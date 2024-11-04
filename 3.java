import java.io.*;
import java.util.*;

class Task {
    private String title;
    private boolean isCompleted;

    public Task(String title) {
        this.title = title;
        this.isCompleted = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        isCompleted = true;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[Выполнено] " : "[Не выполнено] ") + title;
    }
}

class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    // Добавление задачи
    public void addTask(String title) {
        tasks.add(new Task(title));
        System.out.println("Задача добавлена: " + title);
    }

    // Удаление задачи
    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            System.out.println("Задача удалена: " + tasks.get(index).getTitle());
            tasks.remove(index);
        } else {
            System.out.println("Некорректный индекс задачи.");
        }
    }

    // Пометка задачи как выполненной
    public void markTaskAsCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsCompleted();
            System.out.println("Задача помечена как выполненная: " + tasks.get(index).getTitle());
        } else {
            System.out.println("Некорректный индекс задачи.");
        }
    }

    // Отображение всех задач
    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            System.out.println("\nСписок задач:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + ". " + tasks.get(i));
            }
        }
    }

    // Отображение выполненных задач
    public void displayCompletedTasks() {
        System.out.println("\nСписок выполненных задач:");
        tasks.stream()
            .filter(Task::isCompleted)
            .forEach(System.out::println);
    }

    // Сохранение списка задач в файл
    public void saveTasksToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Task task : tasks) {
            writer.write(task.getTitle() + "," + task.isCompleted());
            writer.newLine();
        }
        writer.close();
        System.out.println("Список задач сохранен в файл.");
    }

    // Загрузка списка задач из файла
    public void loadTasksFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        tasks.clear();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String title = parts[0];
                boolean isCompleted = Boolean.parseBoolean(parts[1]);
                Task task = new Task(title);
                if (isCompleted) {
                    task.markAsCompleted();
                }
                tasks.add(task);
            }
        }
        reader.close();
        System.out.println("Список задач загружен из файла.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить задачу");
            System.out.println("2. Удалить задачу");
            System.out.println("3. Отметить задачу как выполненную");
            System.out.println("4. Показать все задачи");
            System.out.println("5. Показать выполненные задачи");
            System.out.println("6. Сохранить задачи в файл");
            System.out.println("7. Загрузить задачи из файла");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Введите название задачи: ");
                        String title = scanner.nextLine();
                        taskManager.addTask(title);
                        break;
                    case 2:
                        System.out.print("Введите номер задачи для удаления: ");
                        int removeIndex = scanner.nextInt() - 1;
                        taskManager.removeTask(removeIndex);
                        break;
                    case 3:
                        System.out.print("Введите номер задачи для отметки как выполненной: ");
                        int completeIndex = scanner.nextInt() - 1;
                        taskManager.markTaskAsCompleted(completeIndex);
                        break;
                    case 4:
                        taskManager.displayTasks();
                        break;
                    case 5:
                        taskManager.displayCompletedTasks();
                        break;
                    case 6:
                        System.out.print("Введите имя файла для сохранения: ");
                        String saveFileName = scanner.nextLine();
                        taskManager.saveTasksToFile(saveFileName);
                        break;
                    case 7:
                        System.out.print("Введите имя файла для загрузки: ");
                        String loadFileName = scanner.nextLine();
                        taskManager.loadTasksFromFile(loadFileName);
                        break;
                    case 0:
                        System.out.println("Выход из программы.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Некорректный выбор. Пожалуйста, выберите действие из меню.");
                }
            } catch (IOException e) {
                System.out.println("Ошибка при работе с файлом: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Пожалуйста, введите корректные данные.");
                scanner.nextLine(); // Очистка буфера
            }
        }
    }
}