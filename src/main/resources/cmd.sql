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
INSERT INTO `supplier` (`name`, `contact`, `email`, `address`, `description`) VALUES
    ('Tropical Foods Ltd.', '0771234567', 'info@tropicalfoodslk.com', 'Colombo 03, Sri Lanka', 'Supplier of tropical fruits and vegetables'),
    ('Oceanic Seafood Supplies', '0712345678', 'sales@oceanicseafoods.lk', 'Negombo, Sri Lanka', 'Specializes in fresh seafood supply'),
    ('Green Pastures Hay Co.', '0763456789', 'contact@greenpastures.lk', 'Kandy, Sri Lanka', 'Hay and grass supplier for herbivores'),
    ('Bug House Lanka', '0778765432', 'insects@bughouselk.com', 'Galle, Sri Lanka', 'Supplier of live insects and small animals'),
    ('Lion Meat Suppliers', '0789876543', 'order@lionmeatsupplies.lk', 'Kurunegala, Sri Lanka', 'High-quality meat for carnivorous animals'),
    ('Birdy Supplies', '0752345678', 'contact@birdysupplies.lk', 'Matara, Sri Lanka', 'Bird seeds and grains supplier'),
    ('Leaf & Bark Exports', '0728765432', 'info@leafandbark.lk', 'Nuwara Eliya, Sri Lanka', 'Supplies eucalyptus leaves and other specialty foliage');



CREATE TABLE IF NOT EXISTS `food` (
    `foodId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `supplierId` INT NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `minQTY` INT NOT NULL,
    `QtyOnHand` INT NOT NULL,
    FOREIGN KEY (`supplierId`) REFERENCES `supplier`(`supplierID`),
);

INSERT INTO `food` (`name`, `supplierId`, `price`, `minQTY`, `QtyOnHand`)
VALUES
    ('Bananas', 1, 1.50, 50, 100),
    ('Fish', 2, 2.75, 30, 80),
    ('Hay', 3, 0.60, 200, 500),
    ('Carrots', 1, 0.45, 100, 150),
    ('Insects', 4, 5.00, 20, 40),
    ('Meat', 5, 7.50, 25, 60),
    ('Fruits Mix', 1, 3.00, 75, 120),
    ('Vegetables Mix', 3, 2.25, 50, 90),
    ('Bird Seed', 6, 1.20, 40, 85),
    ('Eucalyptus Leaves', 7, 4.00, 15, 35);

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
        PRIMARY KEY (`ticketID`)
    );

INSERT INTO `ticket` (ticketType, price) VALUES
        ('Adult', 200.00),
        ('Child', 50.00),
        ('Student', 100.00),
        ('Foreign', 1000.00),
        ('ForeignChild', 500.00);

CREATE TABLE IF NOT EXISTS `visitor` (
        `visitorID` INT NOT NULL AUTO_INCREMENT,
        `total` DECIMAL(10, 2) NOT NULL,
        `child` int NOT NULL,
        `adult` int NOT NULL,
        `foreigner` int NOT NULL,
        `foreignerChild` int NOT NULL,
        `student` int NOT NULL,
        `date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`visitorID`)
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
