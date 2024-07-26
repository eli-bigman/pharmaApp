package com.example.pharmaapp.entities;
import javafx.event.ActionEvent;

import java.util.Stack;

public  class UndoManager {
    public static final Stack<Action> actionStack = new Stack<>();

    public void performAction(Action action, ActionEvent e) {
        action.execute(e);
        actionStack.push(action);
    }

    public void undo(ActionEvent e) {
        if (!actionStack.isEmpty()) {
            Action action = actionStack.pop();
            action.undo(e);
        } else {
            System.out.println("No actions to undo.");
        }
    }

    }

