package com.company.gamestore.dao;

import com.company.gamestore.model.Tshirt;

import java.util.List;

public interface TshirtDao {
    Tshirt addTshirt(Tshirt tshirt);

    Tshirt getTshirt(int tshirtId);

    List<Tshirt> getAllTshirts();

    Tshirt updateTshirt(Tshirt tshirt);

    void deleteTshirt(int tshirtId);

    List<Tshirt> findTshirtByColor(String color);

    List<Tshirt> findTshirtBySize(String size);

}

