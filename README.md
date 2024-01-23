#Dining Review API 
- Name: Luciano M
- Email: lumr0067@gmail.com

#Description
- Developed a RESTful API for dining reviews, enhancing user experience in restaurant selection and review management.
  
#Key features
- Admin Review Management: Admins can manage reviews, including accepting or rejecting them, which impacts restaurant scores.
- User Management: Users can create profiles, including allergy preferences for peanuts, eggs, and dairy products.
- Restaurant Information: Functionality to add and manage restaurant details, including cuisine type and price range.
- Review Submission: Allows users to submit reviews with scores for allergies, overall experience, and estimated spending.
- Restaurant Search: Features a search functionality that considers user allergy preferences and location.

#Database Schema
- User: Stores user information, including allergy preferences.
- Restaurant: Contains details about each restaurant, such as location, cuisine, and features.
- Review: Holds reviews submitted by users, linked to the respective restaurant and user.
- AdminReview: Managed by administrators to oversee the review process.
- Each table is interconnected, ensuring seamless data flow and integration.

#API Endpoints 
- GET /admin/restaurants: Retrieve a list of all restaurants.
- GET /admin/reviews: Fetch all reviews awaiting admin moderation.
- PUT /admin/reviews/{reviewId}: Update the status of a review by its ID.
- POST /restaurants: Add a new restaurant to the database.
- GET /restaurants/{id}: Retrieve details for a specific restaurant.
- GET /restaurants/search: Search for restaurants based on specified criteria.
- POST /reviews: Submit a new review for a restaurant.
- POST /users: Register a new user account.
- GET /users/userName: Fetch a user's profile by username.
- PUT /users/userName: Update a user's profile information.

#Beans
- AdminController: Handles administrative operations such as managing restaurant information and reviews.
- DinningReviewApifApplication: The main application class that bootstraps the API.
- RestaurantController: Processes requests related to restaurant data.
- RestaurantRepository: Manages restaurant data persistence.
- ReviewController: Coordinates the review submission and retrieval process.
- ReviewRepository: Manages storage and retrieval of review data.
- UserController: Responsible for user account management.

#Installation
- This section guides you through the process of setting up the Dining Review API on your local machine.

##Prerequisites

Before you begin, ensure you have met the following requirements:
- You have installed Java JDK version 21.
- You have installed Apache Maven 3.9.6.

# Testing

## Automated Tests

This project contains a suite of automated tests to ensure that the API functions correctly and to prevent regressions.

To run the automated tests, navigate to the project root directory and execute the following command:
- mvn test

#Manual Test
- Using Curl command
- Example:
  - CURL -X GET http://localhost:8080/users/userName -H "Content-Type: application/json"
