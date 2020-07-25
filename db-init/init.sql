CREATE DATABASE IF NOT EXISTS assembly;

CREATE TABLE IF NOT EXISTS agenda (
        id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
        description varchar(350) NOT NULL
);

CREATE TABLE IF NOT EXISTS vote (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  agenda_id int NOT NULL,
  vote varchar(3) NOT NULL,
  associated_id int NOT NULL,
  associated_cpf varchar(11) NOT NULL,
  FOREIGN KEY (agenda_id) REFERENCES agenda(id),
  UNIQUE (agenda_id, associated_id)
);

CREATE TABLE IF NOT EXISTS `session` (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  agenda_id int NOT NULL,
  due_date datetime NOT NULL,
  status varchar(10) NOT NULL,
  FOREIGN KEY (agenda_id) REFERENCES agenda(id)
);
