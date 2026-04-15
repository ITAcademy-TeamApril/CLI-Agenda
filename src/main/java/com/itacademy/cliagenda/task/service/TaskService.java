package com.itacademy.cliagenda.task.service;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.infrastructure.sql.dao.SqlDao;
import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private final TaskRepository repo;
    private final SqlDao dao;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
        this.dao = SqlDao.getInstance();
        List<Task> tasksFromDb = dao.findAllTasks();
        repo.addTasks(tasksFromDb);
    }

    public Task createTask(String body) {
        return createTask(body, null);
    }

    public Task createTask(String body, Event event_fk) {
        int id = generateNextId();
        int eventFk = event_fk != null ? event_fk.getId() : 0;
        Task newTask = new Task(id, body, eventFk);
        dao.saveTask(newTask);
        repo.addIndividualTask(newTask);
        return newTask;
    }

    public List<Task> getAllTasks() {
        return repo.getTasks();
    }

    public Task findTaskById(int id) {
        return repo.getTaskById(id);
    }

    public void deleteTaskById(int id) {
        dao.deleteTask(id);
        repo.removeTaskById(id);
    }

    public void updateTask(Task task) {
        dao.updateTask(task);
        repo.removeTaskById(task.getId());
        repo.addIndividualTask(task);
    }

    int generateNextId() {
        List<Task> tasks = repo.getTasks();
        int maxId = 0;
        for (Task task : tasks) {
            if (task.getId() > maxId) {
                maxId = task.getId();
            }
        }
        return maxId + 1;
    }
}