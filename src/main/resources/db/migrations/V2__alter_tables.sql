
alter table if exists bucket add constraint FKdjo9wtsck5xe2nfcgtymx0bap foreign key (client_id) references client
    ;alter table if exists bucket add constraint FK1rviwb1eyoi7u8nkgpay6ujx foreign key (food_id) references food
    ;alter table if exists comments add constraint FKr285y89l6rqtjod5v46fw6ldv foreign key (cut_id) references cut
    ;alter table if exists comments add constraint FK8omq0tc18jd43bu5tjh6jvraq foreign key (user_id) references users
    ;alter table if exists cut add constraint FKa6sk4r7fiqp9fb3y3e2qxxdl3 foreign key (food_id) references food
    ;alter table if exists cut_comments add constraint FKryhgten5toct1nfc26fchidfe foreign key (comments_id) references comments
    ;alter table if exists cut_comments add constraint FKgjylk9dvwanx0ydkfd9sbhw52 foreign key (cut_id) references cut
    ;alter table if exists food add constraint FKotbdydrcrlsrd8jdpwvxcgu7y foreign key (chief_id) references chief
    ;alter table if exists food add constraint FKf3bmvf487ya6cqglsh3lotiiq foreign key (food_type_id) references food_type
    ;alter table if exists food_images add constraint FKjjjt9373et45vaj0mguo4pd2p foreign key (food_id) references food
    ;alter table if exists food_reviews add constraint FKlwetfgv48hk5hkolailidhuej foreign key (reviews_id) references review
    ;alter table if exists food_reviews add constraint FK91igor4jji3kim0e9tmndburf foreign key (food_id) references food
    ;alter table if exists review add constraint FKch98c43t8o90hy3rj4qmihf0c foreign key (food_id) references food
    ;alter table if exists review add constraint FK6cpw2nlklblpvc7hyt7ko6v3e foreign key (user_id) references users
    ;alter table if exists users add constraint FKc85kjjm2thtgg4w6iffpyoaff foreign key (chief_id) references chief
    ;alter table if exists users add constraint FK8tfw2gt4n1u3lqfd3vnqf51te foreign key (client_id) references client;