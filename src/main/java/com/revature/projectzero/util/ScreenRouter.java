package com.revature.projectzero.util;

import com.revature.projectzero.util.exceptions.ScreenNotFoundException;
import com.revature.projectzero.screens.Screen;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;


// Class for routing between screens.
public class ScreenRouter {

    private Screen currentScreen;

    // HashSet of screens for storing activated screens
    private final Set<Screen> screens = new HashSet<>();

    // ArrayDeque to be used as a stack for "back" or "cancel" functionality.
    private final ArrayDeque<Screen> previousScreens;

    // Instantiates history in default cons
    public ScreenRouter(){ previousScreens = new ArrayDeque<>();}

    //Method to add a screen to the hashset.
    public ScreenRouter addScreen(Screen screen) {
        screens.add(screen);
        return this;
    }

    // Retrieves a route, then sets the current screen to the screen with a matching route by using Collection.Stream()
    // to create a stream of Screen objects, filtering it to screens with the defined route, and finally
    // returning the first instance of a stream with that route.

    public void navigate(String route) {
        if(currentScreen != null) {
            previousScreens.push(currentScreen); //pushes the current screen onto the top of the stack
        }
        currentScreen = screens.stream()
                .filter(screen -> screen.getRoute().equals(route))
                .findFirst()
                .orElseThrow(ScreenNotFoundException::new);
    }

    // Pops a screen off the PreviousScreens deque to return to a previous screen.
    public void goBack() throws ScreenNotFoundException{
        if(previousScreens.isEmpty()){ throw new ScreenNotFoundException();}
        currentScreen = previousScreens.pop();
    }

    // Cleanses the history to free up memory after a user logs off to prevent potential security risks.
    public void deleteHistory(){ previousScreens.clear(); }

    // Getter
    public Screen getCurrentScreen() {
        return currentScreen;
    }


}
