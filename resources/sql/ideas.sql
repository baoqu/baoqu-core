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
    "user-id" integer,
    "idea-id" integer,
    foreign key ("user-id") references users(id),
    foreign key ("idea-id") references ideas(id),
    primary key ("user-id", "idea-id")
)
