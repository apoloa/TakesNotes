package com.adri.takenotes.model

class ClientTable implements Serializable {

    int numberTable
    String name
    LinkedList<Dish> dishes

    public ClientTable(int numberTable, String name) {
        this.numberTable = numberTable
        this.name = name
        dishes = new LinkedList<>()
    }

    boolean removeDish(int id) {
        Dish founded
        for (Dish d : dishes) {
            if (d.id == id) {
                founded = d
                break
            }
        }
        if (!founded) {
            return false
        } else {
            dishes.remove(founded)
            return true
        }
    }

    void addDish(Dish dish, boolean replace) {
        for (Dish d : dishes) {
            if (d.id == dish.id) {
                if(!replace){
                    d.quantity += dish.quantity
                    d.additions += " " + dish.additions
                }else{
                    d.quantity = dish.quantity
                    d.additions = " " + dish.additions
                }

                return
            }
        }
        dishes.add(dish)
    }

    @Override
    String toString() {
        return numberTable + " - " + name.toString()
    }


}