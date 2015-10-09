
USE cooperation;

--
-- Drop old tables
--
DROP TABLE IF EXISTS `serviceproduction_old`;
DROP TABLE IF EXISTS `cooperation_old`;
DROP TABLE IF EXISTS `connectionpoint_old`;
DROP TABLE IF EXISTS `serviceconsumer_old`;
DROP TABLE IF EXISTS `servicecontract_old`;
DROP TABLE IF EXISTS `serviceproducer_old`;
DROP TABLE IF EXISTS `logicaladdress_old`;



--
-- Rename to old
--
ALTER TABLE `cooperation`.`serviceproduction` RENAME TO  `cooperation`.`serviceproduction_old` ;
ALTER TABLE `cooperation`.`cooperation` RENAME TO  `cooperation`.`cooperation_old` ;
ALTER TABLE `cooperation`.`connectionpoint` RENAME TO  `cooperation`.`connectionpoint_old` ;
ALTER TABLE `cooperation`.`serviceconsumer` RENAME TO  `cooperation`.`serviceconsumer_old` ;
ALTER TABLE `cooperation`.`servicecontract` RENAME TO  `cooperation`.`servicecontract_old` ;
ALTER TABLE `cooperation`.`serviceproducer` RENAME TO  `cooperation`.`serviceproducer_old` ;
ALTER TABLE `cooperation`.`logicaladdress` RENAME TO  `cooperation`.`logicaladdress_old` ;


--
-- remove contstraints old tables, otherwise create new tabls will fail
--

ALTER TABLE `cooperation`.`cooperation_old` DROP FOREIGN KEY `FK_cooperation_1`;
ALTER TABLE `cooperation`.`cooperation_old` DROP FOREIGN KEY `FK_cooperation_2`;
ALTER TABLE `cooperation`.`cooperation_old` DROP FOREIGN KEY `FK_cooperation_3`;
ALTER TABLE `cooperation`.`cooperation_old` DROP FOREIGN KEY `FK_cooperation_4`;
ALTER TABLE `cooperation`.`cooperation_old` DROP INDEX `IX_cooperation_1`;
ALTER TABLE `cooperation`.`cooperation_old` DROP INDEX `IX_cooperation_2`;
ALTER TABLE `cooperation`.`cooperation_old` DROP INDEX `IX_cooperation_3`;
ALTER TABLE `cooperation`.`cooperation_old` DROP INDEX `IX_cooperation_4`;

ALTER TABLE `cooperation`.`serviceproduction_old` DROP FOREIGN KEY `FK_serviceproduction_1`;
ALTER TABLE `cooperation`.`serviceproduction_old` DROP FOREIGN KEY `FK_serviceproduction_2`;
ALTER TABLE `cooperation`.`serviceproduction_old` DROP FOREIGN KEY `FK_serviceproduction_3`;
ALTER TABLE `cooperation`.`serviceproduction_old` DROP FOREIGN KEY `FK_serviceproduction_4`;
ALTER TABLE `cooperation`.`serviceproduction_old` DROP INDEX `IX_serviceproduction_1`;
ALTER TABLE `cooperation`.`serviceproduction_old` DROP INDEX `IX_serviceproduction_2`;
ALTER TABLE `cooperation`.`serviceproduction_old` DROP INDEX `IX_serviceproduction_3`;
ALTER TABLE `cooperation`.`serviceproduction_old` DROP INDEX `IX_serviceproduction_4`;
  