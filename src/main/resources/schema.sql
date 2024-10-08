CREATE TABLE IF NOT EXISTS mpa
(
	id	        INTEGER PRIMARY KEY AUTO_INCREMENT,
	name		VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS films
(
	id 	 	        INTEGER PRIMARY KEY AUTO_INCREMENT,
	name 		 	VARCHAR(255) NOT NULL,
	description  	VARCHAR(200) NOT NULL,
	release_date 	DATE,
	duration 	 	INTEGER,
	rating_id 	 	INTEGER REFERENCES mpa(id)
);

CREATE TABLE IF NOT EXISTS genre
(
	id	        INTEGER PRIMARY KEY AUTO_INCREMENT,
	genre		VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS film_genre
(
	film_id		INTEGER REFERENCES films(id),
	genre_id 	INTEGER REFERENCES genre(id),
	UNIQUE 		(film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS users
(
	id 	 	        INTEGER PRIMARY KEY AUTO_INCREMENT,
	email 		 	VARCHAR(255) NOT NULL,
	login  			VARCHAR(255) NOT NULL,
	user_name 		VARCHAR(255) NOT NULL,
	birthday		DATE
);

CREATE TABLE IF NOT EXISTS film_likes
(
	user_id		INTEGER REFERENCES users(id),
	film_id		INTEGER REFERENCES films(id),
	UNIQUE(user_id, film_id)
);

CREATE TABLE IF NOT EXISTS friendship
(
	user_id 	INTEGER REFERENCES users(id),
	friend_id	INTEGER REFERENCES users(id)
);