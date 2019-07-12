--
-- Database: code_snippet_app
--

-- init db with this command:
-- $ mysql -u username -p password code_snippet_app < "C:\<pathToSQLfile>\db.sql"


CREATE TABLE IF NOT EXISTS titles (
    title_id int AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    PRIMARY KEY (title_id)
);

CREATE TABLE IF NOT EXISTS snippets (
    snippet_id int AUTO_INCREMENT,
    description varchar(255) NOT NULL,
    snippet varchar(255) NOT NULL,
    title_id int NOT NULL,
    PRIMARY KEY (snippet_id),
    FOREIGN KEY (title_id) REFERENCES titles(title_id)
);


INSERT INTO titles (title_id, title) VALUES
(NULL,  'IntelliJ'),
(NULL,  'Bash');


INSERT INTO snippets (description, snippet, title_id) VALUES
('Text to uppercase: ', 'ctrl + shift + u', 1),
('Search for keywords: ', 'ctrl + shift + a', 1);