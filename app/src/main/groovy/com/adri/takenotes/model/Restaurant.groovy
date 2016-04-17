package com.adri.takenotes.model;

class Restaurant implements Serializable{

    LinkedList<ClientTable> tables
    LinkedList<Dish> dishs

    private static Restaurant instance

    public Restaurant getInstance(){
        if(instance == null){
            instance = new Restaurant()
        }
        return instance
    }

    private Restaurant(){
        tables = new LinkedList<>();
        tables.add(new ClientTable(tables.size()+1,"Hola Mundio"))
    }

    public void addNewTable(String name){
        tables.add(new ClientTable(tables.size()+1,name))
    }

    public int numberTables(){
        return tables.size() + 1
    }
}