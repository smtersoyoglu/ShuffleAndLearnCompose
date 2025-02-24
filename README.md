# ShuffleAndLearnCompose App

ShuffleAndLearnCompose is a dynamic and interactive educational mobile application designed to make vocabulary learning enjoyable, effective, and flexible. The app encourages users to learn words in a non-linear way by leveraging a shuffle feature that randomizes the display of vocabulary cards. This ensures users engage with words in different sequences, promoting deeper learning and preventing rote memorization.

Built using modern Android technologies like Jetpack Compose, Room, and Retrofit, the app adheres to Clean Architecture principles with MVVM and MVI design patterns for better scalability and maintainability.

---

## Features

### Splash Screen
- **Functionality:** Displays a splash screen for 4 seconds when the app launches.
- **Purpose:** Provides a smooth transition into the app and sets up initial loading.

### Home Screen (Main)
- **Vocabulary Card Display:** Shows a list of 50 English words, each with a Turkish translation and an associated image.
- **The home screen lists vocabulary cards fetched using Retrofit. Each card includes:**
  - The word's English and Turkish translations,
  - An image representing the word.
- **Shuffle Feature:**
  - Users can shuffle the cards to view them in different random orders, encouraging learning without relying on static sequences.
- **Navigation to Detail Screen:**
  - Tapping on a card takes the user to the detail screen for more in-depth information.

### Detail Screen (Word Detail View)
- **Word Details:** Provides detailed information about a selected word from the Home or Learned Words screen.
- **Displays the selected word's:**
  - English and Turkish translations,
  - Example usage in an English sentence.
  - Associated image.
- **Text-to-Speech:**
  - Users can listen to the correct pronunciation of the word.
- **Navigation Back:** 
  - Allows users to return to the previous screen (Home or Learned Words).
- **Learn Button:**
  - Marks a word as learned.
  - Removes the word from the Home Screen and adds it to the Learned Words screen.
  - This functionality is managed using the Room Database.

### Learned Words Screen
- **View Learned Words:** Lists words that have been marked as learned.
  - Displays all words marked as learned by the user.
  - Learned words are persistently stored in the local database (Room).
- **Navigation to Detail Screen:**
  - Tapping on a card takes the user to the detail screen for more in-depth information.
- **Integration with the Home Screen:**
  - The Home Screen only shows unlearned words to prevent repetition.

### Word Game Screen
- **Fun Vocabulary Game:** A timed game where users translate Turkish words into English.
  - Words are presented randomly alongside their images.
  - Users are prompted to type the correct English translation.
- **Feedback with Animations:**
  - Correct answer: A happy animation (happy.anim).
  - Incorrect answer: A sad animation (sad.anim).
- **Game Mechanics:**
  - Score Tracking: Keeps a record of correct answers.
  - Countdown Timer: Users must guess words within the given time limit.
  - End-of-Game Dialog: Displays:
    - The user’s final score,
    - An option to replay the game.

### AI ChatBot
- **Gemini AI Integration:**
  - Users can interact with an integrated AI chatbot to:
    - Ask questions,
    - Receive intelligent responses to enhance their learning experience.

### Notifications with WorkManager
- **Reminder Notifications:**
  - If the app is minimized for an extended period, WorkManager sends reminder notifications such as:
    - "That's enough of a break, get back to learning!"
  - This feature helps users maintain consistent learning habits.

---

## Project Architecture

The app follows Clean Architecture principles, ensuring separation of concerns and better maintainability. It utilizes both MVVM (Model-View-ViewModel) and MVI (Model-View-Intent) patterns to manage UI state, user interactions, and side effects in a scalable and testable way.

### Clean Architecture
- **Layered Architecture:** The app follows Clean Architecture with three main layers:
  - Presentation Layer: ViewModels and Compose UI components.
  - Domain Layer: Use cases and business logic.
  - Data Layer: Repositories for API and Room interactions.
- **Dependency Injection:** All dependencies are managed using Hilt for modular and testable code.

### MVVM Implementation

The app leverages MVVM (Model-View-ViewModel) for its core architecture:
-  View: Renders UI using Jetpack Compose, observing state from ViewModels.
-  ViewModel: Manages UI state and business logic, exposing data to the View.
-  Model: Represents data and business rules, independent of the UI.

### MVI Integration

To enhance the management of user interactions and side effects (e.g., navigation), the app integrates Model-View-Intent (MVI) architecture alongside MVVM. MVI introduces the following components:
-  UiState: Represents the immutable UI state of each screen (e.g., loading state, word list).
-  UiAction: A sealed class that handles user actions (e.g., clicking a word, shuffling cards).
-  UiEffect: A sealed class for one-time side effects (e.g., navigation, toast messages).


#### Screens Enhanced with MVI

-  WordDetailScreen: Uses DetailContract to define UiState, UiAction, and UiEffect. The WordDetailViewModel processes actions and emits effects.
-  WordMainScreen: Manages shuffle and reset operations through WordContract. Navigation is controlled via UiEffect.
-  WordGameScreen: Game logic and state are handled with GameContract.
-  LearnedWordsScreen: Displays learned words and navigation through LearnedContract.


#### Benefits of MVI

-  Centralized State Management: Each screen's state is managed immutably with UiState.
-  Decoupled Navigation: Navigation triggers are handled via UiEffect, ensuring the UI only renders state.
-  Improved Error Handling: Network and database operations are wrapped with safeFlow and safeCall.
-  Testability: Added PreviewProvider for each screen to support composable previews.

---

### Layered Structure

1. **Presentation Layer**:
   - **Jetpack Compose** for modern, declarative UI.
   - ViewModels handle UI state and logic using `State` and `Flow`.

2. **Domain Layer**:
   - **Use Cases** implement business logic. Each use case performs a single, focused task.
     - `CheckWordAnswerUseCase`: Validates if the user's answer is correct.
     - `FetchShuffledWordsUseCase`: Fetches and shuffles words for randomized learning.
     - `FetchWordsUseCase`: Fetches and saves words from a remote source.
     - `GetLearnedWordsUseCase`: Retrieves all learned words from the database.
     - `GetUnlearnedWordsUseCase`: Retrieves unlearned words from the database.
     - `GetWordByIdUseCase`: Fetches word details using its ID.
     - `MarkWordAsLearnedUseCase`: Marks a word as learned in the database.
     - `ResetGameUseCase`: Resets the word game state with shuffled words.
     - `ToggleWordLearnedStatusUseCase`: Toggles the "learned" status of a word between learned and unlearned.

3. **Data Layer**:
   - **Repositories** manage data sources (local and remote).
     - `WordRepositoryImpl` handles operations from Room Database and Retrofit.
   - **Mappers** convert between different layers (DTO ↔ Domain ↔ Entity):
     - Converts data between API (DTO), local database (Entity), and the domain layer (WordItem).

   #### Mapper Functions:
   - `fromDtoToDomain(dto: WordDto)`: Converts API data to the domain model.
   - `fromEntityToDomain(entity: WordEntity)`: Converts local database data to the domain model.
   - `fromDomainToEntity(domain: WordItem)`: Converts the domain model back to a local database entity.

---

## Key Components

- **Jetpack Compose:** For building modern and declarative UI.
- **Room Database:** For managing learned and unlearned words.
- **Retrofit:** For networking and data fetching.
- **Navigation Component:** For managing app navigation between screens.
- **Notifications:** For sending reminders to maintain consistent learning habits.
- **WorkManager:** For scheduling background tasks like notifications.
- **Gemini AI:** For the chatbot functionality.
- **Coroutines/Flow:** For asynchronous programming and reactive data handling.
- **Lottie Animations:** For engaging visual feedback in the Word Game screen.

---

## How to Run the Project

1. **Clone the repository:**

   ```bash
   git clone https://github.com/smtersoyoglu/ShuffleAndLearnCompose.git
-  **Open the project in Android Studio.**
-  **Run the app on an emulator or a physical device.**
-  **Enjoy your vocabulary learning journey!**

---

### Project Video


https://github.com/user-attachments/assets/2422f186-206a-4936-b87f-f406279dad42


---
### Screenshoots


<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/da3c5555-01ab-4cd7-a132-b596cb6c62a1" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/ab982a4e-6f94-4eb3-b42d-2813b7eeb7bd" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/ec0de627-9e8d-478f-9abc-2871edfec710" width="290"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/fe8df608-9a30-4aee-9ce3-bd281449e8cf" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/d6ee0e96-28f3-4cba-8de7-824d0127be63" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/ff87ef4f-b630-45d7-a02e-b22921e8d123" width="290"></td>
  </tr>
    <tr>
    <td><img src="https://github.com/user-attachments/assets/687ae62f-c549-4e60-9f4c-a67f428e23c9" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/9bd085e4-e6fb-48d2-b831-5cbedaafec10" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/d41cacf4-40f7-46cc-9ff0-8babf5553223" width="290"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/303c1fae-fe27-46ed-8039-ea82504248fb" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/f7ddf292-1276-49a7-ba1d-f2869be71de3" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/fa1300fb-5971-444c-b06a-974c5e5262cf" width="290"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/ab879465-57d7-4c12-8587-b376c7a9fb66" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/4ca25b95-2f16-473f-a6a4-076d068e132c" width="290"></td>
    <td><img src="https://github.com/user-attachments/assets/0803e8dc-642a-4f75-9688-801c2a76d9cd" width="290"></td>
  </tr>
</table>


