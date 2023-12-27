create table if not exists t_video_game_publisher
(
    id       int          not null primary key auto_increment,
    name     varchar(255) not null unique,
    location varchar(255) not null
);

alter table t_video_game_publisher auto_increment = 1;

create table if not exists t_video_game
(
    id           int          not null primary key auto_increment,
    name         varchar(255) not null,
    genre        varchar(255) not null,
    publisher_id int          not null,
    sales        int          not null default 0,

    foreign key (publisher_id) references t_video_game_publisher (id)
);

alter table t_video_game auto_increment = 1;

insert ignore into t_video_game_publisher (id, name, location)
values (1, 'Nintendo', 'Japan'),
       (2, 'Ubisoft', 'France'),
       (3, 'From Software', 'Japan');

insert ignore into t_video_game (id, name, genre, publisher_id, sales)
values (1, 'The Legend of Zelda: Tears of the Kingdom', 'Action-adventure', 1, 1000),
       (2, 'Super Smash Bros. Ultimate', 'Fighting', 1, 2000),
       (3, 'Super Mario Odyssey', 'Platform', 1, 3000),
       (4, 'Assassin''s Creed: Valhalla', 'Action role-playing', 2, 4000),
       (5, 'Elden Ring', 'Action role-playing', 3, 5000),
       (6, 'Dark Souls III', 'Action role-playing', 3, 6000);
