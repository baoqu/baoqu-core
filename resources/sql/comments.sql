-- Comments SQL definitions

-- :name q-create-comments-table
-- :command execute
-- :result :raw
create table comments (
         id integer primary key autoincrement,
  "user-id" integer,
"circle-id" integer,
       body integer,
       date integer,
foreign key ("user-id") references user(id),
foreign key ("circle-id") references circle(id)
)

-- :name q-insert-comment :i!
insert into comments ("user-id", "circle-id", body, date)
values (:user-id, :circle-id, :body, :date)

-- :name q-persist-comment :i!
update comments set
  "user-id" = :user-id,
"circle-id" = :circle-id,
       body = :body,
       date = :date,
 where id = :id

-- :name q-delete-comment :!
delete from comments
 where id = :id

-- :name q-get-all :?
select * from comments

-- :name q-get-all-for-circle :?
select * from comments
where "circle-id" = :circle-id

-- :name q-get-by-id :? :1
select * from comments
 where id = :id
