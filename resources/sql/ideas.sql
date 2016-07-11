-- Ideas SQL definitions

-- :name create-ideas-table
-- :command :execute
-- :result :raw
create table ideas (
    id integer primary key autoincrement,
    name varchar(255) unique
)
