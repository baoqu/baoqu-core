-- Users SQL definitions

-- :name create-users-table
-- :command execute
-- :results :raw
create table users (
    id integer primary key autoincrement,
    name varchar(255),
    email varchar(255) unique
)
