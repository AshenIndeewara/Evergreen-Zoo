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
        `hire_date` DEFAULT CURRENT_DATE,
        `userId` INT NOT NULL,
        FOREIGN KEY (`userId`) REFERENCES `users`(`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS `Role` (
        `role_id` INT PRIMARY KEY AUTO_INCREMENT,
        `role_name` VARCHAR(50) NOT NULL UNIQUE,
        `description` TEXT
    );

INSERT INTO `Role` (role_name, description) VALUES
        ('Animal Care Specialist', 'Veterinary staff responsible for animal health and care'),
        ('Management', 'Overall zoo management and administration'),
        ('Maintenance', 'Facility maintenance and grounds keeping'),
        ('Ticket Staff', 'Handles ticket sales and visitor entry');

CREATE TABLE IF NOT EXISTS `species` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(255) NOT NULL,
        `ConservationStatus` VARCHAR(255) NOT NULL,
        PRIMARY KEY (`id`)
    );
CREATE TABLE IF NOT EXISTS `animal` (
        `animalId` INT NOT NULL AUTO_INCREMENT,
        `nickName` VARCHAR(255) NOT NULL,
        `speciesId` INT NOT NULL,
        `gender` VARCHAR(255) NOT NULL,
        `arrivalDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`animalId`),
        FOREIGN KEY (`speciesId`) REFERENCES `species`(`id`)
    );

CREATE TABLE IF NOT EXISTS `feedingSchedule` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `speciesId` INT NOT NULL,
    `feedingTime` TIME NOT NULL,
    FOREIGN KEY (`speciesId`) REFERENCES `species`(`id`)
    );

CREATE TABLE IF NOT EXISTS `feedingDetails` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `scheduleId` INT NOT NULL,
    `employeeId` INT NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `qty` INT NOT NULL,
    `date` DATE NOT NULL,
    FOREIGN KEY (`scheduleId`) REFERENCES `feedingSchedule`(`id`),
    FOREIGN KEY (`employeeId`) REFERENCES `employee`(`id`)
    );

CREATE TABLE IF NOT EXISTS `supplier` (
        `supplierID` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(255) NOT NULL,
        `contact` VARCHAR(255) NOT NULL,
        `email` VARCHAR(255) NOT NULL,
        `address` VARCHAR(255) NOT NULL,
        `description` VARCHAR(255) NOT NULL,
        PRIMARY KEY (`supplierID`)
    );

CREATE TABLE IF NOT EXISTS `food` (
    `foodId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `supplierId` INT NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `minQTY` INT NOT NULL,
    `QtyOnHand` INT NOT NULL,
    FOREIGN KEY (`supplierId`) REFERENCES `supplier`(`supplierID`),
);
CREATE TABLE IF NOT EXISTS `healthRecords` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `Description` VARCHAR(255) NOT NULL,
        `date` DATE NOT NULL,
        `employeeId` INT NOT NULL,
        `animalId` INT NOT NULL,
        PRIMARY KEY (`id`),
        FOREIGN KEY (`employeeId`) REFERENCES `employee`(`id`),
        FOREIGN KEY (`animalId`) REFERENCES `animal`(`animalId`)
);

CREATE TABLE IF NOT EXISTS `ticket` (
        `ticketID` INT NOT NULL AUTO_INCREMENT,
        `price` DECIMAL(10, 2) NOT NULL,
        `ticketType` VARCHAR(255) NOT NULL,
        `price` DECIMAL(10, 2) NOT NULL,
        `employeeId` INT NOT NULL,
        PRIMARY KEY (`ticketID`)
        FOREIGN KEY (`employeeId`) REFERENCES `employee`(`id`)
    );

CREATE TABLE IF NOT EXISTS `visitor` (
        `visitorID` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(255) NOT NULL,
        `age` VARCHAR(255) NOT NULL,
        `contact` VARCHAR(255),
        `ticketId` INT NOT NULL,
        `date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`visitorID`),
        FOREIGN KEY (`ticketId`) REFERENCES `ticket`(`ticketID`)
    );

CREATE TABLE IF NOT EXISTS `eventPrograms` (
        `eventID` INT NOT NULL AUTO_INCREMENT,
        `eventName` VARCHAR(255) NOT NULL,
        `description` VARCHAR(255) NOT NULL,
        PRIMARY KEY (`eventID`)
    );

CREATE TABLE IF NOT EXISTS `eventDetails` (
        `eventId` INT NOT NULL AUTO_INCREMENT,
        `animalID` INT NOT NULL,
        `eventID` INT NOT NULL,
        `startTime` TIME NOT NULL,
        `endTime` TIME NOT NULL,
        `startDate` DATE NOT NULL,
        `endDate` DATE NOT NULL,
        PRIMARY KEY (`id`),
        FOREIGN KEY (`animalID`) REFERENCES `species`(`id`),
        FOREIGN KEY (`eventID`) REFERENCES `eventPrograms`(`eventID`)
    );
CREATE TABLE IF NOT EXISTS `breedingPrograms` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `programName` VARCHAR(255) NOT NULL,
        `speciesId` INT NOT NULL,
        PRIMARY KEY (`id`),
        FOREIGN KEY (`speciesId`) REFERENCES `species`(`id` )
        ON DELETE CASCADE
        ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS `breedingDetails` (
        `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `employeeId` INT NOT NULL,
        `breedingId` INT NOT NULL,
        `startDate` DATE NOT NULL,
        FOREIGN KEY (`employeeId`) REFERENCES `employee`(`id`),
        FOREIGN KEY (`breedingId`) REFERENCES `breedingPrograms`(`id`)
    );


CREATE TABLE IF NOT EXISTS `employeeDetails` (
    `EMPLOYEE_ID` INT,
    `eventID` INT,
    FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `employee`(`id`),
    FOREIGN KEY (`eventID`) REFERENCES `eventPrograms`(`eventID`)
)
