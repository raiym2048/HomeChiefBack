
 create table chief (id uuid not null, activated boolean, firstname varchar(255), lastname varchar(255), rank varchar(255) check (rank in ('CHIEF','JUNIOR','SENIOR','MASTER')), primary key (id))
    ; create table client (id uuid not null, firstname varchar(255), lastname varchar(255), primary key (id))
    ; create table food (id bigint not null, description varchar(255), discount integer not null, image varchar(255), name varchar(255), price integer not null, chief_id uuid, food_type_id bigint, primary key (id))
    ; create table food_type (id bigint not null, name varchar(255), primary key (id))
    ; create table users (id uuid not null, email varchar(255), password varchar(255), phone_number varchar(255), role varchar(255) check (role in ('CLIENT','ADMIN','CHIEF')), chief_id uuid, client_id uuid, primary key (id));