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
    user_id integer,
    idea_id integer,
    foreign key (user_id) references users(id),
    foreign key (idea_id) references ideas(id),
    primary key (user_id, idea_id)
)
