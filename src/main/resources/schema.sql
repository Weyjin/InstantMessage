CREATE TABLE `user`(
id int(20) not null primary key AUTO_INCREMENT,
name char(20),
password char(64) default '123456',
signature char(200),
group_id int(20)
);
CREATE TABLE `group`(
   id int(20) not null primary key AUTO_INCREMENT,
   name char(20)
);
CREATE TABLE `group_chat`(
     id int(20) not null primary key AUTO_INCREMENT,
     name char(64)
);
CREATE TABLE `chat_users`(
    id int(20) not null primary key AUTO_INCREMENT,
    chat_id int(20),
    user_id int(20)
);
CREATE TABLE `login_token`(
       id int(20) not null primary key AUTO_INCREMENT,
       token char(64),
       user_id int(20)
);
