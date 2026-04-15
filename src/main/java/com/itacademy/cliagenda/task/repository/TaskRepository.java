package com.itacademy.cliagenda.task.repository;

import com.itacademy.cliagenda.task.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private final List<Task> tasks;

    public TaskRepository(List<Task> tasks) {
        this.tasks = new ArrayList<>();
        addTasks(tasks);
    }

    public TaskRepository() {
        this.tasks = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTasks(List<Task> tasks) {
        if (tasks != null && !tasks.isEmpty()) {
            this.tasks.addAll(tasks);
        }
    }

    public void addIndividualTask(Task task) {
        if (task != null) {
            this.tasks.add(task);
        }
    }

    public void removeTaskById(int id) {
        Task taskToRemove = null;
        for (Task task : tasks) {
            if (task.getId() == id) {
                taskToRemove = task;
                break;
            }
        }
        if (taskToRemove != null) {
            tasks.remove(taskToRemove);
        } else {
            System.out.println("No se encontró ninguna nota con el ID: " + id);
        }
    }

    public Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public List<Task> getTaskByEventFK(int event_fk) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getEvent_fk() == event_fk) {
                result.add(task);
            }
        }
        return result;
    }
}
