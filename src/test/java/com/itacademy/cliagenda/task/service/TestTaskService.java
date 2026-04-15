package com.itacademy.cliagenda.task.service;

import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.repository.TaskRepository;
import com.itacademy.cliagenda.testing.TestContainerManager;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestTaskService {

    private TaskService taskService;
    private TaskRepository repo;

    @BeforeAll
    static void setUpAll() throws Exception {
        TestContainerManager.ensureRunning();
    }

    @BeforeEach
    void setUp() throws Exception {
        TestContainerManager.clearAllTables();
        repo = new TaskRepository();
        taskService = new TaskService(repo);
    }

    @Test
    void testGetAllTasksEmpty() {
        List<Task> tasks = taskService.getAllTasks();
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void testCreateTask() {
        Task task = taskService.createTask("Nueva tarea de prueba");
        
        assertNotNull(task);
        assertEquals("Nueva tarea de prueba", task.getBody());
        
        List<Task> tasks = taskService.getAllTasks();
        assertEquals(1, tasks.size());
    }

    @Test
    void testDeleteTaskById() {
        Task task = taskService.createTask("Tarea para borrar");
        
        taskService.deleteTaskById(task.getId());
        
        assertNull(taskService.findTaskById(task.getId()));
    }

    @Test
    void testUpdateTask() {
        Task task = taskService.createTask("Tarea original");
        
        task.changeBody("Tarea actualizada");
        taskService.updateTask(task);
        
        Task updated = taskService.findTaskById(task.getId());
        assertNotNull(updated);
        assertEquals("Tarea actualizada", updated.getBody());
    }

    @Test
    void testFindTaskById() {
        Task task = taskService.createTask("Tarea buscada");
        
        Task found = taskService.findTaskById(task.getId());
        
        assertNotNull(found);
        assertEquals("Tarea buscada", found.getBody());
    }

    @Test
    void testFindTaskByIdNotFound() {
        Task task = taskService.findTaskById(999);
        assertNull(task);
    }

    @Test
    void testCreateTaskWithEvent() {
        Task task = taskService.createTask("Tarea con evento", null);
        
        assertNotNull(task);
        assertEquals("Tarea con evento", task.getBody());
    }
}