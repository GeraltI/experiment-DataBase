/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022/12/27 16:17:30                          */
/*==============================================================*/


drop trigger orders_before_finish;

drop trigger orders_before_insert;

drop trigger user_before_insert;

drop 
table if exists food_information;

drop 
table if exists client_informatin;

drop index business_name on business;

drop index business_PK on business;

drop index canteen_name on canteen;

drop index canteen_PK on canteen;

drop index client_name on client;

drop index client_PK on client;

drop index food_name on food;

drop index restaurant_food_FK on food;

drop index food_PK on food;

drop index manager_name on manager;

drop index manager_PK on manager;

drop index restaurant_order_FK on orders;

drop index user_order_FK on orders;

drop index order_PK on orders;

drop index order_food_PK on orders_food;

drop index restaurant_name on restaurant;

drop index business_restaurant_FK on restaurant;

drop index canteen_restaurant_FK on restaurant;

drop index restaurant_PK on restaurant;

drop index user_name on user;

drop index user_id on user;

/*==============================================================*/
/* Table: business                                              */
/*==============================================================*/
create table business
(
   user_id              int not null,
   user_name            varchar(32) not null,
   business_id          int not null auto_increment,
   business_name        varchar(32),
   business_applicaiton text
);

insert into business values
('2','business1',null,'business1','');
insert into business values
('3','business2',null,'business2','');
insert into business values
('4','business3',null,'business3','');

/*==============================================================*/
/* Index: business_PK                                           */
/*==============================================================*/
create unique index business_PK on business
(
   business_id
);

/*==============================================================*/
/* Index: business_name                                         */
/*==============================================================*/
create index business_name on business
(
   business_name
);

/*==============================================================*/
/* Table: canteen                                               */
/*==============================================================*/
create table canteen
(
   canteen_id           int not null auto_increment,
   canteen_name         char(32),
   canteen_address      longtext,
   canteen_application  longtext
);

insert into canteen values
(null,'canteen1','','');
insert into canteen values
(null,'canteen2','','');

/*==============================================================*/
/* Index: canteen_PK                                            */
/*==============================================================*/
create unique index canteen_PK on canteen
(
   canteen_id
);

/*==============================================================*/
/* Index: canteen_name                                          */
/*==============================================================*/
create index canteen_name on canteen
(
   canteen_name
);

/*==============================================================*/
/* Table: client                                                */
/*==============================================================*/
create table client
(
   user_id              int not null,
   user_name            varchar(32) not null,
   client_id            int not null auto_increment,
   client_name          varchar(32),
   client_sex           smallint,
   client_birth         date,
   client_hobby         longtext,
   client_address       longtext,
   client_contact       longtext
);

insert into client values
('5','client1',null,'client1','1','2012-12-31 11:30:45','','','');
insert into client values
('6','client2',null,'client2','2','2013-12-31 11:30:45','','','');
insert into client values
('7','client3',null,'client3','1','2014-12-31 11:30:45','','','');
insert into client values
('8','client4',null,'client4','2','2015-12-31 11:30:45','','','');
insert into client values
('9','client5',null,'client5','1','2016-12-31 11:30:45','','','');

/*==============================================================*/
/* Index: client_PK                                             */
/*==============================================================*/
create unique index client_PK on client
(
   client_id
);

/*==============================================================*/
/* Index: client_name                                           */
/*==============================================================*/
create index client_name on client
(
   client_name
);

/*==============================================================*/
/* Table: food                                                  */
/*==============================================================*/
create table food
(
   food_id              int not null auto_increment,
   restaurant_id        int not null,
   food_name            varchar(32) not null,
   food_application     longtext,
   food_single_price    double not null,
   food_rest            smallint
);

insert into food values
(null,'1','food1',null,'1','5');
insert into food values
(null,'1','food2',null,'2','6');
insert into food values
(null,'2','food3',null,'3','7');
insert into food values
(null,'2','food4',null,'4','8');

/*==============================================================*/
/* Index: food_PK                                               */
/*==============================================================*/
create unique index food_PK on food
(
   food_id
);

/*==============================================================*/
/* Index: restaurant_food_FK                                    */
/*==============================================================*/
create index restaurant_food_FK on food
(
   restaurant_id
);

/*==============================================================*/
/* Index: food_name                                             */
/*==============================================================*/
create index food_name on food
(
   food_name
);

/*==============================================================*/
/* Table: manager                                               */
/*==============================================================*/
create table manager
(
   user_id              int not null,
   user_name            varchar(32) not null,
   manager_id           int not null auto_increment,
   manager_name         varchar(32)
);

insert into manager values
('1','admin',null,'manager1');

/*==============================================================*/
/* Index: manager_PK                                            */
/*==============================================================*/
create unique index manager_PK on manager
(
   manager_id
);

/*==============================================================*/
/* Index: manager_name                                          */
/*==============================================================*/
create index manager_name on manager
(
   manager_name
);

/*==============================================================*/
/* Table: orders                                                */
/*==============================================================*/
create table orders
(
   order_id             bigint not null auto_increment,
   client_id            int not null,
   restaurant_id        int not null,
   client_phone_number  char(12) not null,
   order_address        longtext not null,
   order_price          double not null,
   order_paid           bool,
   order_confirm        bool,
   order_finish         bool,
   order_create_time    datetime,
   order_finish_time    datetime
);

insert into orders values
(null,'1','1','111','11','11',null,null,null,null,null);
insert into orders values
(null,'1','1','111','11','11',null,null,null,null,null);

/*==============================================================*/
/* Index: order_PK                                              */
/*==============================================================*/
create unique index order_PK on orders
(
   order_id
);

/*==============================================================*/
/* Index: user_order_FK                                         */
/*==============================================================*/
create index user_order_FK on orders
(
   client_id
);

/*==============================================================*/
/* Index: restaurant_order_FK                                   */
/*==============================================================*/
create index restaurant_order_FK on orders
(
   restaurant_id
);

/*==============================================================*/
/* Table: orders_food                                           */
/*==============================================================*/
create table orders_food
(
   order_id             int not null,
   food_id              int not null,
   food_number          smallint not null
);

insert into orders_food values
('1','1','3');
insert into orders_food values
('1','2','4');
insert into orders_food values
('2','3','1');
insert into orders_food values
('2','4','2');

/*==============================================================*/
/* Index: order_food_PK                                         */
/*==============================================================*/
create unique index order_food_PK on orders_food
(
   order_id,
   food_id
);

/*==============================================================*/
/* Table: restaurant                                            */
/*==============================================================*/
create table restaurant
(
   restaurant_id        int not null auto_increment,
   canteent_id          int not null,
   business_id          int not null,
   restaurant_name      varchar(32) not null,
   restaurant_location  longtext
);

insert into restaurant values
(null,'1','1','restaurant1','');
insert into restaurant values
(null,'2','2','restaurant2','');
insert into restaurant values
(null,'1','3','restaurant3','');
insert into restaurant values
(null,'2','4','restaurant4','');
insert into restaurant values
(null,'1','5','restaurant5','');

/*==============================================================*/
/* Index: restaurant_PK                                         */
/*==============================================================*/
create unique index restaurant_PK on restaurant
(
   restaurant_id
);

/*==============================================================*/
/* Index: canteen_restaurant_FK                                 */
/*==============================================================*/
create index canteen_restaurant_FK on restaurant
(
   canteent_id
);

/*==============================================================*/
/* Index: business_restaurant_FK                                */
/*==============================================================*/
create index business_restaurant_FK on restaurant
(
   business_id
);

/*==============================================================*/
/* Index: restaurant_name                                       */
/*==============================================================*/
create index restaurant_name on restaurant
(
   restaurant_name
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_id              int not null auto_increment,
   user_name            varchar(32) not null,
   user_password        varchar(16) not null,
   user_active          bool,
   user_create_time     datetime
);

insert into user values
(null,'admin','123456',true,now());
insert into user values
(null,'business1','123456',true,now());
insert into user values
(null,'business2','123456',true,now());
insert into user values
(null,'business3','123456',true,now());
insert into user values
(null,'client1','123456',true,now());
insert into user values
(null,'client2','123456',true,now());
insert into user values
(null,'client3','123456',true,now());
insert into user values
(null,'client4','123456',true,now());
insert into user values
(null,'client5','123456',true,now());

/*==============================================================*/
/* Index: user_id                                               */
/*==============================================================*/
create unique index user_id on user
(
   user_id,
   user_name
);

/*==============================================================*/
/* Index: user_name                                             */
/*==============================================================*/
create unique index user_name on user
(
   user_id,
   user_name
);

/*==============================================================*/
/* View: client_informatin                                      */
/*==============================================================*/
create VIEW  client_informatin
 as
select client.client_id,client.client_name,
       client.client_sex,client.client_birth,
       client.client_hobby,client.client_address,
       client.client_contact,sum(orders.order_price)
       
from   client,orders

where  orders.client_id = client.client_id

group by orders.order_id;

/*==============================================================*/
/* View: food_information                                       */
/*==============================================================*/
create VIEW  food_information
 as
select food.food_id,food.food_name,sum(food_number)

from   orders_food,food

where  orders_food.food_id = food.food_id

group by food.food_id;


create trigger orders_before_finish
before update on orders
for each row update orders set new.order_finish_time = now() 
where new.order_finish <> old.order_finish;


create trigger orders_before_insert
before insert on orders
for each row 
set new.order_create_time = now() and new.order_paid = false 
and new.order_confirm = false and new.order_finish = false;


create trigger user_before_insert
before insert on user
for each row 
set new.user_create_time = now() and new.user_active = true;

