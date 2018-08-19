package com.autodoc.autodoc.listeners;

/**
 * Created by ilabs on 8/15/18.
 */

public interface OnItemClickListener<T> {

    /**
     *
     * @param position
     * @param data
     *
     * this method implementation will give clicked object
     */
    void onItemClick(int position, T data);

}