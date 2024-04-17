package com.Bank.project.controller;

import java.util.Random;

public class GenerateRandom {


    public long getRandom(){
        int minId = 10000;
        int maxId = 99999;
        Random random = new Random();
        return random.nextInt(maxId - minId + 1) + minId;
    }

}
