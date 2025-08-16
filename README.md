# Expense Tracker Application

The Expense Tracker Android App is a user-friendly android application designed to help individuals manage their finances effectively. It allows users to record daily expenses, categorize them and analyze spending patterns through intuitive charts and reports. With features like intuitive charts and reports.


This project is made by me and my friends, Rajnish Kumar, Midhun MS and Aswin E for our college S6 Android team-mini-project.

## Technologies Used


<ul>
    <li><img src="https://skillicons.dev/icons?i=java" width="15" height="15" alt="JAVA" /> <b>Java (Java Android SDK)</b> - Buissness Logic and operations.</li>
    <li><img src="https://skillicons.dev/icons?i=androidstudio" width="15" height="15" alt="ANDROID STUDIO" /> <b>Android Studio</b> - IDE, Development and Testing</li>
    <li>📊 <b><a href="https://github.com/PhilJay/MPAndroidChart">MPAndroidChart Library</a></b> - For drawing Graphs and Reports</li>
    <li><img src="https://skillicons.dev/icons?i=sqlite" width="15" height="15" alt="SQLITE" /> <b>SQ Lite</b> - Storage (Database)</li>

</ul>

## Project Overview

### Core Features

<ul>
    <li>
    <b>User Friendly Interface 📱</b>
    <br>
    Simple and easy to understand Interface. No Learning Curve.
    </li>
    <li>
    <b>User Dashboard 📟</b>
    <br>
    Uses a  simple dashboard with quick summary and quick access buttons.
    </li>
    <li>
    <b>Expense and Income Tracking 💵</b>
    <br>
    User can add, edit or delete their expenses and incomes.
    </li>
    <li>
    <b>Automatic Reporting 📊</b>
    <br>
    Generates reports with graphs that show user's spending trends automatically.
    </li>
    <li>
    <b>Multi-User Support 👥</b>
    <br>
    The app can store multiple user accounts and their expenses. Maybe useful to some users.
    </li>
    <li>
    <b>Offline Storage 💾</b>
    <br>
    Data is stored in a local SQLite database, no need Internet.
    </li>
</ul>


### Implementation

#### Tables Used
<ul>
  <li><b>Users: </b>Stores the records of the user.</li>
  <li><b>Expense: </b>Records the expenses of the user.</li>
  <li><b>Income: </b>Records the income sources of the user.</li>
</ul>

#### MPAndroid Charting Library

MP Android Chart is a library which contains a set of predefined animated charts. This project uses some of the predefined charts for faster development.

#### Multithreading

Using Android's Executor Service, background data calculation, analysis and some crud operations are separated from the main thread for improving performance. This prevents freezing of the app while the new data is updated to the UI asynchronously. Multithreading is used to increase response time for Dashboard and Reports section. But as of now It is not tested with larger data.

#### XML Layout

Android's built in XML layout is used for UI part of the app. Various elements like ImageView, CardView, GridView, etc are utilised.


## How to run this app.

#### Download Compiled Project (apk)
Check the releases section of this repository to get steps to try out the pre-compiled application.

#### On Android Studio
Clone this repository on Android Studio and run. Make sure you are using Android API 35. The app is expected to run well on Android 13.

## App Authors (Collaborators)
- [Allan S Joseph](https://github.com/AllanSJoseph)
- [Rajnish Kumar](https://github.com/Rajnishtheone)
- [Midhun MS](https://github.com/Midhun-M-S5694)
- [Aswin E](https://github.com/Aswin-adwik)