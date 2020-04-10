package com.jay.hometuition;

import android.view.View;

interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
