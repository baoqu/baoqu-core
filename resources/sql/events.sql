-- Events SQL definitions

-- :name create-events-table
-- :command :execute
-- :result :raw
create table events (
    id integer primary key autoincrement,
    name varchar(255),
    circle_size integer,
    approval_factor integer
)

-- :name insert-event :i!
insert into events (name, email)
values (:name, :email)

-- :name all :? :*
select * from events

-- :name get-by-id :? :1
select * from events
where id=:id
