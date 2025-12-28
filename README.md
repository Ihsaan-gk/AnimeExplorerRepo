# 🎌 AnimeExplorer

AnimeExplorer is a simple yet robust Android application.
The app fetches anime data using the **Jikan API** and allows users to browse top anime titles, view detailed information, and watch trailers when available.

---

## 📱 Features

### Anime List Screen
- Fetches **Top Anime** from Jikan API
- Displays:
  - Anime title
  - Number of episodes
  - Rating (MyAnimeList score)
  - Poster image
- Supports pagination
- Handles loading & error states gracefully

### Anime Detail Screen
- Displays:
  - Title
  - Synopsis
  - Genres
  - Episodes count
  - Rating
- Trailer handling:
  - Plays trailer when available (via WebView)
  - Shows poster image when trailer is unavailable
  - Play icon overlay on poster for better UX
- Proper back navigation (WebView closes before fragment)

### Offline Support (Bonus)
- Caches anime list data locally using **Room Database**
- App works in offline mode when cached data is available
- Shows appropriate error state when offline with no cached data

---

## 🛠 Tech Stack

- **Language:** Kotlin
- **Architecture:** MVVM + Repository Pattern
- **Networking:** Retrofit + OkHttp
- **Local Storage:** Room Database
- **Dependency Injection:** Hilt
- **Async:** Coroutines + StateFlow
- **UI:** XML + ViewBinding
- **Media:** ExoPlayer, WebView
- **Navigation:** Jetpack Navigation Component

---

## 📡 API Used

- **Top Anime** : https://api.jikan.moe/v4/top/anime
- **Anime Details** : https://api.jikan.moe/v4/anime/{anime_id}

---

## 📦 APK

- The installable APK is available under **GitHub Releases**
- Download the ZIP, extract it, and install the APK on an Android device
- Minimum supported Android version: **API 24 (Android 7.0)**

---

## 🧠 Assumptions Made

- Trailer playback uses embedded YouTube URLs when direct video URLs are unavailable
- Offline mode is supported for anime list data (details require network unless cached)
- UI prioritizes clarity and stability over heavy animations
- XML-based UI was chosen for better control and interview clarity

---

## ⚠️ Known Limitations

- Anime detail offline caching is limited
- Trailer playback depends on availability of embed URLs
  
---

## 🚀 How to Run the Project

1. Clone the repository:
 git clone https://github.com/Ihsaan-gk/AnimeExplorerRepo.git
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or physical device

---

## 👤 Author

Muhammed Ihsaan
Android Developer
📍 India

