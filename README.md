# MoviesApp
This Project is done in two stages :
PROJECT SPECIFICATION
Popular Movies, Stage 1

User Interface - Layout

MEETS SPECIFICATIONS
Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.

UI contains an element (i.e a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.

UI contains a screen for displaying the details for a selected movie.

Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.

User Interface - Function

MEETS SPECIFICATIONS
When a user changes the sort criteria (“most popular and highest rated”) the main view gets updated correctly.

When a movie poster thumbnail is selected, the movie details screen is launched.

Network API Implementation

MEETS SPECIFICATIONS
In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.

General Project Guidelines

MEETS SPECIFICATIONS
App conforms to common standards found in the Android Nanodegree General Project Guidelines (NOTE: For Stage 1 of the Popular Movies App, it is okay if the app does not restore the data using onSaveInstanceState/onRestoreInstanceState)

Here is Code Review:
https://review.udacity.com/#!/reviews/223464


Project Stage 2
User Interface - Layout

UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.
Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
UI contains a screen for displaying the details for a selected movie.
Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
Movie Details layout contains a section for displaying trailer videos and user reviews.
Tablet UI uses a Master-Detail layout implemented using fragments. The left fragment is for discovering movies. The right fragment displays the movie details view for the currently selected movie.
User Interface - Function

When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.
When a movie poster thumbnail is selected, the movie details screen is launched.
When a trailer is selected, app uses an Intent to launch the trailer.
In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite.
Network API Implementation

In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.
App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.
App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.
Data Persistence

App saves a "Favorited" movie to SharedPreferences or a database using the movie’s id.
When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie IDs stored in SharedPreferences or a database.
Code review By reviewer:
https://review.udacity.com/#!/reviews/221037

Screenshots
[![Screenshot_2016-09-04-14-59-30-509_com.example.b.png](https://s2.postimg.org/f9noyxhkp/Screenshot_2016-09-04-14-59-30-509_com.example.b.png)](https://postimg.org/image/xcgrq5df9/)
[![Screenshot_2016-09-04-15-01-05-176_com.example.b.png](https://s13.postimg.org/47dbzdfmf/Screenshot_2016-09-04-15-01-05-176_com.example.b.png)](https://postimg.org/image/snvhtugcz/)
[![image.png](https://s1.postimg.org/4c3eqx63j/image.png)](https://postimg.org/image/woywhdrtn/)
[![image.png](https://s17.postimg.org/q2ne1zra7/image.png)](https://postimg.org/image/p0d7jg8gr/)
[![image.png](https://s17.postimg.org/fpssx308f/image.png)](https://postimg.org/image/ar5aijwff/)
[![image.png](https://s17.postimg.org/9tbyu6nan/image.png)](https://postimg.org/image/e2gowcqjv/)
[![image.png](https://s13.postimg.org/w1lvps04n/image.png)](https://postimg.org/image/kp9a7zrfn/)
[![image.png](https://s13.postimg.org/aqqsquz9z/image.png)](https://postimg.org/image/dkty4b1g3/)
[![image.png](https://s9.postimg.org/ojmj3u4of/image.png)](https://postimg.org/image/3zhp5cox7/)
[![image.png](https://s21.postimg.org/5dpzp3qtj/image.png)](https://postimg.org/image/xdu39du9v/)
[![image.png](https://s18.postimg.org/gjidx6ae1/image.png)](https://postimg.org/image/6yyraal1x/)
