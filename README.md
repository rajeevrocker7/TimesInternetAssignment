# TimesInternetAssignment


android app with following functionality :
1) (activity 1) Create an activity for a list of products with thumbnails from API #1. (Should have
infinite scrolling: scrolling down should load more elements)
2) (activity 2) On click of any product: get details from API#2 and show the other information in
another view with a larger image, other information and schedule a notification after 2 minutes.

3) Notification should have a thumbnail image (JSON key i ) and a like button which will send a
request to API #2 and will take user to activity 2

// In App :
Main Activity: Used to show products list (api 1) ,

ItemInfo activity: Used to show details of product (api 2) ,

Retrofit 2: Used for restful Api ,

NotifyService : Used for Custom Notification.


@author Rajeev Kumar Sharma