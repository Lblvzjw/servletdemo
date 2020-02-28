create database servlettest charset = utf8mb4;

use servlettest;
create table users(
  id int primary key auto_increment,
  username varchar(200) not null unique ,
  nickname varchar(200) not null,
  password char(64) not null
);

create table articles(
  id int primary key  auto_increment,
  author_id int not null ,
  thilt varchar(200) not null ,
  content text not null ,
  published_at datetime not null
);

create table comments (
  id int primary key auto_increment,
  author_id int not null ,
  article_id int not null,
  content varchar(200),
  published_at datetime not null
);

create table user_like_article_relations(
  user_id int not null ,
  article_id int not null ,
  primary key (user_id,article_id)
);

insert into users(username, nickname, password) values (?,?,?);

select id,username,nickname from users where username = ? and password = ?;