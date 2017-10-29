drop database suprem_command;
create database db_command;
use db_command;

create table context(
context_id INT NOT NULL auto_increment,
context_name Varchar(50),
context_symbole Varchar(20),
PRIMARY key(context_id)
);
create table command(
command_id INT NOT NULL auto_increment,
command_name VARCHAR(30),
PRIMARY key(command_id)
);
create table context_command(
context_id INT NOT NULL auto_increment,
command_id INT NOT NULL,
command_value VARCHAR(250),
command_description VARCHAR(250),
FOREIGN key(command_id) REFERENCES command(command_id),
FOREIGN key(context_id) REFERENCES context(context_id),
PRIMARY key(context_id,command_id)
);
create table command_option(
option_id INT NOT NULL auto_increment,
command_id INT NOT NULL,
option_name VARCHAR(30),
option_value VARCHAR(250),
option_description VARCHAR(100),

FOREIGN key(command_id) REFERENCES command(command_id),
PRIMARY key(option_id)
);