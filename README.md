# PhotoApp
####Application Overview

When the PhotoApp is opened for the first time a user will see an empty ListView and a footer. The footer contains the text “Take a Photo”. Within the PhotoApp, users can take the following actions: 

    1. Take a photo
    2. View a list of photos taken previously
    3. View the full-sized photo

#####Take a photo 
The user can take a photo by ‘clicking’ the “Take a Photo” footer. When the user clicks the footer they will be brought to a camera application which already exists on the device. The user can capture a photo and then select to either retake the photo, exit the camera application or return to the PhotoApp.  

Upon returning to the PhotoApp from the camera application the user will see a thumbnail of the photo and the photo’s timestamp displayed in a ListView. The user can decided to select the “Take a Photo” footer again to take an additional photograph. When a new photograph is taken and the user returns to the PhotoApp - a new row will appear displaying the details of the most recent photo in the ListView. 

#####View a list of photos taken previously
The photo thumbnail and timestamp will be displayed for all photos that are taken using the PhotoApp. If the user leaves the application to complete another task and returns to PhotoApp - the user will see a list of the photos taken with the PhotoApp. 

#####View the full-sized photo
The user also has the option to view the full-sized image or “detailed” view of the photos they have taken. Selecting the desired photo row from the ListView will display the full-sized photo. The user can navigate back to the ListView from the detailed view by selecting the return button.

#####Code Comments
There are a lot of them! Take a deeper dive and check out the code. 
