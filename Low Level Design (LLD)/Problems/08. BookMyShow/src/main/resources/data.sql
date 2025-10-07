-- ===============================
-- 1️⃣ Movies
-- ===============================
INSERT INTO movie (title, runtime, language, description, genre, certification, release_date, poster_url, trailer_url)
VALUES
('Spider-Man: No Way Home', '2h 28m', 'English', 'Peter Parker seeks Doctor Strange''s help to make people forget he is Spider-Man, but things go wrong.', 'Action, Adventure, Sci-Fi', 'PG-13', '2021-12-17', 'https://example.com/posters/spiderman_no_way_home.jpg', 'https://example.com/trailers/spiderman_no_way_home.mp4'),
('The Dark Knight', '2h 32m', 'English', 'Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham.', 'Action, Crime, Drama', 'PG-13', '2008-07-18', 'https://example.com/posters/dark_knight.jpg', 'https://example.com/trailers/dark_knight.mp4'),
('Interstellar', '2h 49m', 'English', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival.', 'Adventure, Drama, Sci-Fi', 'PG-13', '2014-11-07', 'https://example.com/posters/interstellar.jpg', 'https://example.com/trailers/interstellar.mp4'),
('Demon Slayer: Kimetsu no Yaiba – Mugen Train', '1h 57m', 'Japanese', 'Tanjiro Kamado boards the Mugen Train to battle a demon that has been killing people aboard.', 'Animation, Action, Fantasy', 'PG-13', '2020-10-16', 'https://example.com/posters/demon_slayer_mugen_train.jpg', 'https://example.com/trailers/demon_slayer_mugen_train.mp4'),
('Jurassic World', '2h 4m', 'English', 'A new dinosaur theme park, built on the original site of Jurassic Park, experiences chaos when the dinosaurs escape.', 'Action, Adventure, Sci-Fi', 'PG-13', '2015-06-12', 'https://example.com/posters/jurassic_world.jpg', 'https://example.com/trailers/jurassic_world.mp4');

-- ===============================
-- 2️⃣ Theatres
-- ===============================
INSERT INTO theatre (name, city, address)
VALUES
('PVR Guwahati', 'Guwahati', 'G.S. Road, Guwahati, Assam'),
('Cinepolis Guwahati', 'Guwahati', 'Beltola Road, Guwahati, Assam'),
('IMAX Bengaluru', 'Bengaluru', 'Brigade Road, Bengaluru, Karnataka'),
('PVR Bengaluru', 'Bengaluru', 'Whitefield Main Road, Bengaluru, Karnataka'),
('Cinepolis Bengaluru', 'Bengaluru', 'MG Road, Bengaluru, Karnataka');

-- ===============================
-- 3️⃣ Screens
-- ===============================
-- PVR Guwahati (1)
INSERT INTO screen (theatre_id, name, total_seats) VALUES
(1, 'PVR Guwahati - Screen 1', 12),
(1, 'PVR Guwahati - Screen 2', 12),
(1, 'PVR Guwahati - Screen 3', 12);

-- Cinepolis Guwahati (2)
INSERT INTO screen (theatre_id, name, total_seats) VALUES
(2, 'Cinepolis Guwahati - Screen 1', 12),
(2, 'Cinepolis Guwahati - Screen 2', 12),
(2, 'Cinepolis Guwahati - Screen 3', 12);

-- IMAX Bengaluru (3) → IDs 7,8,9
INSERT INTO screen (theatre_id, name, total_seats) VALUES
(3, 'IMAX Bengaluru - Screen 1', 24),
(3, 'IMAX Bengaluru - Screen 2', 24),
(3, 'IMAX Bengaluru - Screen 3', 24);

-- PVR Bengaluru (4)
INSERT INTO screen (theatre_id, name, total_seats) VALUES
(4, 'PVR Bengaluru - Screen 1', 12),
(4, 'PVR Bengaluru - Screen 2', 12),
(4, 'PVR Bengaluru - Screen 3', 12);

-- Cinepolis Bengaluru (5)
INSERT INTO screen (theatre_id, name, total_seats) VALUES
(5, 'Cinepolis Bengaluru - Screen 1', 12),
(5, 'Cinepolis Bengaluru - Screen 2', 12),
(5, 'Cinepolis Bengaluru - Screen 3', 12);

-- ===============================
-- 4️⃣ Seats
-- ===============================
DO $$
DECLARE
    screen_id INT;
    row_num INT;
    total_seats INT;
BEGIN
    FOR screen_id IN 1..15 LOOP
        -- IMAX screens (IDs 7,8,9) have 24 seats
        IF screen_id IN (7,8,9) THEN
            total_seats := 24;
        ELSE
            total_seats := 12;
        END IF;

        FOR row_num IN 1..total_seats LOOP
            INSERT INTO seat (screen_id, seat_number, type, is_booked)
            VALUES (screen_id, 'A' || row_num, 'REGULAR', FALSE);
        END LOOP;
    END LOOP;
END $$;

-- ======================================
-- 🎥 Insert Shows (2 per screen) with real movie runtimes
-- ======================================

DO $$
DECLARE
    screen_id INT;
    movie_id INT;
    show_time_1 TIMESTAMP;
    show_time_2 TIMESTAMP;
    base_price DOUBLE PRECISION;
    runtime INTERVAL;
BEGIN
    FOR screen_id IN 1..15 LOOP
        -- Rotate movie_id between 1 and 5
        movie_id := ((screen_id - 1) % 5) + 1;

        -- Assign movie runtime dynamically
        CASE movie_id
            WHEN 1 THEN runtime := INTERVAL '2 hours 28 minutes';  -- Spider-Man: No Way Home
            WHEN 2 THEN runtime := INTERVAL '2 hours 32 minutes';  -- The Dark Knight
            WHEN 3 THEN runtime := INTERVAL '2 hours 49 minutes';  -- Interstellar
            WHEN 4 THEN runtime := INTERVAL '1 hour 57 minutes';   -- Demon Slayer
            WHEN 5 THEN runtime := INTERVAL '2 hours 4 minutes';   -- Jurassic World
        END CASE;

        -- Define standard show times for the day
        show_time_1 := NOW()::date + TIME '10:00:00';
        show_time_2 := NOW()::date + TIME '19:00:00';

        -- Base price logic
        IF screen_id IN (7,8,9) THEN
            base_price := 450.00;  -- IMAX premium
        ELSE
            base_price := 250.00;
        END IF;

        -- Insert morning show
        INSERT INTO show (movie_id, screen_id, start_time, end_time, base_price)
        VALUES (movie_id, screen_id, show_time_1, show_time_1 + runtime, base_price);

        -- Insert evening show
        INSERT INTO show (movie_id, screen_id, start_time, end_time, base_price)
        VALUES (movie_id, screen_id, show_time_2, show_time_2 + runtime, base_price);
    END LOOP;
END $$;

