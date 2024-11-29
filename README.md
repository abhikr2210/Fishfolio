# Fishfolio -  IoT Enabled Pisciculture Monitoring Application

Fishfolio is an Android-based application designed for real-time monitoring and management of fish tanks. It integrates with sensors to collect vital data, such as water quality and temperature, and uploads the data to Firebase for synchronization. The app ensures that users receive immediate alerts if dangerous conditions are detected, ensuring the health and safety of the aquatic life in the tank.

## Features

- **Real-Time Monitoring**: Continuously collects data from sensors in the fish tank (e.g., water quality, temperature) and uploads it to Firebase, updating the app every second.
- **Firebase Integration**: Real-time synchronization between sensors and the app, ensuring users get up-to-date information on their tank.
- **User Authentication**: Secure login functionality via Firebase Authentication to protect user data and ensure only authorized access.
- **Alerts & Notifications**: Sends instant notifications and phone alarms if dangerous conditions (like abnormal water temperature or poor water quality) are detected in the tank.
- **Android App**: A user-friendly interface that displays real-time data, such as temperature, pH levels, and water quality, along with management features for tank control.

## Technology Used

- **XML**: For creating the layout and user interface of the Android application.
- **Java**: The primary programming language used for implementing the functionality of the app.
- **Firebase**:
  - **Firebase Realtime Database**: For storing and syncing the sensor data in real-time.
  - **Firebase Authentication**: To provide secure user authentication and management.
  - **Firebase Cloud Messaging**: For sending alerts and notifications to users based on real-time data.
- **Android SDK**: To build and run the application on Android devices.

## Screenshots


## How to Run the Project

1. **Clone the Repository**  
   Clone this repository to your local machine using the command:
   ```bash
   git clone https://github.com/abhikr2210/fishfolio.git
