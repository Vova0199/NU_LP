SELECT date,count(*) AS c FROM Trip,
(SELECT trip_no,date FROM Pass_in_trip  GROUP BY trip_no, date) AS t1
WHERE Trip.trip_no=t1.trip_no AND town_from='London'
GROUP BY date;