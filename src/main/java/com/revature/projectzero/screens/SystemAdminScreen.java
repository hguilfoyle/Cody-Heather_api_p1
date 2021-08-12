package com.revature.projectzero.screens;

import com.revature.projectzero.util.ScreenRouter;

import java.io.BufferedReader;
import java.io.IOException;

public class SystemAdminScreen extends Screen{

    public SystemAdminScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("SystemAdminScreen", "/sys-admin", consoleReader, router);
    }

    /*
     * System admin screen for unlocking the console and adding Faculty accounts, (if i implement that)
     *  a little side project for myself.
     *
     *  Didn't really get around to it...
     */

    @Override
    public void render() throws IOException {

        System.out.println("Awaken, my master.\n\n" +
                "1) Unlock the console\n" +
                "2) Add new Faculty account\n" +
                "3) Log out.");

        System.out.println("Nothing to do for now...");

        router.navigate("/welcome");



    }
}
