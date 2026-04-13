package com.itacademy.cliagenda.task.service;

import com.itacademy.cliagenda.infrastructure.sql.dao.SqlDao;
import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class TaskService {

    private TaskRepository repo;
    private SqlDao dao;

    public TaskService() {
        this.repo = new TaskRepository();
        this.dao = SqlDao.getInstance();
        repo.addTasks(dao.findAllTasks());
    }

    public List<Task> extractDatabaseTasksTable(){
        return dao.findAllTasks();
    }

    Task createTask(int id, String name, LocalDateTime taskDate){
        return new Task(id, name, taskDate);
    }

    Task createTaskAllParams(int id, String name, LocalDateTime taskDate, LocalDateTime creationDate, LocalDateTime lastUpdateDate, int event_fk){
        return new Task(id, name, taskDate, creationDate, lastUpdateDate, event_fk);
    }

    int checkTaskId(){
        List<Task> tasks = repo.getTasks();
        if (tasks.isEmpty()) {
            return 0;
        }
        int maxId = 0;
        for (Task task : tasks) {
            if (task.getId() > maxId) {
                maxId = task.getId();
            }
        }
        return maxId;
    }

    public void addTask(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre de la tarea:");
        String name = scanner.nextLine();
        
        int id = checkTaskId() + 1;
        Task task = createTask(id, name, LocalDateTime.now());
        
        addTaskDB(task);
        repo.addIndividualTask(task);
        System.out.println("Tarea añadida con ID: " + id);
    }

    public void addTaskDB(Task task){
        dao.saveTask(task);
    }

}