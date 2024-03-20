
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
DROP TABLE IF EXISTS `servicedomain_old`;

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
ALTER TABLE `cooperation`.`servicedomain` RENAME TO  `cooperation`.`servicedomain_old` ;

ALTER TABLE `cooperation`.`serviceproduction_new` RENAME TO  `cooperation`.`serviceproduction` ;
ALTER TABLE `cooperation`.`cooperation_new` RENAME TO  `cooperation`.`cooperation` ;
ALTER TABLE `cooperation`.`connectionpoint_new` RENAME TO  `cooperation`.`connectionpoint` ;
ALTER TABLE `cooperation`.`serviceconsumer_new` RENAME TO  `cooperation`.`serviceconsumer` ;
ALTER TABLE `cooperation`.`servicecontract_new` RENAME TO  `cooperation`.`servicecontract` ;
ALTER TABLE `cooperation`.`serviceproducer_new` RENAME TO  `cooperation`.`serviceproducer` ;
ALTER TABLE `cooperation`.`logicaladdress_new` RENAME TO  `cooperation`.`logicaladdress` ;
ALTER TABLE `cooperation`.`servicedomain_new` RENAME TO  `cooperation`.`servicedomain` ;



  