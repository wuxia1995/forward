drop database if EXISTS ntech_db;
create database ntech_db default character set utf8 collate utf8_general_ci;
use ntech_db;

drop table if exists customer;
create table customer
(
  name varchar(32) not null,
  password varchar(64) not null,
  contype varchar(2) DEFAULT '0',
  email varchar(64),
  token varchar(32),
  regtime datetime,
  primary key(name)
)engine=innodb default charset=utf8 auto_increment=1;

drop table if exists pay_date;
create table pay_date
(
  id int not null,
  user_name varchar(32) not null,
  contype varchar(2),
  begin_time DATE,
  end_time DATE,
  primary key(id)
)engine=innodb default charset=utf8 auto_increment=1;

drop table if exists pay_times;
create table pay_times
(
  id int not null,
  user_name varchar(32) not null,
  contype varchar(2),
  begin_time DATE,
  total_times int not null,
  left_times int not null,
  primary key(id)
)engine=innodb default charset=utf8 auto_increment=1;