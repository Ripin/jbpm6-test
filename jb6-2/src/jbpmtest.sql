/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50611
Source Host           : localhost:3306
Source Database       : jbpmtest

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2014-06-04 20:33:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for abstractaudittaskimpl
-- ----------------------------
DROP TABLE IF EXISTS `abstractaudittaskimpl`;
CREATE TABLE `abstractaudittaskimpl` (
  `DTYPE` varchar(31) NOT NULL,
  `TASK_ID` bigint(20) NOT NULL,
  `activationTime` date DEFAULT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `createdOn` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parentId` bigint(20) NOT NULL,
  `priority` int(11) NOT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `processSessionId` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `potentialOwners` varchar(255) DEFAULT NULL,
  `actualOwner` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accessType` int(11) DEFAULT NULL,
  `attachedAt` datetime DEFAULT NULL,
  `attachmentContentId` bigint(20) NOT NULL,
  `contentType` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `attachment_size` int(11) DEFAULT NULL,
  `attachedBy_id` varchar(255) DEFAULT NULL,
  `TaskData_Attachments_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7ndpfa311i50bq7hy18q05va3` (`attachedBy_id`),
  KEY `FK_hqupx569krp0f0sgu9kh87513` (`TaskData_Attachments_Id`),
  CONSTRAINT `FK_hqupx569krp0f0sgu9kh87513` FOREIGN KEY (`TaskData_Attachments_Id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_7ndpfa311i50bq7hy18q05va3` FOREIGN KEY (`attachedBy_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bamtasksummary
-- ----------------------------
DROP TABLE IF EXISTS `bamtasksummary`;
CREATE TABLE `bamtasksummary` (
  `pk` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `startDate` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `taskId` bigint(20) NOT NULL,
  `taskName` varchar(255) DEFAULT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for booleanexpression
-- ----------------------------
DROP TABLE IF EXISTS `booleanexpression`;
CREATE TABLE `booleanexpression` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expression` longtext,
  `type` varchar(255) DEFAULT NULL,
  `Escalation_Constraints_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_394nf2qoc0k9ok6omgd6jtpso` (`Escalation_Constraints_Id`),
  CONSTRAINT `FK_394nf2qoc0k9ok6omgd6jtpso` FOREIGN KEY (`Escalation_Constraints_Id`) REFERENCES `escalation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for contextmappinginfo
-- ----------------------------
DROP TABLE IF EXISTS `contextmappinginfo`;
CREATE TABLE `contextmappinginfo` (
  `mappingId` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONTEXT_ID` varchar(255) NOT NULL,
  `KSESSION_ID` int(11) NOT NULL,
  `OWNER_ID` varchar(255) DEFAULT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`mappingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for correlationkeyinfo
-- ----------------------------
DROP TABLE IF EXISTS `correlationkeyinfo`;
CREATE TABLE `correlationkeyinfo` (
  `keyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`keyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for correlationpropertyinfo
-- ----------------------------
DROP TABLE IF EXISTS `correlationpropertyinfo`;
CREATE TABLE `correlationpropertyinfo` (
  `propertyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  `correlationKey_keyId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`propertyId`),
  KEY `FK_hrmx1m882cejwj9c04ixh50i4` (`correlationKey_keyId`),
  CONSTRAINT `FK_hrmx1m882cejwj9c04ixh50i4` FOREIGN KEY (`correlationKey_keyId`) REFERENCES `correlationkeyinfo` (`keyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for deadline
-- ----------------------------
DROP TABLE IF EXISTS `deadline`;
CREATE TABLE `deadline` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deadline_date` datetime DEFAULT NULL,
  `escalated` smallint(6) DEFAULT NULL,
  `Deadlines_StartDeadLine_Id` bigint(20) DEFAULT NULL,
  `Deadlines_EndDeadLine_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_68w742sge00vco2cq3jhbvmgx` (`Deadlines_StartDeadLine_Id`),
  KEY `FK_euoohoelbqvv94d8a8rcg8s5n` (`Deadlines_EndDeadLine_Id`),
  CONSTRAINT `FK_euoohoelbqvv94d8a8rcg8s5n` FOREIGN KEY (`Deadlines_EndDeadLine_Id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_68w742sge00vco2cq3jhbvmgx` FOREIGN KEY (`Deadlines_StartDeadLine_Id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for delegation_delegates
-- ----------------------------
DROP TABLE IF EXISTS `delegation_delegates`;
CREATE TABLE `delegation_delegates` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK_gn7ula51sk55wj1o1m57guqxb` (`entity_id`),
  KEY `FK_fajq6kossbsqwr3opkrctxei3` (`task_id`),
  CONSTRAINT `FK_fajq6kossbsqwr3opkrctxei3` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_gn7ula51sk55wj1o1m57guqxb` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for email_header
-- ----------------------------
DROP TABLE IF EXISTS `email_header`;
CREATE TABLE `email_header` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `body` longtext,
  `fromAddress` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `replyToAddress` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for escalation
-- ----------------------------
DROP TABLE IF EXISTS `escalation`;
CREATE TABLE `escalation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `Deadline_Escalation_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ay2gd4fvl9yaapviyxudwuvfg` (`Deadline_Escalation_Id`),
  CONSTRAINT `FK_ay2gd4fvl9yaapviyxudwuvfg` FOREIGN KEY (`Deadline_Escalation_Id`) REFERENCES `deadline` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eventtypes
-- ----------------------------
DROP TABLE IF EXISTS `eventtypes`;
CREATE TABLE `eventtypes` (
  `InstanceId` bigint(20) NOT NULL,
  `element` varchar(255) DEFAULT NULL,
  KEY `FK_nrecj4617iwxlc65ij6m7lsl1` (`InstanceId`),
  CONSTRAINT `FK_nrecj4617iwxlc65ij6m7lsl1` FOREIGN KEY (`InstanceId`) REFERENCES `processinstanceinfo` (`InstanceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for i18ntext
-- ----------------------------
DROP TABLE IF EXISTS `i18ntext`;
CREATE TABLE `i18ntext` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `shortText` varchar(255) DEFAULT NULL,
  `text` longtext,
  `Notification_Subjects_Id` bigint(20) DEFAULT NULL,
  `Notification_Names_Id` bigint(20) DEFAULT NULL,
  `Notification_Documentation_Id` bigint(20) DEFAULT NULL,
  `Notification_Descriptions_Id` bigint(20) DEFAULT NULL,
  `Reassignment_Documentation_Id` bigint(20) DEFAULT NULL,
  `Task_Subjects_Id` bigint(20) DEFAULT NULL,
  `Task_Names_Id` bigint(20) DEFAULT NULL,
  `Task_Descriptions_Id` bigint(20) DEFAULT NULL,
  `Deadline_Documentation_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_o84rkh69r47ti8uv4eyj7bmo2` (`Notification_Subjects_Id`),
  KEY `FK_g1trxri8w64enudw2t1qahhk5` (`Notification_Names_Id`),
  KEY `FK_qoce92c70adem3ccb3i7lec8x` (`Notification_Documentation_Id`),
  KEY `FK_bw8vmpekejxt1ei2ge26gdsry` (`Notification_Descriptions_Id`),
  KEY `FK_pqarjvvnwfjpeyb87yd7m0bfi` (`Reassignment_Documentation_Id`),
  KEY `FK_k16jpgrh67ti9uedf6konsu1p` (`Task_Subjects_Id`),
  KEY `FK_fd9uk6hemv2dx1ojovo7ms3vp` (`Task_Names_Id`),
  KEY `FK_4eyfp69ucrron2hr7qx4np2fp` (`Task_Descriptions_Id`),
  KEY `FK_21qvifarxsvuxeaw5sxwh473w` (`Deadline_Documentation_Id`),
  CONSTRAINT `FK_21qvifarxsvuxeaw5sxwh473w` FOREIGN KEY (`Deadline_Documentation_Id`) REFERENCES `deadline` (`id`),
  CONSTRAINT `FK_4eyfp69ucrron2hr7qx4np2fp` FOREIGN KEY (`Task_Descriptions_Id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_bw8vmpekejxt1ei2ge26gdsry` FOREIGN KEY (`Notification_Descriptions_Id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK_fd9uk6hemv2dx1ojovo7ms3vp` FOREIGN KEY (`Task_Names_Id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_g1trxri8w64enudw2t1qahhk5` FOREIGN KEY (`Notification_Names_Id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK_k16jpgrh67ti9uedf6konsu1p` FOREIGN KEY (`Task_Subjects_Id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_o84rkh69r47ti8uv4eyj7bmo2` FOREIGN KEY (`Notification_Subjects_Id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK_pqarjvvnwfjpeyb87yd7m0bfi` FOREIGN KEY (`Reassignment_Documentation_Id`) REFERENCES `reassignment` (`id`),
  CONSTRAINT `FK_qoce92c70adem3ccb3i7lec8x` FOREIGN KEY (`Notification_Documentation_Id`) REFERENCES `notification` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for nodeinstancelog
-- ----------------------------
DROP TABLE IF EXISTS `nodeinstancelog`;
CREATE TABLE `nodeinstancelog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `connection` varchar(255) DEFAULT NULL,
  `log_date` datetime DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `nodeId` varchar(255) DEFAULT NULL,
  `nodeInstanceId` varchar(255) DEFAULT NULL,
  `nodeName` varchar(255) DEFAULT NULL,
  `nodeType` varchar(255) DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `workItemId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `DTYPE` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `priority` int(11) NOT NULL,
  `Escalation_Notifications_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bdbeml3768go5im41cgfpyso9` (`Escalation_Notifications_Id`),
  CONSTRAINT `FK_bdbeml3768go5im41cgfpyso9` FOREIGN KEY (`Escalation_Notifications_Id`) REFERENCES `escalation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for notification_bas
-- ----------------------------
DROP TABLE IF EXISTS `notification_bas`;
CREATE TABLE `notification_bas` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK_mfbsnbrhth4rjhqc2ud338s4i` (`entity_id`),
  KEY `FK_fc0uuy76t2bvxaxqysoo8xts7` (`task_id`),
  CONSTRAINT `FK_fc0uuy76t2bvxaxqysoo8xts7` FOREIGN KEY (`task_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK_mfbsnbrhth4rjhqc2ud338s4i` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for notification_email_header
-- ----------------------------
DROP TABLE IF EXISTS `notification_email_header`;
CREATE TABLE `notification_email_header` (
  `Notification_id` bigint(20) NOT NULL,
  `emailHeaders_id` bigint(20) NOT NULL,
  `mapkey` varchar(255) NOT NULL,
  PRIMARY KEY (`Notification_id`,`mapkey`),
  UNIQUE KEY `UK_ptaka5kost68h7l3wflv7w6y8` (`emailHeaders_id`),
  CONSTRAINT `FK_eth4nvxn21fk1vnju85vkjrai` FOREIGN KEY (`Notification_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK_ptaka5kost68h7l3wflv7w6y8` FOREIGN KEY (`emailHeaders_id`) REFERENCES `email_header` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for notification_recipients
-- ----------------------------
DROP TABLE IF EXISTS `notification_recipients`;
CREATE TABLE `notification_recipients` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK_blf9jsrumtrthdaqnpwxt25eu` (`entity_id`),
  KEY `FK_3l244pj8sh78vtn9imaymrg47` (`task_id`),
  CONSTRAINT `FK_3l244pj8sh78vtn9imaymrg47` FOREIGN KEY (`task_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK_blf9jsrumtrthdaqnpwxt25eu` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for organizationalentity
-- ----------------------------
DROP TABLE IF EXISTS `organizationalentity`;
CREATE TABLE `organizationalentity` (
  `DTYPE` varchar(31) NOT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for peopleassignments_bas
-- ----------------------------
DROP TABLE IF EXISTS `peopleassignments_bas`;
CREATE TABLE `peopleassignments_bas` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK_t38xbkrq6cppifnxequhvjsl2` (`entity_id`),
  KEY `FK_omjg5qh7uv8e9bolbaq7hv6oh` (`task_id`),
  CONSTRAINT `FK_omjg5qh7uv8e9bolbaq7hv6oh` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_t38xbkrq6cppifnxequhvjsl2` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for peopleassignments_exclowners
-- ----------------------------
DROP TABLE IF EXISTS `peopleassignments_exclowners`;
CREATE TABLE `peopleassignments_exclowners` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK_pth28a73rj6bxtlfc69kmqo0a` (`entity_id`),
  KEY `FK_b8owuxfrdng050ugpk0pdowa7` (`task_id`),
  CONSTRAINT `FK_b8owuxfrdng050ugpk0pdowa7` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_pth28a73rj6bxtlfc69kmqo0a` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for peopleassignments_potowners
-- ----------------------------
DROP TABLE IF EXISTS `peopleassignments_potowners`;
CREATE TABLE `peopleassignments_potowners` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK_tee3ftir7xs6eo3fdvi3xw026` (`entity_id`),
  KEY `FK_4dv2oji7pr35ru0w45trix02x` (`task_id`),
  CONSTRAINT `FK_4dv2oji7pr35ru0w45trix02x` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_tee3ftir7xs6eo3fdvi3xw026` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for peopleassignments_recipients
-- ----------------------------
DROP TABLE IF EXISTS `peopleassignments_recipients`;
CREATE TABLE `peopleassignments_recipients` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK_4g7y3wx6gnokf6vycgpxs83d6` (`entity_id`),
  KEY `FK_enhk831fghf6akjilfn58okl4` (`task_id`),
  CONSTRAINT `FK_enhk831fghf6akjilfn58okl4` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_4g7y3wx6gnokf6vycgpxs83d6` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for peopleassignments_stakeholders
-- ----------------------------
DROP TABLE IF EXISTS `peopleassignments_stakeholders`;
CREATE TABLE `peopleassignments_stakeholders` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK_met63inaep6cq4ofb3nnxi4tm` (`entity_id`),
  KEY `FK_4bh3ay74x6ql9usunubttfdf1` (`task_id`),
  CONSTRAINT `FK_4bh3ay74x6ql9usunubttfdf1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_met63inaep6cq4ofb3nnxi4tm` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for processinstanceinfo
-- ----------------------------
DROP TABLE IF EXISTS `processinstanceinfo`;
CREATE TABLE `processinstanceinfo` (
  `InstanceId` bigint(20) NOT NULL AUTO_INCREMENT,
  `lastModificationDate` datetime DEFAULT NULL,
  `lastReadDate` datetime DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `processInstanceByteArray` longblob,
  `startDate` datetime DEFAULT NULL,
  `state` int(11) NOT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`InstanceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for processinstancelog
-- ----------------------------
DROP TABLE IF EXISTS `processinstancelog`;
CREATE TABLE `processinstancelog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `duration` bigint(20) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `user_identity` varchar(255) DEFAULT NULL,
  `outcome` varchar(255) DEFAULT NULL,
  `parentProcessInstanceId` bigint(20) DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `processName` varchar(255) DEFAULT NULL,
  `processVersion` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reassignment
-- ----------------------------
DROP TABLE IF EXISTS `reassignment`;
CREATE TABLE `reassignment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Escalation_Reassignments_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pnpeue9hs6kx2ep0sp16b6kfd` (`Escalation_Reassignments_Id`),
  CONSTRAINT `FK_pnpeue9hs6kx2ep0sp16b6kfd` FOREIGN KEY (`Escalation_Reassignments_Id`) REFERENCES `escalation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reassignment_potentialowners
-- ----------------------------
DROP TABLE IF EXISTS `reassignment_potentialowners`;
CREATE TABLE `reassignment_potentialowners` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  KEY `FK_8frl6la7tgparlnukhp8xmody` (`entity_id`),
  KEY `FK_qbega5ncu6b9yigwlw55aeijn` (`task_id`),
  CONSTRAINT `FK_qbega5ncu6b9yigwlw55aeijn` FOREIGN KEY (`task_id`) REFERENCES `reassignment` (`id`),
  CONSTRAINT `FK_8frl6la7tgparlnukhp8xmody` FOREIGN KEY (`entity_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sessioninfo
-- ----------------------------
DROP TABLE IF EXISTS `sessioninfo`;
CREATE TABLE `sessioninfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lastModificationDate` datetime DEFAULT NULL,
  `rulesByteArray` longblob,
  `startDate` datetime DEFAULT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `archived` smallint(6) DEFAULT NULL,
  `allowedToDelegate` varchar(255) DEFAULT NULL,
  `formName` varchar(255) DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `subTaskStrategy` varchar(255) DEFAULT NULL,
  `activationTime` datetime DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `deploymentId` varchar(255) DEFAULT NULL,
  `documentAccessType` int(11) DEFAULT NULL,
  `documentContentId` bigint(20) NOT NULL,
  `documentType` varchar(255) DEFAULT NULL,
  `expirationTime` datetime DEFAULT NULL,
  `faultAccessType` int(11) DEFAULT NULL,
  `faultContentId` bigint(20) NOT NULL,
  `faultName` varchar(255) DEFAULT NULL,
  `faultType` varchar(255) DEFAULT NULL,
  `outputAccessType` int(11) DEFAULT NULL,
  `outputContentId` bigint(20) NOT NULL,
  `outputType` varchar(255) DEFAULT NULL,
  `parentId` bigint(20) NOT NULL,
  `previousStatus` int(11) DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `processSessionId` int(11) NOT NULL,
  `skipable` bit(1) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `workItemId` bigint(20) NOT NULL,
  `taskType` varchar(255) DEFAULT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  `taskInitiator_id` varchar(255) DEFAULT NULL,
  `actualOwner_id` varchar(255) DEFAULT NULL,
  `createdBy_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dpk0f9ucm14c78bsxthh7h8yh` (`taskInitiator_id`),
  KEY `FK_nh9nnt47f3l61qjlyedqt05rf` (`actualOwner_id`),
  KEY `FK_k02og0u71obf1uxgcdjx9rcjc` (`createdBy_id`),
  CONSTRAINT `FK_k02og0u71obf1uxgcdjx9rcjc` FOREIGN KEY (`createdBy_id`) REFERENCES `organizationalentity` (`id`),
  CONSTRAINT `FK_dpk0f9ucm14c78bsxthh7h8yh` FOREIGN KEY (`taskInitiator_id`) REFERENCES `organizationalentity` (`id`),
  CONSTRAINT `FK_nh9nnt47f3l61qjlyedqt05rf` FOREIGN KEY (`actualOwner_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for taskevent
-- ----------------------------
DROP TABLE IF EXISTS `taskevent`;
CREATE TABLE `taskevent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `logTime` datetime DEFAULT NULL,
  `taskId` bigint(20) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for task_comment
-- ----------------------------
DROP TABLE IF EXISTS `task_comment`;
CREATE TABLE `task_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addedAt` datetime DEFAULT NULL,
  `text` longtext,
  `addedBy_id` varchar(255) DEFAULT NULL,
  `TaskData_Comments_Id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_aax378yjnsmw9kb9vsu994jjv` (`addedBy_id`),
  KEY `FK_1ws9jdmhtey6mxu7jb0r0ufvs` (`TaskData_Comments_Id`),
  CONSTRAINT `FK_1ws9jdmhtey6mxu7jb0r0ufvs` FOREIGN KEY (`TaskData_Comments_Id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_aax378yjnsmw9kb9vsu994jjv` FOREIGN KEY (`addedBy_id`) REFERENCES `organizationalentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userJbpmGroupId` varchar(255) DEFAULT NULL,
  `userJbpmId` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for variableinstancelog
-- ----------------------------
DROP TABLE IF EXISTS `variableinstancelog`;
CREATE TABLE `variableinstancelog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `log_date` datetime DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `oldValue` varchar(255) DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `variableId` varchar(255) DEFAULT NULL,
  `variableInstanceId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for workiteminfo
-- ----------------------------
DROP TABLE IF EXISTS `workiteminfo`;
CREATE TABLE `workiteminfo` (
  `workItemId` bigint(20) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `processInstanceId` bigint(20) NOT NULL,
  `state` bigint(20) NOT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  `workItemByteArray` longblob,
  PRIMARY KEY (`workItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO organizationalentity (DTYPE, id) VALUES ('Group', 'Administrators');
INSERT INTO organizationalentity (DTYPE, id) VALUES ('User', 'Administrator');

INSERT INTO organizationalentity (DTYPE, id) VALUES ('Group', 'Test');
INSERT INTO organizationalentity (DTYPE, id) VALUES ('User', 'pandy');
INSERT INTO organizationalentity (DTYPE, id) VALUES ('User', 'pyzheng');
INSERT INTO organizationalentity (DTYPE, id) VALUES ('User', 'jack');
--INSERT INTO organizationalentity (DTYPE, id) VALUES ('User', 'krisv');
--INSERT INTO organizationalentity (DTYPE, id) VALUES ('User', 'john');
INSERT INTO organizationalentity (DTYPE, id) VALUES ('User', 'mary');


INSERT INTO `user` VALUES ('1', 'Test', 'pandy', 'pandy');
INSERT INTO `user` VALUES ('2', 'Test', 'pyzheng', 'pyzheng');
INSERT INTO `user` VALUES ('3', 'Test', 'jack', 'jack');
INSERT INTO `user` VALUES ('4', 'Test', 'krisv', 'krisv');
INSERT INTO `user` VALUES ('5', 'Test', 'john', 'john');
INSERT INTO `user` VALUES ('6', 'Test', 'mary', 'mary');