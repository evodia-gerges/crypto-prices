# Crypto Currency Viewer 📊

An Android app to view real-time cryptocurrency prices in **USD** and **SEK**, with dark/light theme support and detailed controls via Settings.

---

## 🚀 Features

- 🔄 **Live Crypto Prices**
    - Fetches and displays a list of crypto currencies.
    - Default currency is **USD**, switchable to **SEK**.
    - Data is **cached in runtime** to reduce API calls.

- 🎨 **Dark and Light Mode**
    - Follows Material 3 design guidelines.
    - Fully responsive for accessibility compliance.

- 🔍 **Search**
    - Quickly lookup cryptocurrencies by symbol.

- ⚙️ **Settings**
    - Set default currency (**USD** or **SEK**).
    - Choose sorting order: Default / Ascending / Descending.
    - All preferences saved via **SharedPreferences**.

- 📄 **Details Screen**
    - View additional information about selected cryptocurrencies.

---

## 💱 Currency Formatting

The app formats **USD** and **SEK** prices according to the active **locale**, with precision up to **8 decimal places**.

### 📌 Formatting Examples:

| Locale        | USD Format  | SEK Format   |
|---------------|-------------|--------------|
| `en-US`       | `$10.50`    | `SEK 130.75` |
| `sv-SE`       | `10.50 US$` | `130.75 kr`  |

- Uses built-in locale-aware formatting to ensure accessibility and user-friendliness.
- Prices are consistently formatted for international users.


---

## 🔧 Tech Stack

| Layer         | Tools & Libraries                   |
|---------------|-------------------------------------|
| Architecture  | MVVM, Clean Architecture            |
| Dependency Injection | [Koin](https://insert-koin.io/)     |
| UI            | Jetpack Compose, Material 3         |
| Navigation    | `NavHost`                           |
| Storage       | SharedPreferences                   |
| Networking    | Retrofit, JSON parsing              |
| Testing       | JUnit4, MockK, Kotlin Coroutines Test |

---

## 🌐 APIs Used

- **Crypto Prices**: [`https://api.wazirx.com/sapi/v1/tickers/24hr`](https://api.wazirx.com/sapi/v1/tickers/24hr)
- **Currency Exchange Rates**: [`https://api.frankfurter.app/latest`](https://api.frankfurter.app/latest)

---

## 📲 Screens Overview

- **Home Screen**
    - Displays crypto list with:
        - Truncated base asset (max 4 characters)
        - Symbol
        - Latest price (in USD/SEK)
    - Button to toggle USD/SEK on the fly.

- **Search Screen**
    - Find cryptos by typing part of the symbol.

- **Details Screen**
    - Displays more info per cryptocurrency.

- **Settings Screen**
    - Currency and sorting preferences.


---

## 📁 Project Structure

📦 app/

├── data/          # API, repository, model classes

├── domain/        # Use cases and business logic

├── ui/            # Compose UI and screens

├── di/            # Koin DI modules

├── utils/         # Shared utilities

└── MainActivity.kt

---

## 📦 Build & Run
Clone the repo:

bash
git clone https://github.com/evodia-gerges/crypto-prices.git

cd crypto-prices

Open in Android Studio (Meerkat+ recommended).

Hit Run ▶️

---

## 🧪 Testing

Unit tests are written using:
- `JUnit4`
- `MockK`
- `Kotlinx.coroutines.test`
- `kotlin.test`

To run tests:

```bash
./gradlew test
