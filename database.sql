
CREATE DATABASE IF NOT EXISTS campusconnect;

USE campusconnect;

CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    course VARCHAR(100),
    semester INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO students (name, email, phone, course, semester)
VALUES
('Aarav Sharma', 'aarav@example.com', '9876543210', 'B.Tech CSE', 1),
('Priya Verma', 'priya@example.com', '9876543211', 'B.Tech AI & DS', 1),
('Rahul Singh', 'rahul@example.com', '9876543212', 'B.Tech IT', 2);
