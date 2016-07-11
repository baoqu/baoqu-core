-- Ideas SQL definitions

-- :name create-ideas-table
-- :command :execute
-- :result :raw
create table ideas (
    id integer primary key autoincrement,
    name varchar(255) unique
)

-- :name create-users-ideas-table
-- :command :execute
-- :result :raw
create table users_ideas (
    id_user integer,
    id_idea integer,
    foreign key (id_user) references users(id),
    foreign key (id_idea) references ideas(id),
    primary key (id_user, id_idea)
)
