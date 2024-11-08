# FullStore Android - Java

A native Android e-commerce app built in Java, leveraging the same backend (Express, TypeScript, and MongoDB) as the FullStore web application.

## 📝 Description

FullStore Android is a Java-based native Android app for an e-commerce platform. It allows users to browse products, register, log in, and manage a shopping cart. It interacts with the FullStore backend, a Express.js API shared with the web version of the app.

## ✨ Features

- **Navigation**: Intuitive navigation using `BottomNavigationView`, `NavigationView`, `Adaptive menus optimized for different view`.
- **Shopping Cart**: Add, modify, and remove products.
- **User DashBoard**: Access account details, view purchase history, and manage personalized settings.
- **Messaging Service**: Provides real-time communication within the platform, enabling users to exchange messages seamlessly. This service typically includes features such as displaying user profile images, showing message timestamps, and organizing messages by conversations. The layout mimics popular chat interfaces for familiarity, with a clean and user-friendly design that likely leverages RecyclerView to efficiently load and display message threads. Each message may include details like sender information, timestamp, and the message text itself, creating an organized and accessible experience for users.
- **Authentication**: Register, log in, and manage sessions with token-based authentication.
- **Product Details**: View product information on click.
- **Shared Backend**: Integrates with [FullStore backend](https://github.com/emigillini/Full-Online-Shop-Angular-Node-Express.git).

## 📱 Technologies

- **Language**: Java (Android)
- **Architecture**: MVVM (Model-View-ViewModel) ensuring clear separation of business and presentation logic
- **API Client**: Retrofit with OkHttp, utilizing interceptors for secure HTTP requests
- **Session Management**: JSON Web Tokens (JWT) for authentication, with token storage in SharedPreferences for secure, persistent sessions
- **Token Handling**: OkHttp interceptors manage JWT injection into headers and handle token refresh as needed
- **Caching**: OkHttp caching enables improved network efficiency and offline support
- **UI Components**: Material Design principles, with RecyclerView for dynamic lists and detailed item views


## 🚀 Installation

### 1. Clone this repository:
-git clone https://github.com/emigillini/FullStore-Android-Java.git

### 2. Import into Android Studio
- Open Android Studio.
- Go to `File > Open`, and select the cloned project folder.

### 3. Backend Configuration
- Set up the backend on your local machine or a server using the [Full Online Shop backend](https://github.com/emigillini/Full-Online-Shop-Angular-Django.git) instructions.
- Update the Retrofit base URL if the backend is not hosted locally.

### 4. Configuration

#### API Base URL:
- Update the base URL for Retrofit in case the backend is hosted on a different server. 
  You can find the base URL configuration in the Retrofit setup files and change it as needed.

#### JWT Authentication:
- The app uses JSON Web Tokens (JWT) for session management. Ensure the backend supports JWT for secure authentication.

#### Session Management:
- The app securely stores JWT tokens using `SharedPreferences`. OkHttp interceptors handle attaching the tokens to requests automatically.

## Usage

### 1. User Registration and Login
  - User registration
  - Login and logout
  - Password reset with email
  - User authentication with JWT and Knox

### 2. Navigation
- The app uses `BottomNavigationView` and `NavigationView` to easily navigate between the product catalog, cart, profile, and messaging services.

### 3. Shopping Cart
- Users can browse products and add them to their shopping cart. Items can be viewed and managed in the cart section.
  - Create, read, update, and delete shopping carts
  - Add and remove products from the cart
  - Retrieve cart items

### 4. Messaging Service
- The app includes a messaging service where users can send and receive messages. JWT, OkHttp interceptors, and caching ensure secure and efficient communication.

### 5. Purchase Management:
  - Confirm purchases
  - Retrieve user purchases
  - Payment confirmation with Stripe and cash
  - Mail service, to confirm purchases and retrieve passwords

## Features

- **Architecture**: MVVM (Model-View-ViewModel) for clean separation of concerns.
- **API Client**: Retrofit for API calls and OkHttp for session management, interceptors, and caching.
- **UI**: Follows Material Design principles with components like `RecyclerView` to display dynamic lists and product details.
- **Performance**: Caching for efficient network usage and limited offline support.

## Authors

- [Emiliano Gillini](https://github.com/emigillini)

## Contact

For any questions or issues, please reach out to me through the following channels:

- **Email**: [emigillini@hotmail.com](mailto:emigillini@hotmail.com)
- **WhatsApp**: +54 9 351 718 3512

Thank you for checking out the project! https://wa.me/+5493517183512







