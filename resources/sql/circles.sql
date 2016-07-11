-- Circles SQL definitions

-- :name q-create-circles-table
-- :command execute
-- :result :raw
create table circles (
    id integer primary key autoincrement,
    event_id integer,
    level integer,
    parent_circle integer,
    foreign key (event_id) references events(id)
)

-- :name q-create-users-circles-table
-- :command execute
-- :result :raw
create table users_circles (
    user_id integer,
    circle_id integer,
    foreign key (user_id) references users(id),
    foreign key (circle_id) references circles(id),
    primary key (user_id, circle_id)
)

-- :name q-insert-circle :i!
insert into circles (event_id, level, parent_circle)
values (:event-id, :level, :parent-circle)

-- :name q-get-by-id :? :1
select * from circles
where id=:id

-- :name q-add-user-to-repo :!
insert into users_circles (user_id, circle_id)
values (:user-id, :circle-id)

-- :name q-get-all-incomplete :? :*
select c.id from circles as c left join users_circles as cs on (c.id=cs.circle_id) where c.event_id=:event-id group by c.id having count(cs.user_id) < :circle-size;
