# UNO Game Project

## Object Oriented Programming
A multiplayer UNO game built with a server-side architecture and MVC design pattern, emphasizing Object-Oriented Programming principles.

### Description 
This project is a digital version of the classic card game UNO, designed for multiplayer gameplay over the internet. The game allows players to join a lobby, play against each other in real-time, and experience the fun of UNO from the comfort of their own homes. 

The architecture of the game is based on the Model-View-Controller (MVC) pattern, which separates the game logic, user interface, and user input handling. This separation of concerns makes the codebase more manageable, scalable, and easier to maintain.

### OOP Principles
The project utilizes key Object-Oriented Programming principles, including:

- **Encapsulation**: Game logic and player data are encapsulated within classes, ensuring that the internal state is protected and only accessible through defined methods.
- **Inheritance**: The game features a class hierarchy where specific card types (e.g., WildCard, NumberCard) inherit from a base Card class, promoting code reuse and reducing redundancy.
- **Polymorphism**: The game employs polymorphism to allow different card types to implement their own behavior for actions like playing a card or drawing from the deck.
- **Abstraction**: Abstract classes and interfaces are used to define common behaviors for game components, allowing for flexibility and easier maintenance.

### Design Patterns
The project also incorporates several design patterns to enhance its architecture:

- **Singleton Pattern**: Used for managing the game state, ensuring that only one instance of the game controller exists throughout the application.
- **Observer Pattern**: Implemented to notify players of game state changes, such as when a player takes a turn or when the game ends.
- **Factory Pattern**: Utilized for creating different types of cards dynamically, allowing for easy expansion of card types in the future.

### Results 

### created by [@aguilarcarboni](https://github.com/aguilarcarboni/), [EMurillo7](https://github.com/EMurillo7) and [@leomessi](https://github.com/leomessi)