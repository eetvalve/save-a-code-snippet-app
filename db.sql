--
-- Database: code_snippet_app
--

-- init db with this command:
-- $ mysql -u username -p password code_snippet_app < "C:\<pathToSQLfile>\db.sql"


CREATE TABLE IF NOT EXISTS users (
    user_id int AUTO_INCREMENT,
    user_name varchar(255) NOT NULL,
    private_snippets boolean,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS titles (
    title_id int AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    PRIMARY KEY (title_id)
);

CREATE TABLE IF NOT EXISTS title_owners (
    id int AUTO_INCREMENT,
    title_id int NOT NULL,
    owner int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (title_id) REFERENCES titles(title_id),
    FOREIGN KEY (owner) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS snippets (
    snippet_id int AUTO_INCREMENT,
    description varchar(255) NOT NULL,
    snippet varchar(255) NOT NULL,
    is_private_snippet boolean,
    title_id int NOT NULL,
    owner int NOT NULL,
    PRIMARY KEY (snippet_id),
    FOREIGN KEY (title_id) REFERENCES titles(title_id),
    FOREIGN KEY (owner) REFERENCES users(user_id)
);


INSERT INTO users (user_name, private_snippets) VALUES
('GLOBAL', false),
('edu', false),
('heikki', true);


INSERT INTO titles (title) VALUES
('IntelliJ'),
('Bash'),
('Word');


INSERT INTO title_owners (title_id, owner) VALUES
(1, 2),
(1, 3),
(1, 1),
(3, 3);


INSERT INTO snippets (description, snippet, is_private_snippet, title_id, owner) VALUES
('Text to uppercase: ', 'ctrl + shift + u', false, 1, 2),
('Search for keywords: ', 'ctrl + shift + a', false, 1, 2),
('heikin ainoa private command: ', 'oma command', true, 3, 3);
