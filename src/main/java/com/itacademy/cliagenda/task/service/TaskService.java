package com.itacademy.cliagenda.task.service;

import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;


public class TaskService {

    private TaskRepository repo;
    //private SqlDao dao;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
        //this.dao = SqlDao.getInstance();
        //repo.addTasks(dao.findAllTasks());
    }

    public Task createTask(String body) {
        int id = generateNextId();
        Task newTask = new Task(id, body);
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
        repo.removeTaskById(id);
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

    /*
    public List<Task> extractDatabaseTasksTable(){
        return dao.findAllTasks();
    }
    */

    /*
    public void addTaskDB(Task task){
        dao.saveTask(task);
    }
    */

    /*
    Task createTaskAllParams(int id, String name, LocalDateTime taskDate, LocalDateTime creationDate, LocalDateTime lastUpdateDate, int event_fk) {
        return new Task(id, name, taskDate, creationDate, lastUpdateDate, event_fk);
    }
    */

}