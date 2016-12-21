-- Circles SQL definitions

-- :name q-create-circles-table
-- :command execute
-- :result :raw
create table circles (
                 id  integer primary key autoincrement,
         "event-id"  integer,
              level  integer,
               size  integer,
 "parent-circle-id"  integer,
        foreign key  ("event-id") references events(id)
)

-- :name q-create-users-circles-table
-- :command execute
-- :result :raw
create table users_circles (
      "user-id"  integer,
    "circle-id"  integer,
    foreign key  ("user-id") references users(id),
    foreign key  ("circle-id") references circles(id),
    primary key  ("user-id", "circle-id")
)

-- :name q-insert-circle :i!
insert into circles ("event-id", level, size, "parent-circle-id")
values (:event-id, :level, :size, :parent-circle-id)

-- :name q-persist-circle :i!
update circles set
        "event-id" = :event-id,
             level = :level,
              size = :size,
"parent-circle-id" = :parent-circle-id
 where id = :id

-- :name q-delete-circle :!
delete from circles
 where id = :id

-- :name q-get-all :?
select * from circles

-- :name q-get-inner-circles :?
select id from circles
where "parent-circle-id" = :circle-id

-- :name q-get-all-for-event :?
select c.* from circles as c
 inner join users_circles as uc
         on c.id = uc."circle-id"
where c."event-id" = :event-id
group by c.id

-- :name q-get-all-for-user :?
select c.* from circles as c
 inner join users_circles as uc
         on uc."circle-id" = c.id
      where uc."user-id" = :user-id

-- :name q-get-by-id :? :1
select * from circles
 where id = :id

-- :name q-get-circle-users :?
select * from users_circles as uc
 inner join users as u
         on (uc."user-id" = u.id)
 where uc."circle-id" = :id

-- :name q-add-user-to-circle :!
insert into users_circles ("user-id", "circle-id")
values (:user-id, :circle-id)

-- :name q-remove-user-from-circle :!
delete from users_circles
 where "user-id" = :user-id
   and "circle-id" = :circle-id
-- :name q-get-all-incomplete :?
select * from circles as c
  left join users_circles as cs
         on (c.id=cs."circle-id")
 where c."event-id"=:event-id
   and c.level=:level
 group by c.id
having count(cs."user-id") < :agreement-factor;

-- :name q-get-circle-for-user-and-level :? :1
select * from circles as c
 inner join users_circles as uc
    on (c.id = uc."circle-id")
 where uc."user-id" = :user-id
   and c.level = :level

-- :name q-get-highest-level-circle :? :1
select * from circles as c
 inner join users_circles as uc
         on (c.id=uc."circle-id")
 where uc."user-id"=:user-id
 order by level desc

-- :name q-get-circle-ideas :?
select i.id, i.name, count(uc."user-id") as votes
  from users_circles as uc
 inner join users_ideas as ui
         on (uc."user-id"=ui."user-id")
 inner join ideas as i
         on (ui."idea-id"=i.id)
 where uc."circle-id"=:circle-id
 group by ui."idea-id"

-- :name q-get-circle-agreements :?
select i.id, i.name, count(uc."user-id") as votes
  from users_circles as uc
 inner join users_ideas as ui
         on (uc."user-id" = ui."user-id")
 inner join ideas as i
         on (ui."idea-id" = i.id)
 where uc."circle-id" = :circle-id
 group by i.id
having votes = :agreement-factor
