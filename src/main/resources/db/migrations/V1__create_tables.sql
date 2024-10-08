
 create sequence food_seq start with 10000 increment by 1
    ; create sequence food_type_seq start with 10000 increment by 1
    ; create table bucket (count integer not null, food_id bigint, id bigint generated by default as identity, client_id uuid, primary key (id))
    ; create table chief (activated boolean, id uuid not null, firstname varchar(255), lastname varchar(255), rank varchar(255) check (rank in ('CHIEF','JUNIOR','SENIOR','MASTER')), primary key (id))
    ; create table client (id uuid not null, firstname varchar(255), lastname varchar(255), primary key (id))
    ; create table comments (cut_id bigint unique, id bigint generated by default as identity, time timestamp(6), user_id uuid unique, title varchar(255), primary key (id))
    ; create table cut (stars float(53) not null, food_id bigint, id bigint generated by default as identity, description varchar(255), name varchar(255), url varchar(255), primary key (id))
    ; create table cut_comments (comments_id bigint not null unique, cut_id bigint not null)
    ; create table food (discount integer not null, price integer not null, food_type_id bigint, id bigint not null, chief_id uuid, description varchar(255), name varchar(255), primary key (id))
    ; create table food_images (food_id bigint not null, images varchar(255))
    ; create table food_reviews (food_id bigint not null, reviews_id bigint not null unique)
    ; create table food_type (id bigint not null, name varchar(255), primary key (id))
    ; create table review (star integer not null, food_id bigint unique, id bigint generated by default as identity, user_id uuid unique, title varchar(255), primary key (id))
    ; create table users (chief_id uuid unique, client_id uuid unique, id uuid not null, email varchar(255), password varchar(255), phone_number varchar(255), role varchar(255) check (role in ('CLIENT','ADMIN','CHIEF')), primary key (id));