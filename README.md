# Survival game in window gui


## Table of contents
* [What is it?](#what-this-is-about)
* [Functionality](#functionality-in-game-with-amazing-view)
* [Manual](#manual-how-to-navigate-through-game)
* [Images](#images-from-game)
* [Fragments of code](#interesting-fragments-of-code)
* [Used technologies](#technologies-used-in-this-project)
* [License](#license)

## What this is about?

Similar game to sixty seconds, especially to the second part of it, a bit simpler. Make choices which will lead you to survive as many days as possible. Score are set based on days you survived. Feed people and keep them hydrated, send them on expedition to catch supplies and try to survive the next day! How many days are you able to achieve? Try yourself!

## Functionality in game with amazing view!

  1. Play the game! - that is the first and the most important functionality, during the game you can do actions like:
- Drink: hydrate current person!
- Eat: feed them, don't make them starving..
- Expedition: send current person out from shelter for  from 1 to 3 days in order to find some water and potato soups.
- Next person: change person you are do actions on (if this is the last person in shelter - next day executes).
- Next day: move on to the next day and update dialy diary.
- Set volume music volume.
- Back to menu: go back to menu.
   
## Manual how to navigate through game:
You will only need mouse and PMK (Primary Mouse Key) to choose actions from menu and action lists it, so simple! 

## Images from game

![image](https://user-images.githubusercontent.com/39047457/144833289-41ccc358-0547-420b-af2f-fdae6f38c9c3.png)

![image](https://user-images.githubusercontent.com/39047457/144833324-e75d9553-b0c8-42b0-8133-6f0d9b3028c9.png)

![image](https://user-images.githubusercontent.com/39047457/144833537-8145f915-2292-4e6f-af7b-d5305f3b883a.png)

![image](https://user-images.githubusercontent.com/39047457/144834738-6b5c25b1-7f73-465c-bf57-1c92cc0341eb.png)

![image](https://user-images.githubusercontent.com/39047457/144833785-562e8de9-cacb-4a3d-aa43-ae691d057681.png)

![image](https://user-images.githubusercontent.com/39047457/144833838-2f524266-d341-44f3-aa24-dc7b9a012916.png)

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
- Javafx version 11.0.1

## Technologies in unit tests:
- JUnit Jupiter API version 5.8.1
- Mockito Core version 4.0.0

## License
[MIT](https://choosealicense.com/licenses/mit/)



