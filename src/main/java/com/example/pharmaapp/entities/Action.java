package com.example.pharmaapp.entities;


import javafx.event.ActionEvent;

public interface Action {
        public void execute(ActionEvent e);
        public void undo(ActionEvent e);

}
