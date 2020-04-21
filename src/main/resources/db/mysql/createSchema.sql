drop database if exists `acmevolardb`;
create database `acmevolardb`;

grant select, insert, update, delete 
	on `acmevolardb`.* to 'acmevolar'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `acmevolardb`.* to 'acmevolar'@'%';
    
ALTER SCHEMA `acmevolardb`  DEFAULT CHARACTER SET latin1  DEFAULT COLLATE latin1_bin ;