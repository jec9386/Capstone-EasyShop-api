# EasyShop E-Commerce Website

This is a full-stack e-commerce REST API built with Java, Spring Boot, and MySQL. It features user authentication, product management, category selection, shopping cart functionality, and user profiles. The objective of this project is to build on top of a non-operational and published Version 1 codebase and transform it into a functioning ecommerce platform. In Version 2, many bugs and new features were added to bring the website to life, such as creating a shopping cart, and being able to filter item searches.

## 🛍️ Features
- ✅ **Product Management** - CRUD operations with search and filtering
- ✅ **Category Management** - Organize products by categories
- ✅ **User Authentication** - JWT-based secure login/registration
- ✅ **Shopping Cart** - Cart remembers items across logins
- ✅ **User Profiles** - Personal information management
- ✅ **Role-Based Security** - Admin vs User permissions
- ✅ **Advanced Search** - Filter by category, price range, and color

## 🗄️ Database Schema
![Image](https://github.com/user-attachments/assets/9d1a395c-4e4d-469d-832c-553f16a58130)

## 🧪 Test Scenarios- using Postman

### Scenario 1: Complete User Shopping Journey
**Objective:** Test the full e-commerce workflow from user registration to browsing categories and products.
![Image](https://github.com/user-attachments/assets/710e8abc-420f-4f61-bbe0-5dd65c256390)

### Scenario 2: Shopping Cart User Experience
**Objective:** Test shopping cart functionality that's only available to logged-in users, including add, update, and clear operations.
![Image](https://github.com/user-attachments/assets/2cf86910-c150-4566-a1fa-d0a037e49e2e)

### Scenario 3: Shopping Cart JSON Response
**Objective:** Validate the shopping cart JSON structure and data format returned by the API.
![Image](https://github.com/user-attachments/assets/cf3b5fa3-14a0-4a53-852c-55bb43ab747c)

## 💡 Interesting Code
One of the most interesting pieces of code is the smart add-to-cart functionality that automatically decides whether to create a new cart item or update an existing one. It prevents the same product from being listed twice in the cart. This feature is designed with user convenience in mind, ensuring a smooth shopping experience:

![Image](https://github.com/user-attachments/assets/5a34d971-5d1a-45c1-bb7e-e66427305912)















