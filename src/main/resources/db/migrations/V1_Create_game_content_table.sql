
drop table gamecontent;
CREATE TABLE gamecontent
(
    id SERIAL PRIMARY KEY NOT NULL,
    player_one_username TEXT,
    player_two_username TEXT,
    player_one_move_type TEXT,
    player_two_move_type TEXT,
    player_one_result TEXT,
    game_time TIMESTAMP
);