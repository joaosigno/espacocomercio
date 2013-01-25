SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `ecommerce` ;
CREATE SCHEMA IF NOT EXISTS `ecommerce` DEFAULT CHARACTER SET latin1 ;
DROP SCHEMA IF EXISTS `advocacy` ;
CREATE SCHEMA IF NOT EXISTS `advocacy` ;
USE `ecommerce` ;

-- -----------------------------------------------------
-- Table `ecommerce`.`site`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ecommerce`.`site` ;

CREATE  TABLE IF NOT EXISTS `ecommerce`.`site` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ecommerce`.`product` ;

CREATE  TABLE IF NOT EXISTS `ecommerce`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `introduction` VARCHAR(255) NOT NULL ,
  `images` TEXT NOT NULL ,
  `description` LONGTEXT NULL DEFAULT NULL ,
  `quantity` DOUBLE NOT NULL ,
  `unityvalue` DOUBLE NOT NULL ,
  `datecreate` DATETIME NOT NULL ,
  `key_url` VARCHAR(45) NOT NULL ,
  `quantity_frete` DOUBLE NOT NULL ,
  `site_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_product_site1_idx` (`site_id` ASC) ,
  CONSTRAINT `fk_product_site1`
    FOREIGN KEY (`site_id` )
    REFERENCES `ecommerce`.`site` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`product_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ecommerce`.`product_category` ;

CREATE  TABLE IF NOT EXISTS `ecommerce`.`product_category` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(255) NULL DEFAULT NULL ,
  `key_url` VARCHAR(45) NOT NULL ,
  `site_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_product_category_site1_idx` (`site_id` ASC) ,
  CONSTRAINT `fk_product_category_site1`
    FOREIGN KEY (`site_id` )
    REFERENCES `ecommerce`.`site` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`category_has_product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ecommerce`.`category_has_product` ;

CREATE  TABLE IF NOT EXISTS `ecommerce`.`category_has_product` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `product_category_id` INT NOT NULL ,
  `product_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_product_category_has_product_product1_idx` (`product_id` ASC) ,
  INDEX `fk_product_category_has_product_product_category1_idx` (`product_category_id` ASC) ,
  CONSTRAINT `fk_product_category_has_product_product_category1`
    FOREIGN KEY (`product_category_id` )
    REFERENCES `ecommerce`.`product_category` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_category_has_product_product1`
    FOREIGN KEY (`product_id` )
    REFERENCES `ecommerce`.`product` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`client_ecommerce`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ecommerce`.`client_ecommerce` ;

CREATE  TABLE IF NOT EXISTS `ecommerce`.`client_ecommerce` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL ,
  `usermail` VARCHAR(255) NOT NULL ,
  `userpassword` VARCHAR(20) NOT NULL ,
  `newsletter` TINYINT NOT NULL ,
  `address_street` VARCHAR(255) NOT NULL ,
  `address_number` VARCHAR(45) NOT NULL ,
  `address_zipcode` VARCHAR(10) NOT NULL ,
  `address_city` VARCHAR(100) NOT NULL ,
  `address_complement` VARCHAR(255) NULL DEFAULT NULL ,
  `active` TINYINT NOT NULL ,
  `site_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_client_ecommerce_site1_idx` (`site_id` ASC) ,
  CONSTRAINT `fk_client_ecommerce_site1`
    FOREIGN KEY (`site_id` )
    REFERENCES `ecommerce`.`site` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`payment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ecommerce`.`payment` ;

CREATE  TABLE IF NOT EXISTS `ecommerce`.`payment` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(255) NULL DEFAULT NULL ,
  `url` VARCHAR(255) NULL DEFAULT NULL ,
  `site_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_payment_site1_idx` (`site_id` ASC) ,
  CONSTRAINT `fk_payment_site1`
    FOREIGN KEY (`site_id` )
    REFERENCES `ecommerce`.`site` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`order_client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ecommerce`.`order_client` ;

CREATE  TABLE IF NOT EXISTS `ecommerce`.`order_client` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `client_id` INT NOT NULL ,
  `payment_id` INT NOT NULL ,
  `totalvalue` DOUBLE NOT NULL ,
  `sendcust` DOUBLE NOT NULL ,
  `datecreate` DATETIME NOT NULL ,
  `datepayment` DATETIME NULL ,
  `status_order` INT NOT NULL ,
  `discount` DOUBLE NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_order_client1_idx` (`client_id` ASC) ,
  INDEX `fk_order_payment1_idx` (`payment_id` ASC) ,
  CONSTRAINT `fk_order_client1`
    FOREIGN KEY (`client_id` )
    REFERENCES `ecommerce`.`client_ecommerce` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_payment1`
    FOREIGN KEY (`payment_id` )
    REFERENCES `ecommerce`.`payment` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`product_has_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ecommerce`.`product_has_order` ;

CREATE  TABLE IF NOT EXISTS `ecommerce`.`product_has_order` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `product_id` INT NOT NULL ,
  `order_id` INT NOT NULL ,
  `quantity` INT NOT NULL ,
  `unityvalue` DOUBLE NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_product_has_order_order1_idx` (`order_id` ASC) ,
  INDEX `fk_product_has_order_product1_idx` (`product_id` ASC) ,
  CONSTRAINT `fk_product_has_order_product1`
    FOREIGN KEY (`product_id` )
    REFERENCES `ecommerce`.`product` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_order_order1`
    FOREIGN KEY (`order_id` )
    REFERENCES `ecommerce`.`order_client` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`frete_parameter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ecommerce`.`frete_parameter` ;

CREATE  TABLE IF NOT EXISTS `ecommerce`.`frete_parameter` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `state` VARCHAR(5) NOT NULL ,
  `value` DOUBLE NOT NULL ,
  `quantity_day` INT NOT NULL ,
  `site_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_frete_parameter_site1_idx` (`site_id` ASC) ,
  CONSTRAINT `fk_frete_parameter_site1`
    FOREIGN KEY (`site_id` )
    REFERENCES `ecommerce`.`site` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ecommerce`.`client_admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ecommerce`.`client_admin` ;

CREATE  TABLE IF NOT EXISTS `ecommerce`.`client_admin` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `usermail` VARCHAR(255) NOT NULL ,
  `userpassword` VARCHAR(10) NOT NULL ,
  `permission_type` INT NOT NULL ,
  `site_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_client_admin_site1` (`site_id` ASC) ,
  CONSTRAINT `fk_client_admin_site1`
    FOREIGN KEY (`site_id` )
    REFERENCES `ecommerce`.`site` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

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



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
