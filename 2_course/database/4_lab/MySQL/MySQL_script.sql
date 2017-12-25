SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema 4_lab
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema 4_lab
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `4_lab` DEFAULT CHARACTER SET utf8 ;
USE `4_lab` ;

-- -----------------------------------------------------
-- Table `4_lab`.`password`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `4_lab`.`password` (
  `idpassword` INT NOT NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`idpassword`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `4_lab`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `4_lab`.`user` (
  `login` INT NOT NULL,
  `surname` VARCHAR(45) NULL,
  `name` VARCHAR(45) NULL,
  `middle_name` VARCHAR(45) NULL,
  `birthday` DATE NULL,
  `place_birthday` VARCHAR(45) NULL,
  `rating` VARCHAR(45) NULL,
  `password_idpassword` INT NOT NULL,
  PRIMARY KEY (`login`, `password_idpassword`),
  INDEX `fk_user_password1_idx` (`password_idpassword` ASC),
  CONSTRAINT `fk_user_password1`
    FOREIGN KEY (`password_idpassword`)
    REFERENCES `4_lab`.`password` (`idpassword`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `4_lab`.`tree`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `4_lab`.`tree` (
  `idtree` INT NOT NULL,
  `category` VARCHAR(45) NULL,
  `tree_idtree` INT NOT NULL,
  PRIMARY KEY (`idtree`, `tree_idtree`),
  INDEX `fk_tree_tree1_idx` (`tree_idtree` ASC),
  CONSTRAINT `fk_tree_tree1`
    FOREIGN KEY (`tree_idtree`)
    REFERENCES `4_lab`.`tree` (`idtree`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `4_lab`.`books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `4_lab`.`books` (
  `idbooks` INT NOT NULL,
  `title` VARCHAR(45) NULL,
  `author` VARCHAR(45) NULL,
  `rating` VARCHAR(45) NULL,
  `tree_idtree` INT NOT NULL,
  PRIMARY KEY (`idbooks`, `tree_idtree`),
  INDEX `fk_books_tree_idx` (`tree_idtree` ASC),
  CONSTRAINT `fk_books_tree`
    FOREIGN KEY (`tree_idtree`)
    REFERENCES `4_lab`.`tree` (`idtree`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `4_lab`.`link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `4_lab`.`link` (
  `idlink` INT NOT NULL,
  `adress` VARCHAR(45) NULL,
  `books_idbooks` INT NOT NULL,
  `books_tree_idtree` INT NOT NULL,
  PRIMARY KEY (`idlink`, `books_idbooks`, `books_tree_idtree`),
  INDEX `fk_link_books1_idx` (`books_idbooks` ASC, `books_tree_idtree` ASC),
  CONSTRAINT `fk_link_books1`
    FOREIGN KEY (`books_idbooks` , `books_tree_idtree`)
    REFERENCES `4_lab`.`books` (`idbooks` , `tree_idtree`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `4_lab`.`user_has_books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `4_lab`.`user_has_books` (
  `user_login` INT NOT NULL,
  `books_idbooks` INT NOT NULL,
  `books_tree_idtree` INT NOT NULL,
  PRIMARY KEY (`user_login`, `books_idbooks`, `books_tree_idtree`),
  INDEX `fk_user_has_books_books1_idx` (`books_idbooks` ASC, `books_tree_idtree` ASC),
  INDEX `fk_user_has_books_user1_idx` (`user_login` ASC),
  CONSTRAINT `fk_user_has_books_user1`
    FOREIGN KEY (`user_login`)
    REFERENCES `4_lab`.`user` (`login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_books_books1`
    FOREIGN KEY (`books_idbooks` , `books_tree_idtree`)
    REFERENCES `4_lab`.`books` (`idbooks` , `tree_idtree`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
