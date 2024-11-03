package org.example.command;

import org.example.figures.Figure;

public interface Command {

    public void execute();
    public Figure getShape();
}
