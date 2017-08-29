drop database if EXISTS ntech_db;
create database ntech_db default character set utf8 collate utf8_general_ci;
use ntech_db;

drop table if exists customer;
create table customer
(
  name varchar(32) not null,
  password varchar(64) not null,
  contype varchar(2),
  active int DEFAULT 0,
  email varchar(64),
  token varchar(32),
  regtime datetime,
  primary key(name)
)engine=innodb default charset=utf8 auto_increment=1;

drop table if exists set_meal;
create table set_meal
(
  id int not null,
  user_name varchar(32) not null,
  contype varchar(2),
  begin_time DATE,
  end_time DATE,
  total_times int,
  left_times int,
  enable int,
  primary key(id)
)engine=innodb default charset=utf8 auto_increment=1;

# drop table if exists pay_times;
# create table pay_times
# (
#   id int not null,
#   user_name varchar(32) not null,
#   contype varchar(2),
#   begin_time DATE,
#   enable int,
#   total_times int not null,
#   left_times int not null,
#   primary key(id)
# )engine=innodb default charset=utf8 auto_increment=1;

insert into customer values('admin','d033e22ae348aeb5660fc2140aec35850c4da997','',1,'admin@admin.com','','2017-08-23 15:58:07');