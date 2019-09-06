package com.company.gamestore.dao;
import com.company.gamestore.model.Console;

import java.util.List;

public interface ConsoleDao {

    Console addConsole(Console console);

    Console getConsole(int consoleId);

    List<Console> getAllConsoles();

    Console updateConsole(Console console);

    void deleteConsole(int consoleId);

    List<Console> findConsolesbyManufacturer(String manufacturer);

}

