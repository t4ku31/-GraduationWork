set linesize 500
set pagesize 200
col role for a10
col email for a20
col avatar for a20
col username for a15
col hashed_password for a44
col salt for a24
col title for a15
col developer for a15
col release_date for a15
col description for a20
col thumbnail_url for a20
col card_type for a10






select * from games;
select * from languages;
select * from genres;
select * from orders;
select * from order_details;
select * from carts;

delete from carts
where game_id = 30;
delete from order_details
where game_id = 30;
delete from languages
where game_id = 30;
delete from genres
where game_id = 30;
delete from games
where game_id = 30;