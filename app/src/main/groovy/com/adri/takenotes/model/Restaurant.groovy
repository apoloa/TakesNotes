package com.adri.takenotes.model

import groovy.transform.CompileStatic

class Restaurant implements Serializable{

    static String URL_PATH = "http://www.mocky.io/v2/571abf06120000a0231b72ae"

    LinkedList<ClientTable> tables
    LinkedList<Dish> dishes
    boolean downloadedMenu = false

    private static Restaurant instance

    public static Restaurant getInstance(){
        if(instance == null){
            instance = new Restaurant()
        }
        return instance
    }

    private Restaurant(){
        tables = new LinkedList<>()
        dishes = new LinkedList<>()
        //tables.add(new ClientTable(tables.size()+1,"Hola Mundio"))

    }

    public void addNewTable(String name){
        tables.add(new ClientTable(tables.size()+1,name))
    }

    public ClientTable getTable(int id){
        for(ClientTable t:tables){
            if(t.numberTable == id){
                return t
            }
        }
        return null
    }

    public int numberTables(){
        return tables.size() + 1
    }

}