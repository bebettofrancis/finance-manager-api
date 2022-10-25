DROP TABLE IF EXISTS EXPENSE_CATEGORY;
CREATE TABLE EXPENSE_CATEGORY (
    ID INT PRIMARY KEY,
    NAME VARCHAR(255)
);
DROP TABLE IF EXISTS EXPENSE;
CREATE TABLE EXPENSE (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    CATEGORY_ID INT NOT NULL,
    COMMENT VARCHAR(255),
    FOREIGN KEY (CATEGORY_ID) REFERENCES EXPENSE_CATEGORY(ID)
);