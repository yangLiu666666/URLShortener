# URLShortener
•	Shorten a URL
o	Input: A regular URL (not from applau.se domain)
o	Output: A shortened URL (use only ten digits, 26 lowercase characters, 26 uppercase characters) of extra length 2 from a given link (example: applau.se/5s)
o	Handle the case that the 2-character length is running out of choices by retiring the shortened URL that has not been called for the longest time


•	Retrieve a URL
o	Input: A shortened URL (from applau.se domain)
o	Output: Retrieve the original URL


•	Basic admin
o	Show all stored shortened URLs (including shortened URL, original URL, call count and latest call time) and sort by call count

Solution:
I solved this assignment by using double linked list and HashMap. I encapsulated the attributes of each URL including the original URL, the short URL, the call count of the URL, and the call time of the URL into a linked list node. If the space is full, I remove the infrequently called node from the tail. If you are very interested in the solution, or if you have any questions, please do not hesitate to contact me.
