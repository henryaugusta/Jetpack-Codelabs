package com.feylabs.firrieflix.util.data_seed

import com.feylabs.firrieflix.data.source.local.FavoriteEntity
import com.feylabs.firrieflix.data.source.local.MovieLocalEntity
import com.feylabs.firrieflix.data.source.local.ShowLocalEntity
import com.feylabs.firrieflix.data.source.remote.responses.*

object Seeder {

    fun getDummyCast(): List<MovieCreditsResponse.Cast> {
        return listOf(
            MovieCreditsResponse.Cast(
                adult = false,
                gender = 2,
                id = 1461,
                known_for_department = "Acting",
                name = "George Clooney",
                original_name = "George Clooney",
                popularity = 5.99,
                profile_path = "/kHiVY6r1k6juXrNetAYk2jILqn9.jpg",
                cast_id = 18,
                character = "Frank Walker",
                credit_id = "54cd3fae9251414757001c65",
                order = 0
            ),
            MovieCreditsResponse.Cast(
                adult = false,
                gender = 1,
                id = 52018,
                known_for_department = "Acting",
                name = "Britt Robertson",
                original_name = "Britt Robertson",
                popularity = 7.126,
                profile_path = "/6lnipeQ6auGaxn4gQ8UX68xoxOy.jpg",
                cast_id = 17,
                character = "Casey Newton",
                credit_id = "54cd3f9b925141475d001bc8",
                order = 2
            )
        )
    }

    fun getShow(): List<ShowResponse.Result> {
        return listOf(
            ShowResponse.Result(
                first_air_date = "2021-05-07",
                poster_path = "/9yxep7oJdkj3Pla9TD9gKflRApY.jpg",
                id = 93484,
                original_name = "Jupiter's Legacy",
                origin_country = listOf(
                    "US"
                ),
                name = "Jupiter's Legacy",
                vote_count = 187,
                vote_average = 7.5,
                backdrop_path = "/4YKkS95v9o9c0tBVA46xIn6M1tx.jpg",
                overview = "When the world's first generation of superheroes received their powers in the 1930s become the revered elder guard in the present, their superpowered children struggle to live up to the legendary feats of their parents.",
                genre_ids = listOf(
                    10765,
                    10759,
                    18
                ),
                original_language = "en",
                popularity = 1051.234,
                media_type = "tv"
            ),

            ShowResponse.Result(
                poster_path = "/ rlrRI2b6RvM9I9xOBTKqcTaehkE.jpg ",
                vote_average = 0.2,
                id = 6831,
                original_name = "Love, Death & Robots ",
                origin_country = listOf(
                    "US"
                ),
                vote_count = 1026,
                overview =
                "Terrifying creatures, wicked surprises and dark comedy converge in this NSFW anthology of animated stories presented by Tim Miller and David Fincher.",
                name = "Love, Death & Robots",
                first_air_date = "2019 - 03-15",
                genre_ids = listOf(
                    16,
                    10765
                ),
                backdrop_path = "/daXzoOWNBwSoG03RFh5tEqzl1sH.jpg",
                original_language = "en",
                popularity = 76.476,
                media_type = "tv"
            )
        )
    }

    fun getLocalShow(): List<ShowLocalEntity> {
        return listOf(
            ShowLocalEntity(
                first_air_date = "2021-05-07",
                poster_path = "/9yxep7oJdkj3Pla9TD9gKflRApY.jpg",
                id = 93484,
                original_name = "Jupiter's Legacy",
                name = "Jupiter's Legacy",
                vote_count = 187,
                vote_average = 7.5,
                backdrop_path = "/4YKkS95v9o9c0tBVA46xIn6M1tx.jpg",
                overview = "When the world's first generation of superheroes received their powers in the 1930s become the revered elder guard in the present, their superpowered children struggle to live up to the legendary feats of their parents.",
                original_language = "en",
                popularity = 1051.234,
                media_type = "tv"
            ),

            ShowLocalEntity(
                poster_path = "/ rlrRI2b6RvM9I9xOBTKqcTaehkE.jpg ",
                vote_average = 0.2,
                id = 6831,
                original_name = "Love, Death & Robots ",
                vote_count = 1026,
                overview = "Terrifying creatures, wicked surprises and dark comedy converge in this NSFW anthology of animated stories presented by Tim Miller and David Fincher.",
                name = "Love, Death & Robots",
                first_air_date = "2019 - 03-15",
                backdrop_path = "/daXzoOWNBwSoG03RFh5tEqzl1sH.jpg",
                original_language = "en",
                popularity = 76.476,
                media_type = "tv"
            )
        )
    }

    fun getLocalMovies(): List<MovieLocalEntity> {
        return listOf(
            MovieLocalEntity(
                original_language = "en",
                original_title = "Mortal Kombat",
                poster_path = "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg",
                id = 460465,
                vote_average = 7.6,
                overview = "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                release_date = "2021-04-07",
                vote_count = 2459,
                adult = false,
                backdrop_path = "/6ELCZlTA5lGUops70hKdB83WJxH.jpg",
                video = false,
                title = "Mortal Kombat",
                popularity = 3477.711,
                media_type = "movie"
            ),

            MovieLocalEntity(
                original_language = "en",
                original_title = "The Woman in the Window",
                poster_path = "/wcrjc1uwQaqoqtqi67Su4VCOYo0.jpg",
                id = 520663,
                vote_average = 6.3,
                overview = "An agoraphobic woman living alone in New York begins spying on her new neighbors only to witness a disturbing act of violence.",
                release_date = "2021-05-14",
                vote_count = 315,
                video = false,
                adult = false,
                backdrop_path = "/gUttUEqsrvaMlK5oL5TSQ54iE96.jpg",
                title = "The Woman in the Window",
                popularity = 180.11,
                media_type = "movie"
            ),
        )
    }

    fun getFavoriteMovies(): List<FavoriteEntity> {
        return listOf(
            FavoriteEntity(
                id = 1,
                movId = 460465,
                favType = 1,
                name = "Mortal Kombat",
                desc = "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                imgPreview = "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg"
            ),

            FavoriteEntity(
                id = 2,
                movId = 520663,
                favType = 1,
                name = "The Woman in the Window",
                desc = "An agoraphobic woman living alone in New York begins spying on her new neighbors only to witness a disturbing act of violence.",
                imgPreview = "/wcrjc1uwQaqoqtqi67Su4VCOYo0.jpg",
            )
        )
    }

    fun isMovieFavorited(): List<FavoriteEntity> {
        return listOf(
            FavoriteEntity(
                id = 2,
                movId = 520663,
                favType = 1,
                name = "The Woman in the Window",
                desc = "An agoraphobic woman living alone in New York begins spying on her new neighbors only to witness a disturbing act of violence.",
                imgPreview = "/wcrjc1uwQaqoqtqi67Su4VCOYo0.jpg",
            )
        )
    }

    fun getFavoriteShows(): List<FavoriteEntity> {
        return listOf(
            FavoriteEntity(
                id = 3,
                movId = 93484,
                favType = 2,
                name = "Jupiter's Legacy",
                desc = "When the world's first generation of superheroes received their powers in the 1930s become the revered elder guard in the present, their superpowered children struggle to live up to the legendary feats of their parents.",
                imgPreview = "/9yxep7oJdkj3Pla9TD9gKflRApY.jpg",
                ),

            FavoriteEntity(
                id = 4,
                movId = 6831,
                favType = 2,
                name = "Love, Death & Robots ",
                desc = "Terrifying creatures, wicked surprises and dark comedy converge in this NSFW anthology of animated stories presented by Tim Miller and David Fincher.",
                imgPreview = "/daXzoOWNBwSoG03RFh5tEqzl1sH.jpg",
                )
        )
    }

    fun getMovies(): List<MovieResponse.Result> {
        return listOf(
            MovieResponse.Result(
                original_language = "en",
                original_title = "Mortal Kombat",
                poster_path = "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg",
                id = 460465,
                vote_average = 7.6,
                overview = "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                release_date = "2021-04-07",
                vote_count = 2459,
                adult = false,
                backdrop_path = "/6ELCZlTA5lGUops70hKdB83WJxH.jpg",
                video = false,
                genre_ids = listOf(
                    28,
                    14,
                    12
                ),
                title = "Mortal Kombat",
                popularity = 3477.711,
                media_type = "movie"
            ),

            MovieResponse.Result(
                genre_ids = listOf(
                    80,
                    18,
                    9648,
                    53
                ),
                original_language = "en",
                original_title = "The Woman in the Window",
                poster_path = "/wcrjc1uwQaqoqtqi67Su4VCOYo0.jpg",
                id = 520663,
                vote_average = 6.3,
                overview = "An agoraphobic woman living alone in New York begins spying on her new neighbors only to witness a disturbing act of violence.",
                release_date = "2021-05-14",
                vote_count = 315,
                video = false,
                adult = false,
                backdrop_path = "/gUttUEqsrvaMlK5oL5TSQ54iE96.jpg",
                title = "The Woman in the Window",
                popularity = 180.11,
                media_type = "movie"
            ),
        )
    }

    fun getDetailMovie(): MovieDetailResponse {
        return MovieDetailResponse(
            adult = false,
            backdrop_path = "/jFINtstDUh0vHOGImpMAmLrPcXy.jpg",
            belongs_to_collection = "3",
            budget = 5000000,
            genres = listOf(
                MovieDetailResponse.Genre(id = 28, "Action"),
                MovieDetailResponse.Genre(id = 28, "Action"),
                MovieDetailResponse.Genre(id = 28, "Action"),
            ),
            homepage = "",
            id = 643586,
            imdb_id = "tt8114980",
            original_language = "en",
            original_title = "Willy's Wonderland",
            overview = "",
            popularity = 962.478,
            poster_path = "/keEnkeAvifw8NSEC4f6WsqeLJgF.jpg",
            production_companies = listOf(
                MovieDetailResponse.ProductionCompany(831, "/3ef.jpg", "Saturn Films", "US")
            ),
            production_countries =
            listOf(MovieDetailResponse.ProductionCountry("US", "United States Of America")),
            release_date = "2021-02-12",
            revenue = 0,
            runtime = 88,
            spoken_languages = listOf(
                MovieDetailResponse.SpokenLanguage(
                    "Spanish",
                    "es",
                    "Espanol"
                ),
                MovieDetailResponse.SpokenLanguage(
                    "English",
                    "en",
                    "English-en"
                ),
            ),
            status = "Released",
            tagline = "",
            title = "Willy's Wonderland",
            video = false,
            vote_average = 6.8,
            vote_count = 210
        )
    }


    fun getDetailShow(): ShowDetailResponse {
        return ShowDetailResponse(
            backdrop_path = "/4KkS95v9o9c0tBVA46xIn6M1tx.jpg",
            created_by = listOf(
                ShowDetailResponse.CreatedBy(
                    id = 1213074,
                    credit_id = "5fe4946031644b0040f84e04",
                    name = "Steven S. DeKnight",
                    gender = 2,
                    profile_path = "/5AsVXNHJTqhxfwxfhxKe7eeKMUu.jpg"
                )
            ),
            episode_run_time = listOf(45),
            first_air_date = "2021-05-07",
            genres =
            listOf(
                ShowDetailResponse.Genre(10765, "Sci-fi & Fantasy"),
                ShowDetailResponse.Genre(10759, "Action & Adventure"),
            ),
            homepage = "https://www.netflix.com/title/80244953",
            id = 93484,
            in_production = true,
            languages =
            listOf("en"),
            last_air_date = "2021 - 05 - 07",
            last_episode_to_air =
            ShowDetailResponse.LastEpisodeToAir(
                air_date = "2021-05-07",
                episode_number = 8,
                id = 2584271,
                name = "",
                overview = "",
                production_code = "",
                season_number = 1,
                still_path = "/nsyd0XggnfX0STjJTTxth7zk6hk.jpg",
                vote_average = 0.0,
                vote_count = 0
            ),
            name = "Jupiter's Legacy",
            next_episode_to_air = 900,
            networks =
            listOf(
                ShowDetailResponse.Network(
                    213,
                    "Netflix",
                    "//wwemzKWzjKYJFfCeiB57q3r4Bcm.png",
                    "EN"
                )
            ),
            number_of_episodes = 8,
            number_of_seasons = 1,
            origin_country = listOf("US"),
            original_language = "en",
            original_name = "Jupiter's Legacy",
            overview = "",
            popularity = 1051.234,
            poster_path = "/9yxep7oJdkj3Pla9TD9gKflRApY.jpg",
            production_companies =
            listOf(
                ShowDetailResponse.ProductionCompany(
                    435,
                    "/AjzK0s2w1GtLfR4hqCjVSYi0Sr8.png",
                    "Di Bonaventura Pictures",
                    "US"
                )
            ),
            production_countries =
            listOf(ShowDetailResponse.ProductionCountry("US", "United States Of America")),
            seasons = listOf(
                ShowDetailResponse.Season(
                    air_date = "2021-05-07",
                    episode_count = 8,
                    id = 132121,
                    name = "Musim ke 1",
                    overview = "",
                    poster_path = "/gSFDXb6IMXDSdw4avtzCVWouIfk.jpg",
                    season_number = 1
                )
            ),
            spoken_languages = listOf(
                ShowDetailResponse.SpokenLanguage(
                    "Spanish",
                    "es",
                    "Espanol"
                ),
                ShowDetailResponse.SpokenLanguage(
                    "English",
                    "en",
                    "English-en"
                ),
            ),
            status = "Returning Series",
            tagline = "",
            type = "Scripted",
            vote_average = 7.5,
            vote_count = 195
        )

    }
}

