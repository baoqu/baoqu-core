-- Events SQL definitions

-- :name q-create-events-table
-- :command :execute
-- :result :raw
create table events (
    id integer primary key autoincrement,
    name varchar(255),
    "circle-size" integer,
    "agreement-factor" integer
)

-- :name q-insert-event :i!
insert into events (name, "circle-size", "agreement-factor")
values (:name, :circle-size, :agreement-factor)

-- :name q-all :? :*
select * from events

-- :name q-get-by-id :? :1
select * from events
where id=:id
