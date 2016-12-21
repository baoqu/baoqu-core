-- Users SQL definitions

-- :name q-create-users-table
-- :command execute
-- :results :raw
create table users (
    id integer primary key autoincrement,
    name varchar(255)
)

-- :name q-insert-user :i!
insert into users (name)
values (:name)

-- :name q-get-by-id :? :1
select * from users
where id=:id

-- :name q-get-by-name :? :1
select * from users
where name=:name

-- :name q-get-all-by-circle :?
select u.* from users as u
 inner join users_circles as uc
         on u.id = uc."user-id"
 inner join circles as c
         on c.id = uc."circle-id"
      where c.id = :circle-id

-- :name q-get-all :?
select * from users
