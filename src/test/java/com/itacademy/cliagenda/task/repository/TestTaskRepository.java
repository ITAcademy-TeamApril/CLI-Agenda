package com.itacademy.cliagenda.task.repository;

import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.event.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TestTaskRepository {

    private TaskRepository repository;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setUp() {
        task1 = new Task(1, "Tarea 1", 1);
        task2 = new Task(2, "Tarea 2", 1);
        task3 = new Task(3, "Tarea 3", 2);
        
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        repository = new TaskRepository(tasks);
    }

    @Test
    void testConstructor() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        repository = new TaskRepository(tasks);
        assertEquals(2, repository.getTasks().size());
    }

    @Test
    void testGetTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        repository = new TaskRepository(tasks);
        assertNotNull(repository.getTasks());
        assertEquals(1, repository.getTasks().size());
    }

    @Test
    void testAddTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        repository = new TaskRepository(tasks);
        
        List<Task> moreTasks = new ArrayList<>();
        moreTasks.add(task2);
        moreTasks.add(task3);
        repository.addTasks(moreTasks);
        
        assertEquals(3, repository.getTasks().size());
    }

    @Test
    void testAddIndividualTask() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        repository = new TaskRepository(tasks);
        
        repository.addIndividualTask(task2);
        
        assertEquals(2, repository.getTasks().size());
    }

    @Test
    void testAddIndividualTaskNull() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        repository = new TaskRepository(tasks);
        
        repository.addIndividualTask(null);
        
        assertEquals(1, repository.getTasks().size());
    }

    @Test
    void testRemoveTaskById() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        repository = new TaskRepository(tasks);
        
        repository.removeTaskById(2);
        
        assertEquals(2, repository.getTasks().size());
        assertFalse(repository.getTasks().contains(task2));
    }

    @Test
    void testRemoveTaskByIdNotFound() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        repository = new TaskRepository(tasks);
        
        repository.removeTaskById(99);
        
        assertEquals(2, repository.getTasks().size());
    }

    @Test
    void testGetTaskById() {
        Task result = repository.getTaskById(2);
        
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("Tarea 2", result.getBody());
    }

    @Test
    void testGetTaskByIdNotFound() {
        Task result = repository.getTaskById(99);
        
        assertNull(result);
    }

    @Test
    void testGetTaskByEventFK() {
        List<Task> result = repository.getTaskByEventFK(2);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getEvent_fk());
        assertEquals("Tarea 3", result.get(0).getBody());
    }

    @Test
    void testGetTaskByEventFKNotFound() {
        List<Task> result = repository.getTaskByEventFK(99);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTaskByEventFKReturnsAllMatches() {
        List<Task> result = repository.getTaskByEventFK(1);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(task1));
        assertTrue(result.contains(task2));
    }

    @Test
    void testEmptyConstructor() {
        repository = new TaskRepository();
        assertNotNull(repository.getTasks());
        assertTrue(repository.getTasks().isEmpty());
    }
}