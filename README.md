# AndroidBarcodePriceComparison
Using post with php, search a barcode on google shopping to list them in android. (ANDROID STUDIO, AMPPS)

This is term project I did for my Mobile Application Development course.

It scans a barcode number from the camera of your phone and posts this to a .php file which is located in your windows. 
For this .php to work, ampps is used for localhost.

After this post, the barcode number is searched in google shopping and then the html source code is scraped from it.
The results are contained in a JSON and this JSON is posted back to application. Parsing through this JSON, the items name, image,
shop names and prices are printed in a listview.


EXAMPLE BARCODE (This barcode code will only work in Turkey!)

<img src="https://github.com/itsMami/AndroidBarcodePriceComparison/blob/master/ExampleBarcode.jpeg" width="250" height="300">
