-- Banco de dados da atividade de Sockets (Aula 12)
-- Execute uma vez. Se ja existir, os dados serao preservados.
CREATE DATABASE IF NOT EXISTS cadastro_pessoas
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE cadastro_pessoas;

CREATE TABLE IF NOT EXISTS pessoa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sexo VARCHAR(20),
    idioma VARCHAR(40),
    CONSTRAINT chk_nome_nao_vazio CHECK (CHAR_LENGTH(TRIM(nome)) > 0)
);
