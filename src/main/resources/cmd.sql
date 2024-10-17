CREATE DATABASE IF NOT EXISTS zoo;

USE zoo;

CREATE TABLE IF NOT EXISTS `users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `employee` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `position` VARCHAR(255) NOT NULL,
    `hire_date` DATE NOT NULL,
    `userId` INT NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES `users`(`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE Role (
      role_id INT PRIMARY KEY AUTO_INCREMENT,
      role_name VARCHAR(50) NOT NULL UNIQUE,
      description TEXT
);

INSERT INTO Role (role_name, description) VALUES
      ('Animal Care Specialist', 'Veterinary staff responsible for animal health and care'),
      ('Management', 'Overall zoo management and administration'),
      ('Maintenance', 'Facility maintenance and grounds keeping'),
      ('Ticket Staff', 'Handles ticket sales and visitor entry');
76uy56y765y

CREATE TABLE IF NOT EXISTS `species` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `ConservationStatus` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
    );


CREATE TABLE IF NOT EXISTS `healthRecords` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `Description` VARCHAR(255) NOT NULL,
    `date` DATE NOT NULL,
    `employeeId` INT NOT NULL,
    `speciesId` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`employeeId`) REFERENCES `employee`(`id`),
    FOREIGN KEY (`speciesId`) REFERENCES `species`(`id`)
    );

Create Table IF NOT EXISTS 'ticket'(
    'ticketID' INT NOT NULL AUTO_INCREMENT,
    'price' DOUBLE NOT NULL,
    'type' VARCHAR(255) NOT NULL,
    'Date' DATE NOT NULL,
    'time' TIME NOT NULL,
)
CREATE TABLE IF NOT EXISTS 'visitor'(
    'visitorID' INT NOT NULL AUTO_INCREMENT,
    'name' VARCHAR(255) NOT NULL,
    'age' VARCHAR(255) NOT NULL,
    'contact' VARCHAR(255) NOT NULL,
    'ticketId' INT NOT NULL,
    FOREIGN KEY ('ticketId') REFERENCES 'ticket'('id')
)
CREATE TABLE IF NOT EXISTS 'eventPrograms'(
    'eventID' INT NOT NULL AUTO_INCREMENT,
    'eventName' VARCHAR(255) NOT NULL,
    'discription' VARCHAR(255) NOT NULL,
)
CREATE TABLE IF NOT EXISTS 'eventDetails'(
    'id' INT NOT NULL AUTO_INCREMENT,
    'animalID' INT NOT NULL,
    'eventID' INT NOT NULL,
    'startTime' TIME NOT NULL,
    'endTime' TIME NOT NULL,
    'startDate' DATE NOT NULL,
    'endDate' DATE NOT NULL,
    FOREIGN KEY ('animalID') REFERENCES 'species'('id'),
    FOREIGN KEY ('eventID') REFERENCES 'eventPrograms'('id')
)
CREATE TABLE IF NOT EXISTS 'employeeDetails'(
    'id' INT NOT NULL AUTO_INCREMENT,
    
)