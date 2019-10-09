package com.pandey.popcorn4.movie;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


public class SwipeToDismissCallback extends ItemTouchHelper.SimpleCallback {

    @NonNull
    private Context mContext;

    @NonNull
    private SwipeToDismissListener mSwipeToDismissListener;

    SwipeToDismissCallback(@NonNull Context context,
                           @NonNull SwipeToDismissListener swipeToDismissListener) {
        super(0, ItemTouchHelper.RIGHT);
        this.mContext = context;
        this.mSwipeToDismissListener = swipeToDismissListener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mSwipeToDismissListener.onSwipedItem(viewHolder.getAdapterPosition());
        Toast.makeText(mContext, "Added to your favourite list", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }

    public interface SwipeToDismissListener {
        void onSwipedItem(int position);
    }
}
