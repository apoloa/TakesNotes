package com.adri.takenotes.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adri.takenotes.R
import com.adri.takenotes.model.ClientTable
import com.adri.takenotes.model.Dish
import com.adri.takenotes.views.DishView
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView

class DishFragment extends Fragment{

    final static String ARG_TABLE_CLIENT = "Dishs"

    private LinkedList<Dish> dishes

    @InjectView(R.id.list_dishes)
    RecyclerView listDishes

    /**
     * Factory estatico enviando el Client Table al fragment
     * @param clientTable Numero de Tabla
     * @return Fragment
     */
    static DishFragment newInstance(LinkedList<Dish> dishes){
        DishFragment fragment = new DishFragment()

        Bundle bundle = new Bundle()
        bundle.putSerializable(ARG_TABLE_CLIENT, dishes)
        fragment.setArguments(bundle)
        return fragment
    }

    @Override
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        if(getArguments() != null){
            dishes = (LinkedList<Dish>) getArguments().getSerializable(ARG_TABLE_CLIENT)

        }
    }

    @Override
    View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState)

        def root = inflater.inflate(R.layout.fragment_list_dish,container, false);

        SwissKnife.inject(this,root)
        listDishes.setLayoutManager(new LinearLayoutManager(getActivity()));
        listDishes.setItemAnimator(new DefaultItemAnimator());
        listDishes.swapAdapter(new DishAdapter(dishes),false)

        return root
    }

    protected class DishViewHolder extends RecyclerView.ViewHolder {

        private DishView dishView

        DishViewHolder(View itemView) {
            super(itemView)

            dishView = (DishView) itemView
        }

        public void bindDish(Dish dish){

            dishView.setDescriptionDish(dish.description)
            dishView.setTitleDish(dish.name)
            dishView.setAllergens(dish.allergens)
            dishView.setPrice(dish.price)
        }
    }

    protected class DishAdapter extends RecyclerView.Adapter<DishViewHolder>{

        private List<Dish> dishs;

        public DishAdapter(List<Dish> dishs){
            super();
            this.dishs = dishs
        }

        @Override
        DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DishViewHolder(new DishView(getActivity()))
        }

        @Override
        void onBindViewHolder(DishViewHolder holder, int position) {
            Dish currentDish = dishs.get(position)
            holder.bindDish(currentDish)
        }

        @Override
        int getItemCount() {

            return dishs.size()
        }
    }
}