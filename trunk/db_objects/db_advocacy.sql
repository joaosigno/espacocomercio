SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `advocacy` ;
CREATE SCHEMA IF NOT EXISTS `advocacy` ;
USE `advocacy` ;

-- -----------------------------------------------------
-- Table `advocacy`.`advocacy_office`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `advocacy`.`advocacy_office` ;

CREATE  TABLE IF NOT EXISTS `advocacy`.`advocacy_office` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL ,
  `subtitle` VARCHAR(150) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `advocacy`.`finance_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `advocacy`.`finance_category` ;

CREATE  TABLE IF NOT EXISTS `advocacy`.`finance_category` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(45) NOT NULL ,
  `advocacy_office_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_finance_category_advocacy_office1` (`advocacy_office_id` ASC) ,
  CONSTRAINT `fk_finance_category_advocacy_office1`
    FOREIGN KEY (`advocacy_office_id` )
    REFERENCES `advocacy`.`advocacy_office` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `advocacy`.`advocacy_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `advocacy`.`advocacy_user` ;

CREATE  TABLE IF NOT EXISTS `advocacy`.`advocacy_user` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(100) NOT NULL ,
  `userpassword` VARCHAR(10) NOT NULL ,
  `manage_office` TINYINT(1) NOT NULL ,
  `manage_user` TINYINT(1) NOT NULL ,
  `manage_finance` TINYINT(1) NOT NULL ,
  `manage_client` TINYINT(1) NOT NULL ,
  `view_client` TINYINT(1) NOT NULL ,
  `manage_contract` TINYINT(1) NOT NULL ,
  `view_contract` TINYINT(1) NOT NULL ,
  `view_lawyer` TINYINT(1) NOT NULL ,
  `advocacy_office_id` INT NOT NULL ,
  `manage_lawyer` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_advocacy_user_advocacy_office` (`advocacy_office_id` ASC) ,
  CONSTRAINT `fk_advocacy_user_advocacy_office`
    FOREIGN KEY (`advocacy_office_id` )
    REFERENCES `advocacy`.`advocacy_office` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `advocacy`.`finance_expenses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `advocacy`.`finance_expenses` ;

CREATE  TABLE IF NOT EXISTS `advocacy`.`finance_expenses` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `finance_category_id` INT NOT NULL ,
  `title` VARCHAR(200) NOT NULL ,
  `date_expiration` DATE NOT NULL ,
  `date_payment` DATE NULL ,
  `description` VARCHAR(1000) NULL ,
  `value` DOUBLE NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_finance_expenses_finance_category1` (`finance_category_id` ASC) ,
  CONSTRAINT `fk_finance_expenses_finance_category1`
    FOREIGN KEY (`finance_category_id` )
    REFERENCES `advocacy`.`finance_category` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `advocacy`.`finance_revenue`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `advocacy`.`finance_revenue` ;

CREATE  TABLE IF NOT EXISTS `advocacy`.`finance_revenue` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `finance_category_id` INT NOT NULL ,
  `title` VARCHAR(200) NOT NULL ,
  `date_payment` DATE NOT NULL ,
  `description` VARCHAR(1000) NULL ,
  `value` DOUBLE NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_finance_revenue_finance_category1` (`finance_category_id` ASC) ,
  CONSTRAINT `fk_finance_revenue_finance_category1`
    FOREIGN KEY (`finance_category_id` )
    REFERENCES `advocacy`.`finance_category` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `advocacy`.`advocacy_client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `advocacy`.`advocacy_client` ;

CREATE  TABLE IF NOT EXISTS `advocacy`.`advocacy_client` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `email` VARCHAR(255) NOT NULL ,
  `rg` VARCHAR(100) NULL ,
  `cpf` VARCHAR(20) NULL ,
  `born` DATE NULL ,
  `phone` VARCHAR(20) NULL ,
  `cellphone` VARCHAR(20) NOT NULL ,
  `address_street` VARCHAR(100) NOT NULL ,
  `address_city` VARCHAR(100) NOT NULL ,
  `address_zipcode` VARCHAR(20) NOT NULL ,
  `address_number` VARCHAR(20) NOT NULL ,
  `address_complement` VARCHAR(100) NULL ,
  `advocacy_office_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_advocacy_client_advocacy_office1` (`advocacy_office_id` ASC) ,
  CONSTRAINT `fk_advocacy_client_advocacy_office1`
    FOREIGN KEY (`advocacy_office_id` )
    REFERENCES `advocacy`.`advocacy_office` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
