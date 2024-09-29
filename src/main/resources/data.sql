MERGE INTO MPA (RATING_ID, RATING)
VALUES 	(1, 'G'),
 	   	(2, 'PG'),
		(3, 'PG-13'),
 		(4, 'R'),
 		(5, 'NC-17');

MERGE INTO GENRE (GENRE_ID, GENRE)
VALUES 	(1, 'Comedy'),
		(2, 'Drama'),
		(3, 'Сartoon'),
		(4, 'Thriller'),
		(5, 'Documentary'),
		(6, 'Action');