# Survival game


## Table of contents
* [What is it?](#what-this-is-about)
* [Functionality](#functionality-in-game)
* [How to install](#how-to-install)
* [Manual](#manual-how-to-navigate-through-game)
* [Images](#images-from-game)
* [Fragments of code](#interesting-fragments-of-code)
* [Used technologies](#technologies-used-in-this-project)
* [License](#license)

## What this is about?

Similar game to sixty seconds, especially to the second part of it, a bit simpler. Make choices which will lead you to survive as many days as possible. Score are set based on days you survived. Feed people and keep them hydrated, send them on expedition to catch supplies and try to survive the next day! How many days are you able to achieve? Try yourself!

## Functionality in game

  1. Play the game! - that is the first and the most important functionality, during the game you can do actions like:
- Drink: hydrate current person!
- Eat: feed them, don't make them starving..
- Expedition: send current person out from shelter for  from 1 to 3 days in order to find some water and potato soups.
- Next person: change person you are do actions on (if this is the last person in shelter - next day executes).
- Next day: move on to the next day and update dialy diary.
- Clear diary: if you don't want to know what is going on and increase your user's experience by surprise - go ahead!
- Back to menu: go back to menu.
   
## How to install:
Download <a href="https://drive.google.com/file/d/1zAnKsU7nlMkMBwUG4NMKeM41ZXdLIsMJ/view?usp=sharing">Survival.jar</a> and open it with java.

# WARNING OF CLOSING
Do NOT close game by direct closing terminal (for example by clicking in the "X" in up-right corner), don't do it!

# Proper closing game
Close game by button "Quit game" in menu, otherwise music will NOT stop and you will need to close app by task manager. Unfortunatelly lanterna doesn't have solution for this.

## Manual how to navigate through game:
You will only need arrows (up and down) to choose actions from menu and action lists and enter to confirm it. 

## Images from game

![image](https://user-images.githubusercontent.com/39047457/140241333-3a7af4b4-b39b-4831-843d-1c03e63d9410.png)

![image](https://user-images.githubusercontent.com/39047457/140241277-63bf316b-9c2e-4990-85ac-4f4c2a267b4d.png)

![image](https://user-images.githubusercontent.com/39047457/140240571-04e47e57-237a-4430-a520-f8c964f5e773.png)

![image](https://user-images.githubusercontent.com/39047457/140240515-5ade5443-61fd-45db-a0ce-377dd496d44b.png)

![image](https://user-images.githubusercontent.com/39047457/140240831-4db5a9c0-8337-4576-a262-7181a478abb4.png)

## Interesting fragments of code:

- Refreshing only parts of view depending on need.
![image](https://user-images.githubusercontent.com/39047457/140242184-c093be56-f49c-4cfa-9b3d-33b01b490033.png)

- Fragment of code representing going for expedition.
![image](https://user-images.githubusercontent.com/39047457/140242502-64ea369b-9806-4530-b29a-872ea2f6e746.png)

- Using of "State pattern" in context of person's health.
![image](https://user-images.githubusercontent.com/39047457/140243563-cee3a08c-8100-438c-ac8f-7a0bfaae0ea3.png)
![image](https://user-images.githubusercontent.com/39047457/140243970-4e293dbf-8f58-4cc6-a0f3-227c39cff551.png)
![image](https://user-images.githubusercontent.com/39047457/140244095-0518be33-16d0-4fe3-8466-954200870be0.png)

## Technologies used in this project:
- Java version 11
- Lanterna version 3.1.1
- Jlayer version 1.0.1

## Technologies in unit tests:
- JUnit Jupiter API version 5.8.1
- Mockito Core version 4.0.0

## License
[MIT](https://choosealicense.com/licenses/mit/)






