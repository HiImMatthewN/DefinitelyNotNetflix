package com.example.pinayflix.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pinayflix.local.dao.SavedItemDao;
import com.example.pinayflix.model.datamodel.SavedItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SavedItemRepository {
   private SavedItemDao savedItemDao;
    private static final String TAG = "SavedItemRepository";
    private MutableLiveData<List<SavedItem>> saveItemsLiveData;
    private MutableLiveData<Boolean> savedItemExists = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Inject
    public SavedItemRepository(SavedItemDao savedItemDao) {
        this.savedItemDao = savedItemDao;
        saveItemsLiveData = new MutableLiveData<>();
        Log.d(TAG, "SavedItemRepository: Created");
    }

    public void insertSavedItem(SavedItem savedItem) {
        Disposable disposable = Completable.fromRunnable(() -> {
            savedItemDao.insert(savedItem);
        }).subscribeOn(Schedulers.io()).subscribe(() -> {
            Log.d(TAG, "insertSavedItem: " + savedItem.getTitle());
            savedItemExists.postValue(true);
        });
        compositeDisposable.add(disposable);
    }

    public void deleteSavedItem(SavedItem savedItem) {
        Disposable disposable = Completable.fromRunnable(() -> {
            savedItemDao.delete(savedItem.getId());
        }).subscribeOn(Schedulers.io()).subscribe(() -> {
            Log.d(TAG, "deleteSavedItem: Name " + savedItem.getTitle());
            savedItemExists.postValue(false);

        });
        compositeDisposable.add(disposable);

    }

    public void checkIfSavedItemExists(int id) {
        Log.d(TAG, "checkIfSavedItemExists:Checking ");
        Disposable disposable = savedItemDao.checkIfExists(id)
                .subscribeOn(Schedulers.io())
                .subscribe(value -> {
                    savedItemExists.postValue(value);
                });
        compositeDisposable.add(disposable);
    }

    public void getAllSavedItem() {
        Log.d(TAG, "getAllSavedItem: Getting All Saved Item");
        Disposable disposable = savedItemDao.getAll().subscribeOn(Schedulers.io())
                .subscribe(savedItems->{
                    saveItemsLiveData.postValue(savedItems);
                });
        compositeDisposable.add(disposable);
    }

    public LiveData<List<SavedItem>> getSaveItemsLiveData() {
        return saveItemsLiveData;
    }

    public LiveData<Boolean> getSavedItemExists() {
        return savedItemExists;
    }

    public void disposeSubscribers() {
        compositeDisposable.dispose();
    }
}
