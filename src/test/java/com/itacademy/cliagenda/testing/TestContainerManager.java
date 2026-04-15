package com.itacademy.cliagenda.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class TestContainerManager {

    private static boolean started = false;
    private static boolean schemaCreated = false;
    private static Process dockerProcess;
    private static Connection connection;

    public static void ensureRunning() throws Exception {
        if (!started) {
            startContainer();
            loadProperties();
            createSchema();
            started = true;
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    private static void startContainer() throws Exception {
        System.out.println("Starting MySQL container (shared for all tests)...");
        
        ProcessBuilder pb = new ProcessBuilder(
            "docker-compose", "up", "-d", "mysql"
        );
        pb.directory(new File(System.getProperty("user.dir")));
        pb.redirectErrorStream(true);
        
        dockerProcess = pb.start();
        
        String output = new String(dockerProcess.getInputStream().readAllBytes());
        System.out.println("Docker output: " + output);
        
        dockerProcess.waitFor();
        
        System.out.println("Waiting for MySQL to be ready...");
        Thread.sleep(15000);
        
        System.out.println("MySQL container started (shared).");
    }

    private static void loadProperties() throws Exception {
        Properties props = new Properties();
        try (InputStream input = TestContainerManager.class.getClassLoader()
                .getResourceAsStream("com/itacademy/cliagenda/application/config/application.properties")) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar application.properties");
            }
            props.load(input);
        }

        String url = props.getProperty("jdbc.url");
        String user = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        int retries = 10;
        while (retries > 0) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to MySQL (shared connection).");
                return;
            } catch (java.sql.SQLException e) {
                retries--;
                if (retries == 0) throw e;
                System.out.println("Retrying connection... (" + retries + " attempts left)");
                Thread.sleep(3000);
            }
        }
    }

    private static void createSchema() throws Exception {
        if (schemaCreated) {
            return;
        }
        
        System.out.println("Creating schema (shared)...");
        
        StringBuilder sql = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(TestContainerManager.class.getClassLoader()
                        .getResourceAsStream("schema.sql")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
        }

        try (Statement stmt = connection.createStatement()) {
            String[] statements = sql.toString().split(";");
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    try {
                        stmt.execute(statement.trim());
                    } catch (java.sql.SQLException e) {
                        if (!e.getMessage().contains("already exists")) {
                            System.err.println("Error executing: " + statement.trim() + " - " + e.getMessage());
                        }
                    }
                }
            }
        }
        
        schemaCreated = true;
        System.out.println("Schema created successfully (shared).");
    }

    public static void clearAllTables() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("DELETE FROM notes");
            stmt.execute("DELETE FROM tasks");
            stmt.execute("DELETE FROM events");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    public static void cleanup() {
        if (dockerProcess != null) {
            System.out.println("Stopping MySQL container (shared)...");
            try {
                ProcessBuilder pb = new ProcessBuilder(
                    "docker-compose", "down"
                );
                pb.directory(new File(System.getProperty("user.dir")));
                Process p = pb.start();
                p.waitFor();
            } catch (Exception e) {
                System.err.println("Error stopping Docker: " + e.getMessage());
            }
        }
        
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}