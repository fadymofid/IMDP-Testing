# IMOP-testing-gui
Assignment Description:
Part 1:
Selenium + Spring unit Test

If you choose Robot:
Folder named Part1 containing one TestSuite.robot file.
If you choose Seleniumn:
Folder named Part1 containing java project using selenium to cover the below scenarios.
Zip Part1, Part2 folders and upload them on Classroom with a file named:

Scenario 1: Verify user can search for a movie on the IMDb homepage
● Given that the user is on the IMDb homepage "https://www.imdb.com/"
● When the user enters a search query "The Shawshank Redemption" (or any other movie
name) in the search bar.
● And clicks the search button
● Then the search results page should display movies related to the search query.
● And the first search result should be "The Shawshank Redemption" movie i.e. the name
written in the search query.

● Given that the user is on the IMDb homepage "https://www.imdb.com/".
● When the user clicks on the "Top 250 Movies" link in the header.
● Then the user should be directed to the Top 250 Movies movies section page.
● And the page should display a list of the Top 250 Movies.
● And the first movie in the list should be "The Shawshank Redemption"

● Given that the user is on the IMDb homepage "https://www.imdb.com/"
● When the user clicks on the "Advanced Search" link in the search bar filter
● The user would then be redirected to a page containing “Advanced Title Search” link,
which he should click.
● And selects "Feature Film" as title type
● And selects the “Action” genre from Genres.
● And enters a start year and end year in the "Release Date" fields (2010 - 2020).
● And clicks the "Search" button
● Then the search results page should display a list of Action movies released between
2010 and 2020, sorted by User Rating (Higher ratings appear first, i.e. descendingly).

2

Scenario 2: Verify user can access the top-rated movies section

Scenario3: Verify user can search for movies released in a specific year on IMDb (bonus)

Part 2:
For the second part of the assignment you will find a zip file containing code for a TODO list developed in
spring boot.
write unit test cases using Junit, and any necessary libraries you need, to
make test coverage for the applicaion, and use any of the coverage techniques we studied to ensure more
coverage for the provided code.
And make sure that there is no overlap between test cases, or redundant ones.
Coverage should be at least 80% of the code. Also, if any code changes are required that will help you
in writng test cases, feel free to do it, and highlight these changes either in comments or in a separate .txt file
