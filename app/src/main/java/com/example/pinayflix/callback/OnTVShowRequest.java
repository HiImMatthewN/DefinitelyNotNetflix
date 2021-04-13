package com.example.pinayflix.callback;

import com.example.pinayflix.model.datamodel.tvshow.TVShow;

public interface OnTVShowRequest {
    void onTVShowSelect(TVShow tvShow);
}
