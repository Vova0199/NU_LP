select name from ships
where name like '% %'
union
select ship from outcomes
where ship like '% %';