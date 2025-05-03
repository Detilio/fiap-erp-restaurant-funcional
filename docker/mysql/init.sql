-- Criação do banco
CREATE DATABASE IF NOT EXISTS `erp-restaurant` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Criação de usuário do banco de dados
CREATE USER IF NOT EXISTS 'user_erp_restaurant'@'%' IDENTIFIED WITH mysql_native_password BY '466238';
GRANT ALL PRIVILEGES ON `erp-restaurant`.* TO 'user_erp_restaurant'@'%';
FLUSH PRIVILEGES;

-- Seleciona o banco
USE `erp-restaurant`;

-- Criação da tabela
CREATE TABLE IF NOT EXISTS `users` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `date_generation` DATE,
  `date_change` DATE,
  `email` VARCHAR(100),
  `login` VARCHAR(50),
  `name` VARCHAR(100),
  `password` VARCHAR(100),
  `type` VARCHAR(20)
);

-- Inserção inicial
INSERT INTO `users`
(`id`, `date_generation`, `date_change`, `login`, `name`, `password`, `type`, `email`)
VALUES
(1, CURDATE(), CURDATE(), 'admin', 'administrador', 'admin', 'MASTER', '');
