
alter table if exists bucket add constraint FKdjo9wtsck5xe2nfcgtymx0bap foreign key (client_id) references client
    ;alter table if exists bucket add constraint FK1rviwb1eyoi7u8nkgpay6ujx foreign key (food_id) references food
    ;alter table if exists comments add constraint FK5eb9reqw3fwfg1n5sd5jv9q18 foreign key (food_id) references food
    ;alter table if exists comments add constraint FK8omq0tc18jd43bu5tjh6jvraq foreign key (user_id) references users
    ;alter table if exists food add constraint FKotbdydrcrlsrd8jdpwvxcgu7y foreign key (chief_id) references chief
    ;alter table if exists food add constraint FKf3bmvf487ya6cqglsh3lotiiq foreign key (food_type_id) references food_type
    ;alter table if exists food_comments add constraint FKchcruicgk5363sb4dj61bf31e foreign key (comments_id) references comments
    ;alter table if exists food_comments add constraint FK359ymay78ig75qgb6ijdqv3bq foreign key (food_id) references food
    ;alter table if exists food_images add constraint FKjjjt9373et45vaj0mguo4pd2p foreign key (food_id) references food
    ;alter table if exists users add constraint FKc85kjjm2thtgg4w6iffpyoaff foreign key (chief_id) references chief
    ;alter table if exists users add constraint FK8tfw2gt4n1u3lqfd3vnqf51te foreign key (client_id) references client;