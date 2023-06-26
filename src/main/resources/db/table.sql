CREATE TABLE IF NOT EXISTS `stadium` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `team` (
     `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
     `stadium_id` INT UNSIGNED NOT NULL,
     `name` VARCHAR(20) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
    INDEX `fk_stadium_team_idx_idx` (`stadium_id` ASC) VISIBLE,
    CONSTRAINT `fk_stadium_team_idx`
    FOREIGN KEY (`stadium_id`)
    REFERENCES `stadium` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `player` (
       `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
       `team_id` INT UNSIGNED NOT NULL,
       `name` VARCHAR(20) NOT NULL,
        `position` VARCHAR(45) NOT NULL,
        `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

        UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
        INDEX `fk_team_player_idx` (`team_id` ASC) INVISIBLE,
        UNIQUE INDEX `uk_team_id_position_idx` (`team_id` ASC, `position` ASC) INVISIBLE,
        UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
        CONSTRAINT `fk_team_player_idx`
        FOREIGN KEY (`team_id`)
        REFERENCES `team` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    )
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `out_player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `out_player` (
   `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
   `player_id` INT UNSIGNED NOT NULL,
   `reason` VARCHAR(200) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_out_player_player_idx` (`player_id` ASC) VISIBLE,
    CONSTRAINT `fk_out_player_player`
    FOREIGN KEY (`player_id`)
    REFERENCES `player` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;