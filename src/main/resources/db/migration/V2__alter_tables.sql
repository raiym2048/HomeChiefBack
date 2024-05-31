 alter table if exists users drop constraint if exists UKgfl9wd9dx7rkkvyimclm9qu1h

    ; alter table if exists users add constraint UKgfl9wd9dx7rkkvyimclm9qu1h unique (chief_id)
    ; alter table if exists users drop constraint if exists UKg9epudg12gt4bfg0g8l9evbf2

    ; alter table if exists users add constraint UKg9epudg12gt4bfg0g8l9evbf2 unique (client_id)
    ; create sequence food_seq start with 10000 increment by 1
    ; create sequence food_type_seq start with 10000 increment by 1
    ; alter table if exists food add constraint FKotbdydrcrlsrd8jdpwvxcgu7y foreign key (chief_id) references chief
    ; alter table if exists food add constraint FKf3bmvf487ya6cqglsh3lotiiq foreign key (food_type_id) references food_type
    ; alter table if exists users add constraint FKc85kjjm2thtgg4w6iffpyoaff foreign key (chief_id) references chief
    ; alter table if exists users add constraint FK8tfw2gt4n1u3lqfd3vnqf51te foreign key (client_id) references client;