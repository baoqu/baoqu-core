-- Circles SQL definitions

-- :name q-create-circles-table
-- :command execute
-- :result :raw
create table circles (
    id integer primary key autoincrement,
    "event-id" integer,
    level integer,
    "parent-circle" integer,
    foreign key ("event-id") references events(id)
)

-- :name q-create-users-circles-table
-- :command execute
-- :result :raw
create table users_circles (
    "user-id" integer,
    "circle-id" integer,
    foreign key ("user-id") references users(id),
    foreign key ("circle-id") references circles(id),
    primary key ("user-id", "circle-id")
)

-- :name q-insert-circle :i!
insert into circles ("event-id", level, "parent-circle")
values (:event-id, :level, :parent-circle)

-- :name q-get-all :?
select * from circles

-- :name q-get-by-id :? :1
select * from circles
where id=:id

-- :name q-add-user-to-repo :!
insert into users_circles ("user-id", "circle-id")
values (:user-id, :circle-id)

-- :name q-get-all-incomplete :?
select c.id from circles as c left join users_circles as cs on (c.id=cs."circle-id") where c."event-id"=:event-id group by c.id having count(cs."user-id") < :circle-size;
