create table food_type (id bigint not null, name varchar(255), primary key (id))
;
 create table chief (id uuid not null, activated boolean, firstname varchar(255), lastname varchar(255), rank varchar(255) check (rank in ('CHIEF','JUNIOR','SENIOR','MASTER')), primary key (id))
    ; create table client (id uuid not null, firstname varchar(255), lastname varchar(255), primary key (id));
 CREATE TABLE food (
                       id BIGINT NOT NULL,
                       description VARCHAR(255),
                       discount INTEGER NOT NULL,
                       name VARCHAR(255),
                       price INTEGER NOT NULL,
                       chief_id UUID,
                       food_type_id BIGINT,
                       PRIMARY KEY (id),
                       FOREIGN KEY (chief_id) REFERENCES chief (id),
                       FOREIGN KEY (food_type_id) REFERENCES food_type (id)
 );
 CREATE TABLE food_images (
                              food_id BIGINT NOT NULL,
                              images VARCHAR(255), -- Consider changing this to TEXT if URLs might be very long.
                              FOREIGN KEY (food_id) REFERENCES food (id)
 );

    ; create table users (id uuid not null, email varchar(255), password varchar(255), phone_number varchar(255), role varchar(255) check (role in ('CLIENT','ADMIN','CHIEF')), chief_id uuid, client_id uuid, primary key (id));