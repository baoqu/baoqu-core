-- Circles SQL definitions

-- :name create-circles-table
-- :command execute
-- :result :raw
create table circles (
    id integer primary key autoincrement,
    id_event integer,
    level integer,
    parent_circle integer,
    foreign key (id_event) references events(id)
)

-- :name create-users-circles-table
-- :command execute
-- :result :raw
create table users_circles (
    id_user integer,
    id_circle integer,
    foreign key (id_user) references users(id),
    foreign key (id_circle) references circles(id),
    primary key (id_user, id_circle)
)
