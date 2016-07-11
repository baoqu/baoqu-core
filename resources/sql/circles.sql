-- Circles SQL definitions

-- :name create-circles-table
-- :command execute
-- :result :raw
create table circles (
    id integer primary key autoincrement,
    foreign key (id_event) references events(id),
    level integer,
    parent_circle integer
)
