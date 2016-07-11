-- Events SQL definitions

-- :name q-create-events-table
-- :command :execute
-- :result :raw
create table events (
    id integer primary key autoincrement,
    name varchar(255),
    circle_size integer,
    approval_factor integer
)

-- :name q-insert-event :i!
insert into events (name, circle_size, approval_factor)
values (:name, :circle-size, :approval-factor)

-- :name q-all :? :*
select * from events

-- :name q-get-by-id :? :1
select * from events
where id=:id
