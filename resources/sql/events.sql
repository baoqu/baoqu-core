-- Events SQL definitions

-- :name q-create-events-table
-- :command :execute
-- :result :raw
create table events (
    id integer primary key autoincrement,
    name varchar(255),
    description text,
    "circle-size" integer,
    "agreement-factor" integer
)

-- :name q-insert-event :i!
insert into events (name, description, "circle-size", "agreement-factor")
values (:name, :description, :circle-size, :agreement-factor)

-- :name q-get-all :? :*
select * from events

-- :name q-get-by-id :? :1
select * from events
where id=:id

-- :name q-get-events-for-user :?
select e.* from events as e
inner join circles as c
on e.id = c."event-id"
inner join users_circles as uc
on c.id = uc."circle-id"
where uc."user-id" = :user-id
